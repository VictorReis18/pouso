package com.pouso.controller;

import com.pouso.repository.AdocaoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdocaoController {

    private final AdocaoRepository adocaoRepository;

    public AdocaoController(AdocaoRepository adocaoRepository) {
        this.adocaoRepository = adocaoRepository;
    }

    @GetMapping("/adocoes")
    public String minhasAdocoes(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String cpf = (String) session.getAttribute("cpf");
        if (cpf == null) {
            redirectAttributes.addFlashAttribute("error", "Faça login para ver suas adoções.");
            return "redirect:/login";
        }

        model.addAttribute("solicitacoesRealizadas", adocaoRepository.listarRealizadas(cpf));
        model.addAttribute("solicitacoesRecebidas", adocaoRepository.listarRecebidas(cpf));
        return "adocoes";
    }
}
