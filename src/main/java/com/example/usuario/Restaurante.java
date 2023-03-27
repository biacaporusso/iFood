package com.example.usuario;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    public int getId() {
        return this.id;
    }

    @OneToMany(mappedBy = "restaurante")
    private List<ItemCardapio> itenscardapio;

    public void setId(int i) {
        this.id = i;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String n) {
        this.nome = n;
    }
}
