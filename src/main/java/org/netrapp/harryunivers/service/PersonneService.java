package org.netrapp.harryunivers.service;

import java.util.List;
import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.domain.Maison;
import org.netrapp.harryunivers.domain.Personne;
import org.netrapp.harryunivers.model.PersonneDTO;
import org.netrapp.harryunivers.repos.EcoleRepository;
import org.netrapp.harryunivers.repos.MaisonRepository;
import org.netrapp.harryunivers.repos.PersonneRepository;
import org.netrapp.harryunivers.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PersonneService {

    private final PersonneRepository personneRepository;
    private final EcoleRepository ecoleRepository;
    private final MaisonRepository maisonRepository;

    public PersonneService(final PersonneRepository personneRepository,
            final EcoleRepository ecoleRepository, final MaisonRepository maisonRepository) {
        this.personneRepository = personneRepository;
        this.ecoleRepository = ecoleRepository;
        this.maisonRepository = maisonRepository;
    }

    public List<PersonneDTO> findAll() {
        final List<Personne> personnes = personneRepository.findAll(Sort.by("id"));
        return personnes.stream()
                .map(personne -> mapToDTO(personne, new PersonneDTO()))
                .toList();
    }

    public PersonneDTO get(final Long id) {
        return personneRepository.findById(id)
                .map(personne -> mapToDTO(personne, new PersonneDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonneDTO personneDTO) {
        final Personne personne = new Personne();
        mapToEntity(personneDTO, personne);
        return personneRepository.save(personne).getId();
    }

    public void update(final Long id, final PersonneDTO personneDTO) {
        final Personne personne = personneRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(personneDTO, personne);
        personneRepository.save(personne);
    }

    public void delete(final Long id) {
        personneRepository.deleteById(id);
    }

    private PersonneDTO mapToDTO(final Personne personne, final PersonneDTO personneDTO) {
        personneDTO.setId(personne.getId());
        personneDTO.setUidPersonne(personne.getUidPersonne());
        personneDTO.setNomPersonne(personne.getNomPersonne());
        personneDTO.setPrenomPersonne(personne.getPrenomPersonne());
        personneDTO.setSexePersonne(personne.getSexePersonne());
        personneDTO.setNaissance(personne.getNaissance());
        personneDTO.setLieuNaissance(personne.getLieuNaissance());
        personneDTO.setBaguette(personne.getBaguette());
        personneDTO.setPatronus(personne.getPatronus());
        personneDTO.setAnimalCompagnie(personne.getAnimalCompagnie());
        personneDTO.setAnimagus(personne.getAnimagus());
        personneDTO.setAnimalForme(personne.getAnimalForme());
        personneDTO.setAvatar(personne.getAvatar());
        personneDTO.setPersonneType(personne.getPersonneType());
        personneDTO.setOtherType(personne.getOtherType());
        personneDTO.setDebutEtude(personne.getDebutEtude());
        personneDTO.setFinEtude(personne.getFinEtude());
        personneDTO.setDebutDirecteurEcole(personne.getDebutDirecteurEcole());
        personneDTO.setFinDirecteurEcole(personne.getFinDirecteurEcole());
        personneDTO.setDebutDirecteurMaison(personne.getDebutDirecteurMaison());
        personneDTO.setFinDirecteurMaison(personne.getFinDirecteurMaison());
        personneDTO.setEmail(personne.getEmail());
        personneDTO.setUsername(personne.getUsername());
        personneDTO.setPassword(personne.getPassword());
        personneDTO.setEcole(personne.getEcole() == null ? null : personne.getEcole().getId());
        personneDTO.setMaison(personne.getMaison() == null ? null : personne.getMaison().getId());
        return personneDTO;
    }

    private Personne mapToEntity(final PersonneDTO personneDTO, final Personne personne) {
        personne.setUidPersonne(personneDTO.getUidPersonne());
        personne.setNomPersonne(personneDTO.getNomPersonne());
        personne.setPrenomPersonne(personneDTO.getPrenomPersonne());
        personne.setSexePersonne(personneDTO.getSexePersonne());
        personne.setNaissance(personneDTO.getNaissance());
        personne.setLieuNaissance(personneDTO.getLieuNaissance());
        personne.setBaguette(personneDTO.getBaguette());
        personne.setPatronus(personneDTO.getPatronus());
        personne.setAnimalCompagnie(personneDTO.getAnimalCompagnie());
        personne.setAnimagus(personneDTO.getAnimagus());
        personne.setAnimalForme(personneDTO.getAnimalForme());
        personne.setAvatar(personneDTO.getAvatar());
        personne.setPersonneType(personneDTO.getPersonneType());
        personne.setOtherType(personneDTO.getOtherType());
        personne.setDebutEtude(personneDTO.getDebutEtude());
        personne.setFinEtude(personneDTO.getFinEtude());
        personne.setDebutDirecteurEcole(personneDTO.getDebutDirecteurEcole());
        personne.setFinDirecteurEcole(personneDTO.getFinDirecteurEcole());
        personne.setDebutDirecteurMaison(personneDTO.getDebutDirecteurMaison());
        personne.setFinDirecteurMaison(personneDTO.getFinDirecteurMaison());
        personne.setEmail(personneDTO.getEmail());
        personne.setUsername(personneDTO.getUsername());
        personne.setPassword(personneDTO.getPassword());
        final Ecole ecole = personneDTO.getEcole() == null ? null : ecoleRepository.findById(personneDTO.getEcole())
                .orElseThrow(() -> new NotFoundException("ecole not found"));
        personne.setEcole(ecole);
        final Maison maison = personneDTO.getMaison() == null ? null : maisonRepository.findById(personneDTO.getMaison())
                .orElseThrow(() -> new NotFoundException("maison not found"));
        personne.setMaison(maison);
        return personne;
    }

}
