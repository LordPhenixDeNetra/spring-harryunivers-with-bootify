package org.netrapp.harryunivers.service;

import java.util.List;
import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.domain.Maison;
import org.netrapp.harryunivers.domain.Personne;
import org.netrapp.harryunivers.model.MaisonDTO;
import org.netrapp.harryunivers.repos.EcoleRepository;
import org.netrapp.harryunivers.repos.MaisonRepository;
import org.netrapp.harryunivers.repos.PersonneRepository;
import org.netrapp.harryunivers.util.NotFoundException;
import org.netrapp.harryunivers.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MaisonService {

    private final MaisonRepository maisonRepository;
    private final EcoleRepository ecoleRepository;
    private final PersonneRepository personneRepository;

    public MaisonService(final MaisonRepository maisonRepository,
            final EcoleRepository ecoleRepository, final PersonneRepository personneRepository) {
        this.maisonRepository = maisonRepository;
        this.ecoleRepository = ecoleRepository;
        this.personneRepository = personneRepository;
    }

    public List<MaisonDTO> findAll() {
        final List<Maison> maisons = maisonRepository.findAll(Sort.by("id"));
        return maisons.stream()
                .map(maison -> mapToDTO(maison, new MaisonDTO()))
                .toList();
    }

    public MaisonDTO get(final Long id) {
        return maisonRepository.findById(id)
                .map(maison -> mapToDTO(maison, new MaisonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MaisonDTO maisonDTO) {
        final Maison maison = new Maison();
        mapToEntity(maisonDTO, maison);
        return maisonRepository.save(maison).getId();
    }

    public void update(final Long id, final MaisonDTO maisonDTO) {
        final Maison maison = maisonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(maisonDTO, maison);
        maisonRepository.save(maison);
    }

    public void delete(final Long id) {
        maisonRepository.deleteById(id);
    }

    private MaisonDTO mapToDTO(final Maison maison, final MaisonDTO maisonDTO) {
        maisonDTO.setId(maison.getId());
        maisonDTO.setUidMaison(maison.getUidMaison());
        maisonDTO.setNomMaison(maison.getNomMaison());
        maisonDTO.setHymneMaison(maison.getHymneMaison());
        maisonDTO.setDescriptionMaison(maison.getDescriptionMaison());
        maisonDTO.setEcole(maison.getEcole() == null ? null : maison.getEcole().getId());
        return maisonDTO;
    }

    private Maison mapToEntity(final MaisonDTO maisonDTO, final Maison maison) {
        maison.setUidMaison(maisonDTO.getUidMaison());
        maison.setNomMaison(maisonDTO.getNomMaison());
        maison.setHymneMaison(maisonDTO.getHymneMaison());
        maison.setDescriptionMaison(maisonDTO.getDescriptionMaison());
        final Ecole ecole = maisonDTO.getEcole() == null ? null : ecoleRepository.findById(maisonDTO.getEcole())
                .orElseThrow(() -> new NotFoundException("ecole not found"));
        maison.setEcole(ecole);
        return maison;
    }

    public String getReferencedWarning(final Long id) {
        final Maison maison = maisonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Personne maisonPersonne = personneRepository.findFirstByMaison(maison);
        if (maisonPersonne != null) {
            return WebUtils.getMessage("maison.personne.maison.referenced", maisonPersonne.getId());
        }
        return null;
    }

}
