package com.example.projetoEpagri;

import java.io.Serializable;

public class Pastagem implements Serializable {

    String nome;
    double condicao [];
    int meses [];
    int total;





    public Pastagem(String nome, double condicao[], int meses[], int total){
        condicao = new double[3];
        meses = new int[12];

        this.condicao = condicao;
        this.meses = meses;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double[] getCondicao() {
        return condicao;
    }

    public void setCondicao(double[] condicao) {
        this.condicao = condicao;
    }

    public int[] getMeses() {
        return meses;
    }

    public void setMeses(int[] meses) {
        this.meses = meses;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
