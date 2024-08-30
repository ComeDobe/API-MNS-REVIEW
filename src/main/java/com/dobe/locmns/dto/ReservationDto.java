package com.dobe.locmns.dto;

import com.dobe.locmns.models.Reservation;
import com.dobe.locmns.models.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReservationDto {

        private Integer id;
        private LocalDate dateDebut;
        private LocalDate dateFin;
        private LocalDate dateRetour;
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
                        .utilisateur(convertToUtilisateurDto(reservation.getUtilisateur()))
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

        private static UtilisateurDto convertToUtilisateurDto(Utilisateur utilisateur) {
                return UtilisateurDto.builder()
                        .id(utilisateur.getId())
                        .firstName(utilisateur.getFirstName())
                        .lastName(utilisateur.getLastName())
                        .email(utilisateur.getEmail())
                        .role(utilisateur.getRole().stream()
                                .map(role -> RoleDto.fromEntity(role))
                                .collect(Collectors.toSet()))
                        .adresse(utilisateur.getAdresse())
                        .telephone(utilisateur.getTelephone())
                        .active(utilisateur.isActive())
                        .isAdmin(utilisateur.isAdmin())
                        .build();
        }

}
