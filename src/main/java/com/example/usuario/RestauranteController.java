package com.example.usuario;

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

    @GetMapping(value={"/index", "/"})
    public String mostrarListaRestaurantes(Model model) {
        model.addAttribute("listarestaurantes", restauranteRepository.findAll());
        return "index";
    }

//    @GetMapping("/index-cardapio/{id}")
//    public String mostrarCardapio(@PathVariable ("id") int id, Model model, HttpServletRequest request){
//        List<ItemCardapio> cardapio =
//                (List<ItemCardapio>) request.getSession().getAttribute(SESSION_CARDAPIO);
//        model.addAttribute("sessionCardapio",
//                !CollectionUtils.isEmpty(cardapio) ? cardapio : new ArrayList<>());
//        model.addAttribute("idRest", id);
//
//        return "index-cardapio";
//    }

}
