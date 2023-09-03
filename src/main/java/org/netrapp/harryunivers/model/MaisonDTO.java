package org.netrapp.harryunivers.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MaisonDTO {

    private Long id;

    @Size(max = 255)
    private String uidMaison;

    @Size(max = 50)
    private String nomMaison;

    @Size(max = 255)
    private String hymneMaison;

    @Size(max = 255)
    private String descriptionMaison;

    private Long ecole;

}
