package com.example.projetoEpagri.Classes;

public class Animais {

    private String categoria;
    private double consumo;
    private int numAnimais;
    private String entradaMes;
    private double pesoInicial;
    private double pesoFinal;
    private double pesoGanhoVer;
    private double pesoGanhoOut;
    private double pesoGanhoInv;
    private double pesoGanhoPrim;
    private double [] meses;

    public Animais(){}



    public  Animais(String categoria, double consumo, int numAnimais, String entradaMes, double pesoInicial, double pesoFinal, double pesoGanhoVer, double pesoGanhoOut, double pesoGanhoInv, double pesoGanhoPrim, double [] meses){
        this.meses = new double[12];

        this.categoria = categoria;
        this.consumo = consumo;
        this.numAnimais = numAnimais;
        this.entradaMes = entradaMes;
        this.pesoInicial = pesoInicial;
        this.pesoFinal = pesoFinal;
        this.pesoGanhoVer = pesoGanhoVer;
        this.pesoGanhoOut = pesoGanhoOut;
        this.pesoGanhoInv = pesoGanhoInv;
        this.pesoGanhoPrim = pesoGanhoPrim;
        this.meses = meses;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public int getNumAnimais() {
        return numAnimais;
    }

    public void setNumAnimais(int numAnimais) {
        this.numAnimais = numAnimais;
    }

    public String getEntradaMes() {
        return entradaMes;
    }

    public void setEntradaMes(String entradaMes) {
        this.entradaMes = entradaMes;
    }

    public double getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(double pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public double getPesoFinal() {
        return pesoFinal;
    }

    public void setPesoFinal(double pesoFinal) {
        this.pesoFinal = pesoFinal;
    }

    public double getPesoGanhoVer() {
        return pesoGanhoVer;
    }

    public void setPesoGanhoVer(double pesoGanhoVer) {
        this.pesoGanhoVer = pesoGanhoVer;
    }

    public double getPesoGanhoOut() {
        return pesoGanhoOut;
    }

    public void setPesoGanhoOut(double pesoGanhoOut) {
        this.pesoGanhoOut = pesoGanhoOut;
    }

    public double getPesoGanhoInv() {
        return pesoGanhoInv;
    }

    public void setPesoGanhoInv(double pesoGanhoInv) {
        this.pesoGanhoInv = pesoGanhoInv;
    }

    public double getPesoGanhoPrim() {
        return pesoGanhoPrim;
    }

    public void setPesoGanhoPrim(double pesoGanhoPrim) {
        this.pesoGanhoPrim = pesoGanhoPrim;
    }

    public double[] getMeses() {
        return meses;
    }

    public void setMeses(double[] meses) {
        this.meses = meses;
    }
}
