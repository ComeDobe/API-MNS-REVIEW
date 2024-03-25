package com.dobe.locmns.dto;

import com.dobe.locmns.models.Materiel;
import com.dobe.locmns.models.Pret;
import com.dobe.locmns.models.Utilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "La date de debut ne peut pas etre vide")
    private LocalDate dateDebut;
    @NotBlank(message = "La date de fin ne peut pas etre vide")
    private LocalDate dateFin;
    private String Description;
    @NotBlank(message = "La quantite ne peut pas etre vide")
    private Integer quantite;
    @NotBlank(message = "L'utilisateur ne peut pas etre vide")
    private Utilisateur utilisateur;
    private Materiel materiel;
    @NotBlank(message = "La prolongation ne peut pas etre vide")
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
