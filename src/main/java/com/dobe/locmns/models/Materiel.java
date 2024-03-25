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
@Table(name = " MATERIEL")
public class Materiel  extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Materiel")
    private Integer id;

    private String marque;
    private String reference;
    private String description;
    private Integer quantite;
    @ManyToOne
    @JoinColumn(name = "Id_Localisation")
    private Localisation localisation;

}
