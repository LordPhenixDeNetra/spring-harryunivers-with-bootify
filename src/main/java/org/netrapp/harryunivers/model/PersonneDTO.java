package org.netrapp.harryunivers.model;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class PersonneDTO {

    private Long id;

    @Size(max = 255)
    private String uidPersonne;

    @Size(max = 50)
    private String nomPersonne;

    @Size(max = 50)
    private String prenomPersonne;

    private SexeType sexePersonne;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime naissance;

    @Size(max = 50)
    private String lieuNaissance;

    @Size(max = 150)
    private String baguette;

    @Size(max = 50)
    private String patronus;

    @Size(max = 50)
    private String animalCompagnie;

    private Boolean animagus;

    @Size(max = 50)
    private String animalForme;

    @Size(max = 255)
    private String avatar;

    private PersonneType personneType;

    private OtherType otherType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime debutEtude;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime finEtude;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime debutDirecteurEcole;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime finDirecteurEcole;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime debutDirecteurMaison;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime finDirecteurMaison;

    @Size(max = 255)
    private String email;

    @Size(max = 150)
    private String username;

    @Size(max = 255)
    private String password;

    private Long ecole;

    private Long maison;

}
