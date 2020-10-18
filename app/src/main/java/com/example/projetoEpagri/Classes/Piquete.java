package com.example.projetoEpagri.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Piquete implements Parcelable {
    private String tipo;
    private String condicao;
    private double area;
    private double prodEstimada;
    private double mesesProd [];
    private int  total;

    public Piquete(){}

    public Piquete(String tipo, String condicao, double area, double prodEstimada, double mesesProd[], int total){
        this.mesesProd = new double[12];

        this.tipo = tipo;
        this.condicao = condicao;
        this.area = area;
        this.prodEstimada = prodEstimada;
        this.mesesProd = mesesProd;
        this.total = total;
    }

   private Piquete(Parcel p){
        tipo = p.readString();
        condicao = p.readString();
        area = p.readDouble();
        prodEstimada = p.readDouble();
        mesesProd = p.createDoubleArray();
        total = p.readInt();
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

    public void setMeses(double meses, int posicao) {
        this.mesesProd[posicao] = meses;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
    }
}
