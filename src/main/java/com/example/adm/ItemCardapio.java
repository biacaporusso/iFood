package com.example.adm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="item_cardapio")
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private String descricao;
    private double preco;

    @ManyToOne
    @JoinColumn(name="id_restaurante")
    //@JoinColumn(insertable=false, nullable=false)
    private Restaurante restaurante;

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
