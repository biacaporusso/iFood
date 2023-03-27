package com.example.usuario;

public class ItensCarrinho extends ItemCardapio {

    private int quantidadeCarrinho;
    private double valorTotal;

    private String nomeRestaurante;

    public ItensCarrinho(int i, String n, String d, double p, String nomeRest) {
        this.id = i;
        this.nome = n;
        this.descricao = d;
        this.preco = p;
        this.quantidadeCarrinho = 1;
        this.nomeRestaurante = nomeRest;
    }

    public int getQuantidadeCarrinho() {
        return quantidadeCarrinho;
    }

    public void setQuantidadeCarrinho(int quantidade) {
        this.quantidadeCarrinho = this.quantidadeCarrinho + quantidade;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(double valor) {
        this.valorTotal = valor;
    }

    public String getNomeRestaurante() {
        return this.nomeRestaurante;
    }

    public void setNomeRestaurante(String n) {
        this.nomeRestaurante = n;
    }
}

