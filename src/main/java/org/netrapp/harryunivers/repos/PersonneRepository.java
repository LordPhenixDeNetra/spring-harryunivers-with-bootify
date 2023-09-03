package org.netrapp.harryunivers.repos;

import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.domain.Maison;
import org.netrapp.harryunivers.domain.Personne;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Personne findFirstByEcole(Ecole ecole);

    Personne findFirstByMaison(Maison maison);

}
