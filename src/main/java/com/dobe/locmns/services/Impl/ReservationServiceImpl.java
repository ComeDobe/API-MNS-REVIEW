package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.dto.ReservationDto;
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
    public Integer validateReservation(ReservationDto dto) {
        validator.validate(dto);
        Reservation reservation = ReservationDto.toEntity(dto);
        reservation.setValidated(true);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    @Transactional
    public Integer invalidateReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setValidated(false);
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
        return createReservation(dto);
    }

    @Override
    @Transactional
    public Integer update(ReservationDto dto) {
        validator.validate(dto);
        Reservation reservation = ReservationDto.toEntity(dto);
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
    @Transactional
    public Integer createReservation(ReservationDto reservationDto) {
        MaterielDto materielDto = reservationDto.getMateriel();
        if (materielDto.getQuantite() < reservationDto.getQuantite()) {
            throw new IllegalArgumentException("La quantité demandée est supérieure à la quantité disponible");
        }

        materielDto.setQuantite(materielDto.getQuantite() - reservationDto.getQuantite());
        materielService.update(materielDto);

        Reservation reservation = ReservationDto.toEntity(reservationDto);
        Integer reservationId = reservationRepository.save(reservation).getId();

        emailService.sendConfirmationEmail(reservationDto.getUtilisateur().getEmail(), "Votre réservation a été enregistrée avec succès");

        return reservationId;
    }

}
