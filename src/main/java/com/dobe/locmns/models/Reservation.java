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
@Table(name = "RESERVATION")
public class Reservation extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Reservation")
    private Integer id;
    private String dateDebut;
    private String dateFin;
    private String dateRetour;
    private  Integer quantite;
    private String motifPret;
    @ManyToOne
    @JoinColumn(name = "Id_Materiel")
    private Materiel materiel;
    @ManyToOne
    @JoinColumn(name = "Id_Utilisateur")
    private Utilisateur utilisateur;


    public void setValidated(boolean b) {
    }
}
