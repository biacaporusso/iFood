package com.example.adm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
