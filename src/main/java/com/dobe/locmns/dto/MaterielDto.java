package com.dobe.locmns.dto;

import com.dobe.locmns.models.Localisation;
import com.dobe.locmns.models.Materiel;
import jakarta.persistence.*;
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
    private String marque;
    private String reference;
    private String description;
    private Integer quantite;
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
