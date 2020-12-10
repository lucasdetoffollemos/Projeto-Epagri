package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.Classes.Dados;

import java.util.ArrayList;

public class DadosDAO implements IDadosSchema {
    private final SQLiteDatabase bancoDeDados;

    public DadosDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir uma pastagem no banco de dados (tabela dados_sul).
     */
    public void inserirPastagem(String pastagem, double condicaoDegradada, double condicaoMedia, double condicaoOtima, int [] meses, int total, String nomeTabela){
        ContentValues values = new ContentValues();
        values.put(COLUNA_TIPO, pastagem);
        values.put(COLUNA_CONDICAO_DEGRADADA, condicaoDegradada);
        values.put(COLUNA_CONDICAO_MEDIA, condicaoMedia);
        values.put(COLUNA_CONDICAO_OTIMA, condicaoOtima);
        values.put(COLUNA_JAN, meses[0]);
        values.put(COLUNA_FEV, meses[1]);
        values.put(COLUNA_MAR, meses[2]);
        values.put(COLUNA_ABR, meses[3]);
        values.put(COLUNA_MAI, meses[4]);
        values.put(COLUNA_JUN, meses[5]);
        values.put(COLUNA_JUL, meses[6]);
        values.put(COLUNA_AGO, meses[7]);
        values.put(COLUNA_SET, meses[8]);
        values.put(COLUNA_OUT, meses[9]);
        values.put(COLUNA_NOV, meses[10]);
        values.put(COLUNA_DEZ, meses[11]);
        values.put(COLUNA_TOTAL_PASTAGEM, total);

        this.bancoDeDados.insert(nomeTabela, null, values);
    }

    /**
     * Método para recuperar todas as pastagens.
     * @return ArrayList de String com as pastagens.
     */
    public ArrayList<Dados> getPastagem(String nomeTabela) {
        ArrayList<Dados> arrayList = new ArrayList<>();

        String sql_query = "SELECT * FROM " + nomeTabela;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        while(cursor.moveToNext()){
            Dados dado = new Dados();
            dado.setPastagem(cursor.getString(0));
            dado.setCondicao(cursor.getDouble(1), 0);
            dado.setCondicao(cursor.getDouble(2), 1);
            dado.setCondicao(cursor.getDouble(3), 2);
            dado.setMeses(cursor.getInt(4), 0);
            dado.setMeses(cursor.getInt(5), 1);
            dado.setMeses(cursor.getInt(6), 2);
            dado.setMeses(cursor.getInt(7), 3);
            dado.setMeses(cursor.getInt(8), 4);
            dado.setMeses(cursor.getInt(9), 5);
            dado.setMeses(cursor.getInt(10), 6);
            dado.setMeses(cursor.getInt(11), 7);
            dado.setMeses(cursor.getInt(12), 8);
            dado.setMeses(cursor.getInt(13), 9);
            dado.setMeses(cursor.getInt(14), 10);
            dado.setMeses(cursor.getInt(15), 11);
            dado.setTotal(cursor.getInt(16));
            arrayList.add(dado);
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Método para recuperar todos os tipos de pastagem.
     * @return ArrayList de String com os tipos de pastagem.
     */
    public ArrayList<String> getTiposPastagem(String nomeTabela) {
        ArrayList<String> arrayList = new ArrayList<>();

        String sql_query = "SELECT * FROM " + nomeTabela;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUNA_TIPO)));
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Método responsável por recuperar o valor numérico da condição baseado nos valores "degradada", "média" e "ótima".
     * @param tipo Tipo da pastagem.
     * @param condicao Condição da pastagem (degradada, média ou ótima).
     * @return Valor númerico da condição.
     */
    public Double getCondicao(String tipo, String condicao, String nomeTabela) {
        double valorCondicao = 1.0;
        int posicaoColuna = 1;

        String sql_query = "SELECT * FROM " + nomeTabela + " WHERE " + COLUNA_TIPO + " = '" + tipo +"'";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        switch (condicao) {
            case "Degradada":
                posicaoColuna = 1;
                break;
            case "Média":
                posicaoColuna = 2;
                break;
            case "Ótima":
                posicaoColuna = 3;
                break;
        }

        while (cursor.moveToNext()) {
            valorCondicao = (cursor.getDouble(posicaoColuna));
        }
        cursor.close();
        return valorCondicao;
    }

    /**
     * Método responsável por recuperar o valor numérico do mês para certo tipo de pastagem na tabela dados_sul.
     * @param mes Mês do ano.
     * @param tipo Tipo da pastagem.
     * @return Valor númerico do mês.
     */
    public int getMeses(int mes, String tipo, String nomeTabela){
        int posicaoColuna;
        int valorDoMes = 1;

        String sql_query = "SELECT * FROM " + nomeTabela + " WHERE "+ COLUNA_TIPO + " = '"+ tipo +"'";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        switch (mes){
            case 1: posicaoColuna = 4; break;
            case 2: posicaoColuna = 5; break;
            case 3: posicaoColuna = 6; break;
            case 4: posicaoColuna = 7; break;
            case 5: posicaoColuna = 8; break;
            case 6: posicaoColuna = 9; break;
            case 7: posicaoColuna = 10; break;
            case 8: posicaoColuna = 11; break;
            case 9: posicaoColuna = 12; break;
            case 10: posicaoColuna = 13; break;
            case 11: posicaoColuna = 14; break;
            case 12: posicaoColuna = 15; break;
            default: posicaoColuna = 1; break;
        }

        while (cursor.moveToNext()){
            valorDoMes = (cursor.getInt(posicaoColuna));
        }
        cursor.close();
        return  valorDoMes;
    }

    public void deleteAllDados(String nomeTabela){
        this.bancoDeDados.execSQL("DELETE from " + nomeTabela);
    }
}
