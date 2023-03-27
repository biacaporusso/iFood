package com.example.adm;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CardapioController {

    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    ItemCardapioRepository cardapioRepository;

    @GetMapping("/novo-cardapio/{id}")
    public String mostrarFormNovoCardapio(ItemCardapio item, @PathVariable("id") int id, Model model){

        model.addAttribute("item", item);
        model.addAttribute("id", id);
        return "novo-cardapio";
    }

    @GetMapping("/index-cardapio/{id}")
    public String mostrarListaCardapio(@PathVariable("id") int id, Model model) {
        model.addAttribute("item", cardapioRepository.findByRestauranteId(id));
        model.addAttribute("id", id);
        return "index-cardapio";
    }

    @PostMapping("/adicionar-cardapio/{id}")
    public String adicionarCardapio(@Valid ItemCardapio item, @PathVariable("id") int id, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "redirect:/novo-cardapio/" + id;
        }

        Restaurante r = restauranteRepository.findById(id).get();
        item.setRestaurante(r);

        cardapioRepository.save(item);
        return "redirect:/index-cardapio/" + id;
    }

    @GetMapping("/editar-item/{id}")
    public String mostrarFormAtualizarItem(@PathVariable("id") int id, Model model) {
        ItemCardapio cardapio = cardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "O id do cardapio é inválido:" + id));


        model.addAttribute("cardapio", cardapio);
        return "atualizar-cardapio";
    }

    @PostMapping("/atualizar-cardapio/{id}")
    public String atualizarCardapio(@PathVariable("id") int id, @Valid ItemCardapio cardapio,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            cardapio.setId(id);
            return "redirect:/editar-item/" + id;
        }

        cardapio.setRestaurante(cardapioRepository.findById(id).get().getRestaurante());
        cardapioRepository.save(cardapio);
        return "redirect:/index-cardapio/" + cardapio.getRestaurante().getId();
    }

    @GetMapping("/remover-item/{id}")
    public String removerItemCardapio(@PathVariable("id") int id) {
        ItemCardapio cardapio = cardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do cardapio é inválido:" + id));
        cardapioRepository.delete(cardapio);
        return "redirect:/index-cardapio/" + cardapio.getRestaurante().getId();
    }


}
