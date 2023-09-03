package org.netrapp.harryunivers.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.netrapp.harryunivers.model.OtherType;
import org.netrapp.harryunivers.model.PersonneType;
import org.netrapp.harryunivers.model.SexeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Personne {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uidPersonne;

    @Column(length = 50)
    private String nomPersonne;

    @Column(length = 50)
    private String prenomPersonne;

    @Column
    @Enumerated(EnumType.STRING)
    private SexeType sexePersonne;

    @Column
    private OffsetDateTime naissance;

    @Column(length = 50)
    private String lieuNaissance;

    @Column(length = 150)
    private String baguette;

    @Column(length = 50)
    private String patronus;

    @Column(length = 50)
    private String animalCompagnie;

    @Column
    private Boolean animagus;

    @Column(length = 50)
    private String animalForme;

    @Column
    private String avatar;

    @Column
    @Enumerated(EnumType.STRING)
    private PersonneType personneType;

    @Column
    @Enumerated(EnumType.STRING)
    private OtherType otherType;

    @Column
    private OffsetDateTime debutEtude;

    @Column
    private OffsetDateTime finEtude;

    @Column
    private OffsetDateTime debutDirecteurEcole;

    @Column
    private OffsetDateTime finDirecteurEcole;

    @Column
    private OffsetDateTime debutDirecteurMaison;

    @Column
    private OffsetDateTime finDirecteurMaison;

    @Column
    private String email;

    @Column(length = 150)
    private String username;

    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecole_id")
    private Ecole ecole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maison_id")
    private Maison maison;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
