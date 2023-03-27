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
public class CardapioController {

    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    ItemCardapioRepository cardapioRepository;

    private static final String SESSION_CARRINHO = "sessionCarrinho";
    private static final String SESSION_CARRINHO_TOTAL = "sessionCarrinhoTotal";

    @GetMapping("/index-cardapio/{id}")
    public String mostrarListaCardapio(@PathVariable("id") int id, Model model) {
        model.addAttribute("item", cardapioRepository.findByRestauranteId(id));
        model.addAttribute("idRest", id);
        return "index-cardapio";
    }

    @GetMapping("/carrinho")
    public String mostrarCarrinho(Model model, HttpServletRequest request){
        List<ItemCardapio> carrinho =
                (List<ItemCardapio>) request.getSession().getAttribute(SESSION_CARRINHO);
        model.addAttribute("sessionCarrinho",
                !CollectionUtils.isEmpty(carrinho) ? carrinho : new ArrayList<>());

        double valorTotal = (double) request.getSession().getAttribute(SESSION_CARRINHO_TOTAL);
        model.addAttribute("valorTotal", valorTotal);

        return "carrinho";
    }


    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarAoCarrinho (@PathVariable("id") int id, HttpServletRequest request) {

        ItemCardapio item = cardapioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("O id do item é inválido: " + id));
        List<ItensCarrinho> carrinho = (List<ItensCarrinho>)request.getSession().getAttribute(SESSION_CARRINHO);

        // se não tem nenhum item adicionado ao carrinho ainda, cria um novo carrinho
        if (carrinho == null) {
            carrinho = new ArrayList<ItensCarrinho>();
        }

        // se o carrinho já contem o item, incrementa +1 na quantidade de produtos adicionados
        int jaExiste = 0;
        for (ItensCarrinho pt : carrinho) {
            if (pt.getId() == item.getId()) {
                jaExiste = 1;
                pt.setQuantidadeCarrinho(1);
                break;
            }
        }
        // se ainda não tem o item no carrinho
        if (jaExiste == 0) {
            ItensCarrinho pdc = new ItensCarrinho(item.getId(), item.getNome(), item.getDescricao(), item.getPreco(), item.getRestaurante().getNome());
            carrinho.add(pdc);
        }

        // calcula o valor total do carrinho
        double valorTotalCarrinho = 0;
        for (ItensCarrinho pt : carrinho) {
            valorTotalCarrinho = valorTotalCarrinho + (pt.getQuantidadeCarrinho() * pt.getPreco());
            pt.setValorTotal(valorTotalCarrinho);
        }


        request.getSession().setAttribute(SESSION_CARRINHO, carrinho);
        request.getSession().setAttribute(SESSION_CARRINHO_TOTAL, valorTotalCarrinho);

        return "redirect:/carrinho";

    }

    @GetMapping("/carrinho/remover/{id}")
    public String removerDoCarrinho(@PathVariable("id") int id, HttpServletRequest request) {

        List<ItensCarrinho> sessionCarrinho =
                (List<ItensCarrinho>) request.getSession().getAttribute(SESSION_CARRINHO);


        ItemCardapio item = cardapioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "O id do item é inválido: " + id));


        for (ItensCarrinho pt : sessionCarrinho) {
            if (pt.getId() == item.getId()) {
                sessionCarrinho.remove(pt);
                break;
            }
        }


        request.getSession().setAttribute(SESSION_CARRINHO, sessionCarrinho);

        return "redirect:/carrinho";
    }


    @GetMapping("/desincrementar/{id}")
    public String desincrementarItemCarrinho (@PathVariable("id") int id, HttpServletRequest request) {

        ItemCardapio item = cardapioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("O id do item é inválido: " + id));

        List<ItensCarrinho> carrinho = (List<ItensCarrinho>)request.getSession().getAttribute(SESSION_CARRINHO);

        // se não tem nenhum item adicionado ao carrinho ainda, cria um novo carrinho
        if (carrinho == null) {
            carrinho = new ArrayList<ItensCarrinho>();
        }

        for (ItensCarrinho pt : carrinho) {
            if (pt.getId() == item.getId()) {
                // se a quantidade no carrinho for maior que um, desincrementa 1. Se for igual a 1, remove.
                if (pt.getQuantidadeCarrinho() > 1) {
                    pt.setQuantidadeCarrinho(-1);
                } else if (pt.getQuantidadeCarrinho() == 1){
                    removerDoCarrinho(pt.getId(), request);
                }
                break;
            }
        }

        request.getSession().setAttribute(SESSION_CARRINHO, carrinho);

        return "redirect:/carrinho";

    }

}
