package com.example.projetoEpagri;

import java.io.Serializable;

public class Pastagem implements Serializable {


    String tipo;
    double condicao [];
    int meses [];
    int total;




    public Pastagem(String tipo, double condicao[], int meses[], int total){

        this.condicao = new double[3];
        this.meses = new int[12];

        this.tipo = tipo;
        this.condicao = condicao;
        this.meses = meses;
        this.total = total;
    }


    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getCondicao(int posicao) {
        return condicao[posicao];
    }

    public void setCondicao(double condicao, int posicao) {
        this.condicao[posicao] = condicao;
    }

    public int getMeses(int posicao) {
        return meses[posicao];
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
