package com.dobe.locmns.dto;

import com.dobe.locmns.models.Materiel;
import com.dobe.locmns.models.Pannes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PannesDto {

    private Integer id;
    private String description;
    private String imageUrl;
    private Materiel materiel;

    public static PannesDto fromEntity(Pannes pannes) {
        return PannesDto.builder()
                .id(pannes.getId())
                .description(pannes.getDescription())
                .imageUrl(pannes.getImageUrl())
                .materiel(pannes.getMateriel())
                .build();
    }
    public static Pannes toEntity(PannesDto pannes) {
        return Pannes.builder()
                .id(pannes.getId())
                .description(pannes.getDescription())
                .imageUrl(pannes.getImageUrl())
                .materiel(pannes.getMateriel())
                .build();

    }
}
