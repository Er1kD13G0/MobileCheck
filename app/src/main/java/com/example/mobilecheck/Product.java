package com.example.mobilecheck;

public class Product {
    private String id; // Para armazenar o ID do documento
    private String nome; // Nome do produto
    private int quantidade; // Quantidade do produto

    // Construtor com parâmetros
    public Product(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}