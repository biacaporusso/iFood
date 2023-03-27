package com.example.adm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RestauranteController {

    private static final String SESSION_CARDAPIO = "sessionCardapio";

    @Autowired
    RestauranteRepository restauranteRepository;

    @GetMapping("/novo-restaurante")
    public String mostrarFormNovoRestaurante(Restaurante restaurante){
        return "novo-restaurante";
    }

    @GetMapping(value={"/index", "/"})
    public String mostrarListaRestaurantes(Model model) {
        model.addAttribute("listarestaurantes", restauranteRepository.findAll());
        return "index";
    }

    @PostMapping("/adicionar-restaurante")
    public String adicionarRestaurante(@Valid Restaurante restaurante,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "/novo-restaurante";
        }

        restauranteRepository.save(restaurante);
        return "redirect:/index";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormAtualizar(@PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "O id do restaurante é inválido:" + id));


        model.addAttribute("restaurante", restaurante);
        return "atualizar-restaurante";
    }



    @PostMapping("/atualizar-restaurante/{id}")
    public String atualizarRestaurante(@PathVariable("id") int id, @Valid Restaurante restaurante,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            restaurante.setId(id);
            return "atualizar-restaurante";
        }

        restauranteRepository.save(restaurante);
        return "redirect:/index";
    }

    @GetMapping("/remover/{id}")
    public String removerRestaurante(@PathVariable("id") int id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do restaurante é inválido:" + id));

        restauranteRepository.delete(restaurante);
        return "redirect:/index";
    }

    @GetMapping("/index-cardapio")
    public String mostrarCardapio(Model model, HttpServletRequest request){
        List<ItemCardapio> cardapio =
                (List<ItemCardapio>) request.getSession().getAttribute(SESSION_CARDAPIO);
        model.addAttribute("sessionCardapio",
                !CollectionUtils.isEmpty(cardapio) ? cardapio : new ArrayList<>());


        return "index-cardapio";
    }


}
