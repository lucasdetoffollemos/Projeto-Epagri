package com.example.projetoEpagri.Classes;

public class Dados {
    private String pastagem;
    private final double [] condicao;
    private int[] meses;
    private int total;

    public Dados() {
        condicao = new double[3];
        meses = new int[12];
    }

    public String getPastagem() {
        return pastagem;
    }

    public void setPastagem(String pastagem) {
        this.pastagem = pastagem;
    }

    public double getCondicao(int pos){
        return this.condicao[pos];
    }

    public void setCondicao(double valor, int pos){
        this.condicao[pos] = valor;
    }

    public int[] getMeses() {
        return meses;
    }

    public int getMeses(int pos){
        return this.meses[pos];
    }

    public void setMeses(int[] meses) {
        this.meses = meses;
    }

    public void setMeses(int valor, int pos){
        this.meses[pos] = valor;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
