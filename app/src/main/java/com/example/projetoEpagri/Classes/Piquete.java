package com.example.projetoEpagri.Classes;

import java.io.Serializable;

public class Piquete {
    private String tipo;
    private double condicao [];
    private double condSelecionada;
    private double area;
    private double prodEstimada;
    private int meses [];
    private int total;

    public Piquete(){}

    public Piquete (String tipo, double condicao[], int meses[], int total){
        this.condicao = new double[3];
        this.meses = new int[12];

        this.tipo = tipo;
        this.condicao = condicao;
//        this.condSelecionada = condSelecionada;
//        this.area = area;
//        this.prodEstimada = prodEstimada;
        this.meses = meses;
        this.total = total;
    }


    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public double[] getCondicao() {
        return condicao;
    }

    public double getCondicao(int posicao) {
        return condicao[posicao];
    }

    public void  setCondicao(double[]  condicao) {
        this.condicao = condicao;
    }

    public void setCondicao(double condicao, int posicao) {
        this.condicao[posicao] = condicao;
    }

    public double getCondSelecionada() {
        return condSelecionada;
    }

    public void setCondSelecionada(double condSelecionada) {
        this.condSelecionada = condSelecionada;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getProdEstimada() {
        return prodEstimada;
    }

    public void setProdEstimada(double prodEstimada) {
        this.prodEstimada = prodEstimada;
    }

    public int[] getMeses() {
        return meses;
    }

    public int getMeses(int posicao) {
        return meses[posicao];
    }

    public void setMeses(int [] meses) {
        this.meses = meses;
    }

    public void setMeses(int meses, int posicao) {
        this.meses[posicao] = meses;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
