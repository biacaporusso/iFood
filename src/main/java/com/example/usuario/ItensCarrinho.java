package com.example.usuario;

public class ItensCarrinho extends ItemCardapio {

    private int quantidadeCarrinho;
    private double valorTotal;
    public ItensCarrinho(int i, String n, String d, double p) {
        this.id = i;
        this.nome = n;
        this.descricao = d;
        this.preco = p;
        this.quantidadeCarrinho = 1;
    }

    public int getQuantidadeCarrinho() {
        return quantidadeCarrinho;
    }

    public void setQuantidadeCarrinho(int quantidade) {
        this.quantidadeCarrinho = this.quantidadeCarrinho + quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valor) {
        this.valorTotal = valor;
    }
}
