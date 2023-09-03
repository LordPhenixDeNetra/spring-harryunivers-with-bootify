package org.netrapp.harryunivers.repos;

import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.domain.Maison;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MaisonRepository extends JpaRepository<Maison, Long> {

    Maison findFirstByEcole(Ecole ecole);

}
