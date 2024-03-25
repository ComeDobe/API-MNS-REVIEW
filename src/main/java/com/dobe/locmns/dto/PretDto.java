package com.dobe.locmns.dto;

import com.dobe.locmns.models.Materiel;
import com.dobe.locmns.models.Pret;
import com.dobe.locmns.models.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@Builder

public class PretDto {

    private Integer id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String Description;
    private Integer quantite;
    private Utilisateur utilisateur;
    private Materiel materiel;
    private boolean prolongationValide;
    public static PretDto fromEntity(Pret pret) {
        return PretDto.builder()
                .id(pret.getId())
                .dateDebut(pret.getDateDebut())
                .dateFin(pret.getDateFin())
                .Description(pret.getDescription())
                .quantite(pret.getQuantite())
                .utilisateur(pret.getUtilisateur())
                .materiel(pret.getMateriel())
                .prolongationValide(pret.isProlongationValide())
                .build();
    }
    public static Pret toEntity(PretDto pret) {
        return Pret.builder()
                .id(pret.getId())
                .dateDebut(pret.getDateDebut())
                .dateFin(pret.getDateFin())
                .Description(pret.getDescription())
                .quantite(pret.getQuantite())
                .utilisateur(pret.getUtilisateur())
                .materiel(pret.getMateriel())
                .prolongationValide(pret.isProlongationValide())
                .build();

    }
}
