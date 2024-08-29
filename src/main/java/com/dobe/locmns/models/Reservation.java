package com.dobe.locmns.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESERVATION")
public class Reservation extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Reservation")
    private Integer id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateRetour;
    private  Integer quantite;
    private String motifPret;
    private boolean validate;
    private boolean ProlongationValidee;
    @ManyToOne
    @JoinColumn(name = "Id_Materiel")
    private Materiel materiel;
    @ManyToOne
    @JoinColumn(name = "Id_Utilisateur")
    private Utilisateur utilisateur;



}
