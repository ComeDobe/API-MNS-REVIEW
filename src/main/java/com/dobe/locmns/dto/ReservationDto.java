package com.dobe.locmns.dto;

import com.dobe.locmns.models.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReservationDto {

        private Integer id;
        private LocalDate dateDebut;
        private LocalDate dateFin;
        private  LocalDate dateRetour;
        private Integer quantite;
        private String motifPret;
        private MaterielDto materiel;
        private UtilisateurDto utilisateur;

        public static ReservationDto fromEntity(Reservation reservation) {
            return ReservationDto.builder()
                    .id(reservation.getId())
                    .dateDebut(reservation.getDateDebut())
                    .dateFin(reservation.getDateFin())
                    .dateRetour(reservation.getDateRetour())
                    .quantite(reservation.getQuantite())
                    .motifPret(reservation.getMotifPret())
                    .materiel(MaterielDto.fromEntity(reservation.getMateriel()))
                    .utilisateur(UtilisateurDto.fromEntity(reservation.getUtilisateur()))
                    .build();
        }
        public static Reservation toEntity(ReservationDto reservation) {
            return Reservation.builder()
                    .id(reservation.getId())
                    .dateDebut(reservation.getDateDebut())
                    .dateFin(reservation.getDateFin())
                    .dateRetour(reservation.getDateRetour())
                    .quantite(reservation.getQuantite())
                    .motifPret(reservation.getMotifPret())
                    .materiel(MaterielDto.toEntity(reservation.getMateriel()))
                    .utilisateur(UtilisateurDto.toEntity(reservation.getUtilisateur()))
                    .build();

        }

}
