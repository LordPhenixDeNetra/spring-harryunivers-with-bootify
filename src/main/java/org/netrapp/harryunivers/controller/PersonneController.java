package org.netrapp.harryunivers.controller;

import jakarta.validation.Valid;
import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.domain.Maison;
import org.netrapp.harryunivers.model.OtherType;
import org.netrapp.harryunivers.model.PersonneDTO;
import org.netrapp.harryunivers.model.PersonneType;
import org.netrapp.harryunivers.model.SexeType;
import org.netrapp.harryunivers.repos.EcoleRepository;
import org.netrapp.harryunivers.repos.MaisonRepository;
import org.netrapp.harryunivers.service.PersonneService;
import org.netrapp.harryunivers.util.CustomCollectors;
import org.netrapp.harryunivers.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/personnes")
public class PersonneController {

    private final PersonneService personneService;
    private final EcoleRepository ecoleRepository;
    private final MaisonRepository maisonRepository;

    public PersonneController(final PersonneService personneService,
            final EcoleRepository ecoleRepository, final MaisonRepository maisonRepository) {
        this.personneService = personneService;
        this.ecoleRepository = ecoleRepository;
        this.maisonRepository = maisonRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("sexePersonneValues", SexeType.values());
        model.addAttribute("personneTypeValues", PersonneType.values());
        model.addAttribute("otherTypeValues", OtherType.values());
        model.addAttribute("ecoleValues", ecoleRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Ecole::getId, Ecole::getUidEcole)));
        model.addAttribute("maisonValues", maisonRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Maison::getId, Maison::getUidMaison)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("personnes", personneService.findAll());
        return "personne/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("personne") final PersonneDTO personneDTO) {
        return "personne/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("personne") @Valid final PersonneDTO personneDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "personne/add";
        }
        personneService.create(personneDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("personne.create.success"));
        return "redirect:/personnes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("personne", personneService.get(id));
        return "personne/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("personne") @Valid final PersonneDTO personneDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "personne/edit";
        }
        personneService.update(id, personneDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("personne.update.success"));
        return "redirect:/personnes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        personneService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("personne.delete.success"));
        return "redirect:/personnes";
    }

}
