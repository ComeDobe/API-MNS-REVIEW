package com.dobe.locmns.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = " PANNES ")
public class Pannes extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Pannes")
    private Integer id;
    private String description;
    private String imageUrl;
    @ManyToOne
    private Materiel materiel;

}
