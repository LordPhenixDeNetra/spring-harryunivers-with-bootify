package org.netrapp.harryunivers.controller;

import jakarta.validation.Valid;
import org.netrapp.harryunivers.domain.Ecole;
import org.netrapp.harryunivers.model.MaisonDTO;
import org.netrapp.harryunivers.repos.EcoleRepository;
import org.netrapp.harryunivers.service.MaisonService;
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
@RequestMapping("/maisons")
public class MaisonController {

    private final MaisonService maisonService;
    private final EcoleRepository ecoleRepository;

    public MaisonController(final MaisonService maisonService,
            final EcoleRepository ecoleRepository) {
        this.maisonService = maisonService;
        this.ecoleRepository = ecoleRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("ecoleValues", ecoleRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Ecole::getId, Ecole::getUidEcole)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("maisons", maisonService.findAll());
        return "maison/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("maison") final MaisonDTO maisonDTO) {
        return "maison/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("maison") @Valid final MaisonDTO maisonDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "maison/add";
        }
        maisonService.create(maisonDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("maison.create.success"));
        return "redirect:/maisons";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("maison", maisonService.get(id));
        return "maison/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("maison") @Valid final MaisonDTO maisonDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "maison/edit";
        }
        maisonService.update(id, maisonDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("maison.update.success"));
        return "redirect:/maisons";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = maisonService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            maisonService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("maison.delete.success"));
        }
        return "redirect:/maisons";
    }

}
