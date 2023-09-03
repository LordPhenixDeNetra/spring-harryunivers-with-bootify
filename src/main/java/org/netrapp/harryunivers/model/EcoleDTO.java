package org.netrapp.harryunivers.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EcoleDTO {

    private Long id;

    @Size(max = 255)
    private String uidEcole;

    @Size(max = 60)
    private String nomEcole;

    @Size(max = 255)
    private String descriptionEcole;

    @Size(max = 60)
    private String lieuEcole;

}
