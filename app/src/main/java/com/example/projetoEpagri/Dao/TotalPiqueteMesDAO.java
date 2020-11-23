package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;

import java.util.ArrayList;

public class TotalPiqueteMesDAO implements ITotalPiqueteMes {
    SQLiteDatabase bancoDeDados;

    public TotalPiqueteMesDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir o total produzido em cada mês no banco de dados (tabela total_piquete_mes_atual).
     * @param listaTotaisMes ArrayList com os totais de cada mês.
     * @param idPropriedade Id da propriedade.
     */
    public void inserirTotalMes(ArrayList<Double> listaTotaisMes, int idPropriedade, String NOME_TABELA){
        ContentValues values = new ContentValues();
        values.put(COLUNA_TOTAL_JAN, listaTotaisMes.get(0));
        values.put(COLUNA_TOTAL_FEV, listaTotaisMes.get(1));
        values.put(COLUNA_TOTAL_MAR, listaTotaisMes.get(2));
        values.put(COLUNA_TOTAL_ABR, listaTotaisMes.get(3));
        values.put(COLUNA_TOTAL_MAI, listaTotaisMes.get(4));
        values.put(COLUNA_TOTAL_JUN, listaTotaisMes.get(5));
        values.put(COLUNA_TOTAL_JUL, listaTotaisMes.get(6));
        values.put(COLUNA_TOTAL_AGO, listaTotaisMes.get(7));
        values.put(COLUNA_TOTAL_SET, listaTotaisMes.get(8));
        values.put(COLUNA_TOTAL_OUT, listaTotaisMes.get(9));
        values.put(COLUNA_TOTAL_NOV, listaTotaisMes.get(10));
        values.put(COLUNA_TOTAL_DEZ, listaTotaisMes.get(11));
        values.put(COLUNA_ID_PROPRIEDADE, idPropriedade);

        this.bancoDeDados.insert(NOME_TABELA, null, values);
    }

    /**
     * Método para recuperar os totais de todos os meses de determinada propriedade baseado no seu id.
     * @param idPropriedade Id da propriedade.
     * @return Array com os totais de todos os meses da propriedade.
     */
    public ArrayList<Double> getTotalMesByPropId(int idPropriedade, String NOME_TABELA){
        String sql_query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_ID_PROPRIEDADE + "=\"" + idPropriedade + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        ArrayList<Double> totais = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()){
            totais.add(cursor.getDouble(1));
            totais.add(cursor.getDouble(2));
            totais.add(cursor.getDouble(3));
            totais.add(cursor.getDouble(4));
            totais.add(cursor.getDouble(5));
            totais.add(cursor.getDouble(6));
            totais.add(cursor.getDouble(7));
            totais.add(cursor.getDouble(8));
            totais.add(cursor.getDouble(9));
            totais.add(cursor.getDouble(10));
            totais.add(cursor.getDouble(11));
            totais.add(cursor.getDouble(12));
        }
        cursor.close();
        return totais;
    }

    /**
     * Método para remover todos os totais de uma propriedade baseado no id.
     * @param idPropriedade Id da propriedade a ser removida.
     */
    public void deleteTotalMesByPropId(int idPropriedade, String NOME_TABELA){
        this.bancoDeDados.delete(NOME_TABELA,COLUNA_ID_PROPRIEDADE + "=" + idPropriedade, null);
    }
}
