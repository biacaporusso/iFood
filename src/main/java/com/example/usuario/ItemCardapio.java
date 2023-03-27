package com.example.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name="item_cardapio")
public class ItemCardapio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @NotBlank(message = "O nome é obrigatório")
    protected String nome;
    protected String descricao;
    protected double preco;

    @ManyToOne
    @JoinColumn(name="id_restaurante")
    //@JoinColumn(insertable=false, nullable=false)
    protected Restaurante restaurante;

    public int getId() { return this.id; }
    public void setId(int i) { this.id = i; }
    public String getNome() { return this.nome; }
    public void setNome(String n) { this.nome = n; }
    public double getPreco() {
        return this.preco;
    }
    public void setPreco(double p) {
        this.preco = p;
    }
    public String getDescricao() {
        return this.descricao;
    }
    public void setDescricao(String d) {
        this.descricao = d;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }


    //public int getIdRestaurante () {
    //    return this.id_restaurante;
    //}
    //public void setIdRestaurante (int i) {
    //    this.id_restaurante = i;
    //}
}
