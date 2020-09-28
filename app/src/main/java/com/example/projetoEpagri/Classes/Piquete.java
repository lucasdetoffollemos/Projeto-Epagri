package com.example.projetoEpagri.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Piquete implements Parcelable {
    private String tipo;
    private String condicao;
    private double area;
    private double prodEstimada;
    private double mesesProd [];
    private int  total;
    private double totalColunaHa;
    private  double totaisMeses [];
    private double totaisEstacao[];

    public Piquete(){}

    public Piquete(String tipo, String condicao, double area, double prodEstimada, double mesesProd[], int total, double totalColunaHa, double totaisMeses[], double totaisEstacao[]){
        this.mesesProd = new double[12];
        this.totaisMeses = new double[12];
        this.totaisEstacao = new double[4];

        this.tipo = tipo;
        this.condicao = condicao;
        this.area = area;
        this.prodEstimada = prodEstimada;
        this.mesesProd = mesesProd;
        this.total = total;
        this.totalColunaHa = totalColunaHa;
        this.totaisMeses = totaisMeses;
        this.totaisEstacao = totaisEstacao;
    }

   private Piquete(Parcel p){
        tipo = p.readString();
        condicao = p.readString();
        area = p.readDouble();
        prodEstimada = p.readDouble();
        mesesProd = p.createDoubleArray();
        total = p.readInt();
        totalColunaHa = p.readDouble();
        totaisMeses = p.createDoubleArray();
        totaisEstacao = p.createDoubleArray();

   }


    public static final Creator<Piquete> CREATOR = new Creator<Piquete>() {
        @Override
        public Piquete createFromParcel(Parcel in) {
            return new Piquete(in);
        }

        @Override
        public Piquete[] newArray(int size) {
            return new Piquete[size];
        }
    };

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCondicao() {
        return condicao;
    }

    public void  setCondicao(String condicao) {
        this.condicao = condicao;
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

    public double[] getMeses() {
        return mesesProd;
    }

    public double getMeses(int posicao) {
        return mesesProd[posicao];
    }

    public void setMeses(double [] meses) {
        this.mesesProd = meses;
    }

    public void setMeses(int meses, int posicao) {
        this.mesesProd[posicao] = meses;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getTotalColunaHa() {
        return totalColunaHa;
    }

    public void setTotalColunaHa(double totalColunaHa) {
        this.totalColunaHa = totalColunaHa;
    }

    public double[] getTotaisMeses() {
        return totaisMeses;
    }

    public void setTotaisMeses(double[] totaisMeses) {
        this.totaisMeses = totaisMeses;
    }

    public double[] getTotaisEstacao() {
        return totaisEstacao;
    }

    public void setTotaisEstacao(double[] totaisEstacao) {
        this.totaisEstacao = totaisEstacao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipo);
        dest.writeString(condicao);
        dest.writeDouble(area);
        dest.writeDouble(prodEstimada);
        dest.writeDoubleArray(mesesProd);
        dest.writeInt(total);
        dest.writeDouble(totalColunaHa);
        dest.writeDoubleArray(totaisMeses);
        dest.writeDoubleArray(totaisEstacao);
    }
}
