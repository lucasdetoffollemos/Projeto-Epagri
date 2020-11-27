package com.example.projetoEpagri.Classes;

import java.util.ArrayList;

public class Propriedade {
    private String nome, regiao;
    private double area;
    private int qtdeAnimais;
    //Número de piquetes?
    //Número de animais?
    private ArrayList<Piquete> listaPiqueteAtual;
    private ArrayList<Animais> listaAnimaisAtual;

    public Propriedade(){}

    public Propriedade(String nome, String regiao, double area, int qtdeAnimais, ArrayList<Piquete> listaPiqueteAtual, ArrayList<Animais> listaAnimaisAtual) {
        this.nome = nome;
        this.regiao = regiao;
        this.area = area;
        this.qtdeAnimais = qtdeAnimais;
        this.listaPiqueteAtual = listaPiqueteAtual;
        this.listaAnimaisAtual = listaAnimaisAtual;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getQtdeAnimais() {
        return qtdeAnimais;
    }

    public void setQtdeAnimais(int qtdeAnimais) {
        this.qtdeAnimais = qtdeAnimais;
    }

    public ArrayList<Piquete> getListaPiqueteAtual() {
        return listaPiqueteAtual;
    }

    public void setListaPiqueteAtual(ArrayList<Piquete> listaPiqueteAtual) {
        this.listaPiqueteAtual = listaPiqueteAtual;
    }

    public ArrayList<Animais> getListaAnimaisAtual() {
        return listaAnimaisAtual;
    }

    public void setListaAnimaisAtual(ArrayList<Animais> listaAnimaisAtual) {
        this.listaAnimaisAtual = listaAnimaisAtual;
    }
}
