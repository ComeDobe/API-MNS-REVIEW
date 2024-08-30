package com.dobe.locmns.services.Impl;
import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.dto.ReservationDto;
import com.dobe.locmns.dto.UtilisateurDto;
import com.dobe.locmns.models.Reservation;
import com.dobe.locmns.repositories.ReservationRepository;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.services.ReservationService;
import com.dobe.locmns.services.service.EmailService;
import com.dobe.locmns.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ObjectsValidator<ReservationDto> validator;
    private final MaterielService materielService;
    private final EmailService emailService;

    @Override
    @Transactional
    public Integer validateReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setValidate(true);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    @Transactional
    public Integer invalidateReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setValidate(false);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    @Transactional
    public Void delete(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée avec l'ID : " + id));

        MaterielDto materiel = materielService.findById(reservation.getMateriel().getId());
        materiel.setQuantite(materiel.getQuantite() + reservation.getQuantite());
        materielService.update(materiel);

        reservationRepository.delete(reservation);
        return null;
    }

    @Override
    @Transactional
    public Integer save(ReservationDto dto) {
        validator.validate(dto);
        MaterielDto materielDto = materielService.findById(dto.getMateriel().getId());
        if (materielDto.getQuantite() < dto.getQuantite()) {
            throw new IllegalArgumentException("La quantité demandée est supérieure à la quantité disponible");
        }
        materielDto.setQuantite(materielDto.getQuantite() - dto.getQuantite());
        materielService.update(materielDto);
        Reservation reservation = new Reservation();
        updateReservationFromDto(reservation, dto);
        reservation.setValidate(false);
        Integer reservationId = reservationRepository.save(reservation).getId();

        sendConfirmationEmail(dto, reservationId, materielDto);
        sendAdminNotificationEmail(dto, reservationId, materielDto);

        return reservationId;
    }

    private void sendAdminNotificationEmail(ReservationDto reservationDto, Integer reservationId, MaterielDto materielDto) {
        String adminEmail = "autorisation.loc@gmail.com";
        String messageAdmin = "Une nouvelle réservation a été créée :\n" +
                "Identifiant de la Réservation : " + reservationId + "\n" +
                "Utilisateur : " + reservationDto.getUtilisateur().getFirstName() + " " + reservationDto.getUtilisateur().getLastName() + "\n" +
                "Matériel : " + materielDto.getReference() + "\n" +
                "Quantité : " + reservationDto.getQuantite();

        emailService.sendConfirmationEmail(adminEmail, "Nouvelle réservation", messageAdmin);
    }


    @Override
    @Transactional
    public Integer update(ReservationDto dto) {
        validator.validate(dto);
        Reservation reservation = reservationRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        updateReservationFromDto(reservation, dto);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto findById(Integer id) {
        return reservationRepository.findById(id)
                .map(ReservationDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
    }

    @Override
    @Transactional
    public Integer createReservation(ReservationDto reservationDto) {
        MaterielDto materielDto = materielService.findById(reservationDto.getMateriel().getId());
        if (materielDto.getQuantite() < reservationDto.getQuantite()) {
            throw new IllegalArgumentException("La quantité demandée est supérieure à la quantité disponible");
        }

        materielDto.setQuantite(materielDto.getQuantite() - reservationDto.getQuantite());
        materielService.update(materielDto);

        Reservation reservation = new Reservation();
        updateReservationFromDto(reservation, reservationDto);
        Integer reservationId = reservationRepository.save(reservation).getId();

        sendConfirmationEmail(reservationDto, reservationId, materielDto);

        return reservationId;
    }

    @Override
    @Transactional
    public Integer validateExtension(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));

        if (reservation.isProlongationValidee()) {
            throw new IllegalArgumentException("Prolongation déjà validée");
        }

        reservation.setProlongationValidee(true);
        return reservationRepository.save(reservation).getId();
    }

    private void updateReservationFromDto(Reservation reservation, ReservationDto dto) {
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setDateRetour(dto.getDateRetour());
        reservation.setQuantite(dto.getQuantite());
        reservation.setMotifPret(dto.getMotifPret());
        reservation.setMateriel(MaterielDto.toEntity(dto.getMateriel()));
        reservation.setUtilisateur(UtilisateurDto.toEntity(dto.getUtilisateur()));
    }

    private void sendConfirmationEmail(ReservationDto reservationDto, Integer reservationId, MaterielDto materielDto) {
        String messageUser = "Cher utilisateur,\n\nVotre demande de reservation a été confirmée. " +
                "Nous vous contacterons bientôt pour organiser les détails du prêt." +
                "\n\nDétails du matériel :\n" +
                "Identifiant du Materiel : " + materielDto.getId() + "\n" +
                "Identifiant du Pret : " + reservationId + "\n" +
                "Référence : " + materielDto.getReference() + "\n" +
                "Description : " + materielDto.getDescription() + "\n";

        emailService.sendConfirmationEmail(reservationDto.getUtilisateur().getEmail(), "Confirmation de réservation", messageUser);
    }
}
