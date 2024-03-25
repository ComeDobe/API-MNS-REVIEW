package com.dobe.locmns.dto;

import com.dobe.locmns.models.Localisation;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LocalisationDto {

    private Integer id;
    private String Lieu;
    private String batiment;
    private String salle;

    public static LocalisationDto fromEntity(Localisation localisation) {
        return LocalisationDto.builder()
                .id(localisation.getId())
                .Lieu(localisation.getLieu())
                .batiment(localisation.getBatiment())
                .salle(localisation.getSalle())
                .build();
    }
    public static Localisation toEntity(LocalisationDto localisation) {
        return Localisation.builder()
                .id(localisation.getId())
                .Lieu(localisation.getLieu())
                .batiment(localisation.getBatiment())
                .salle(localisation.getSalle())
                .build();

    }
}
