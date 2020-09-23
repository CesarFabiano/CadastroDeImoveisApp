package com.example.trabalhofinal.model;

import java.text.DecimalFormat;

public class Imovel {
    public double preco;
    public double metragem;
    public String tipo;
    public String endereco;
    public String imagem;
    public String descricao;
    private String key;

    public Imovel(){
    }

    public Imovel(double preco, double metragem, String tipo, String endereco, String imagem, String descricao) {
        this.preco = preco;
        this.metragem = metragem;
        this.tipo = tipo;
        this.endereco = endereco;
        this.imagem = imagem;
        this.descricao = descricao;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
