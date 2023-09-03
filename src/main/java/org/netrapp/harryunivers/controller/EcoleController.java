package org.netrapp.harryunivers.controller;

import jakarta.validation.Valid;
import org.netrapp.harryunivers.model.EcoleDTO;
import org.netrapp.harryunivers.service.EcoleService;
import org.netrapp.harryunivers.util.WebUtils;
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
@RequestMapping("/ecoles")
public class EcoleController {

    private final EcoleService ecoleService;

    public EcoleController(final EcoleService ecoleService) {
        this.ecoleService = ecoleService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("ecoles", ecoleService.findAll());
        return "ecole/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("ecole") final EcoleDTO ecoleDTO) {
        return "ecole/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("ecole") @Valid final EcoleDTO ecoleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ecole/add";
        }
        ecoleService.create(ecoleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("ecole.create.success"));
        return "redirect:/ecoles";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("ecole", ecoleService.get(id));
        return "ecole/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("ecole") @Valid final EcoleDTO ecoleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ecole/edit";
        }
        ecoleService.update(id, ecoleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("ecole.update.success"));
        return "redirect:/ecoles";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = ecoleService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            ecoleService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("ecole.delete.success"));
        }
        return "redirect:/ecoles";
    }

}
