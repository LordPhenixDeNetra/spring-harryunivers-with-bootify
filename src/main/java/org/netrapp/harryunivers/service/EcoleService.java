package org.netrapp.harryunivers.service;

import java.util.List;
import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.domain.Maison;
import org.netrapp.harryunivers.domain.Personne;
import org.netrapp.harryunivers.model.EcoleDTO;
import org.netrapp.harryunivers.repos.EcoleRepository;
import org.netrapp.harryunivers.repos.MaisonRepository;
import org.netrapp.harryunivers.repos.PersonneRepository;
import org.netrapp.harryunivers.util.NotFoundException;
import org.netrapp.harryunivers.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EcoleService {

    private final EcoleRepository ecoleRepository;
    private final PersonneRepository personneRepository;
    private final MaisonRepository maisonRepository;

    public EcoleService(final EcoleRepository ecoleRepository,
            final PersonneRepository personneRepository, final MaisonRepository maisonRepository) {
        this.ecoleRepository = ecoleRepository;
        this.personneRepository = personneRepository;
        this.maisonRepository = maisonRepository;
    }

    public List<EcoleDTO> findAll() {
        final List<Ecole> ecoles = ecoleRepository.findAll(Sort.by("id"));
        return ecoles.stream()
                .map(ecole -> mapToDTO(ecole, new EcoleDTO()))
                .toList();
    }

    public EcoleDTO get(final Long id) {
        return ecoleRepository.findById(id)
                .map(ecole -> mapToDTO(ecole, new EcoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EcoleDTO ecoleDTO) {
        final Ecole ecole = new Ecole();
        mapToEntity(ecoleDTO, ecole);
        return ecoleRepository.save(ecole).getId();
    }

    public void update(final Long id, final EcoleDTO ecoleDTO) {
        final Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ecoleDTO, ecole);
        ecoleRepository.save(ecole);
    }

    public void delete(final Long id) {
        ecoleRepository.deleteById(id);
    }

    private EcoleDTO mapToDTO(final Ecole ecole, final EcoleDTO ecoleDTO) {
        ecoleDTO.setId(ecole.getId());
        ecoleDTO.setUidEcole(ecole.getUidEcole());
        ecoleDTO.setNomEcole(ecole.getNomEcole());
        ecoleDTO.setDescriptionEcole(ecole.getDescriptionEcole());
        ecoleDTO.setLieuEcole(ecole.getLieuEcole());
        return ecoleDTO;
    }

    private Ecole mapToEntity(final EcoleDTO ecoleDTO, final Ecole ecole) {
        ecole.setUidEcole(ecoleDTO.getUidEcole());
        ecole.setNomEcole(ecoleDTO.getNomEcole());
        ecole.setDescriptionEcole(ecoleDTO.getDescriptionEcole());
        ecole.setLieuEcole(ecoleDTO.getLieuEcole());
        return ecole;
    }

    public String getReferencedWarning(final Long id) {
        final Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Personne ecolePersonne = personneRepository.findFirstByEcole(ecole);
        if (ecolePersonne != null) {
            return WebUtils.getMessage("ecole.personne.ecole.referenced", ecolePersonne.getId());
        }
        final Maison ecoleMaison = maisonRepository.findFirstByEcole(ecole);
        if (ecoleMaison != null) {
            return WebUtils.getMessage("ecole.maison.ecole.referenced", ecoleMaison.getId());
        }
        return null;
    }

}
