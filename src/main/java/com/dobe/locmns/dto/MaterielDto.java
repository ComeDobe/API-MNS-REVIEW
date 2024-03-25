package com.dobe.locmns.dto;

import com.dobe.locmns.models.Localisation;
import com.dobe.locmns.models.Materiel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MaterielDto {


    private Integer id;
    @NotBlank(message = "La marque ne peut pas etre vide")
    private String marque;
    @NotBlank(message = "La reference ne peut pas etre vide")
    private String reference;
    @NotBlank(message = "La description ne peut pas etre vide")
    private String description;
    @NotBlank(message = "La quantite ne peut pas etre vide")
    private Integer quantite;
    @NotBlank(message = "La localisation ne peut pas etre vide")
    private Localisation localisation;

    public static MaterielDto fromEntity(Materiel materiel) {
        return MaterielDto.builder()
                .id(materiel.getId())
                .marque(materiel.getMarque())
                .reference(materiel.getReference())
                .description(materiel.getDescription())
                .quantite(materiel.getQuantite())
                .localisation(materiel.getLocalisation())
                .build();
    }
    public static Materiel toEntity(MaterielDto materiel) {
        return Materiel.builder()
                .id(materiel.getId())
                .marque(materiel.getMarque())
                .reference(materiel.getReference())
                .description(materiel.getDescription())
                .quantite(materiel.getQuantite())
                .localisation(materiel.getLocalisation())
                .build();

    }
}
