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
@Table(name = " ROLE ")

public class Role extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Id_Role")
    private Integer id;

    private String roleName;

    private String roleDescription;

}