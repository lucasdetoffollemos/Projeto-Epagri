package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.Classes.Propriedade;

import java.util.ArrayList;

public class PiqueteDAO implements IPiqueteSchema{
    SQLiteDatabase bancoDeDados;

    public PiqueteDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir um piquete no banco de dados (tabela piquetes_atual).
     * @param p Representa o objeto do Piquete.
     * @param idPropriedade Representa o id da propriedade a qual pertencem os piquetes.
     */
    public void inserirPiquete(Piquete p, int idPropriedade){
        ContentValues values = new ContentValues();
        values.put(COLUNA_TIPO, p.getTipo());
        values.put(COLUNA_CONDICAO, p.getCondicao());
        values.put(COLUNA_AREA, p.getArea());
        values.put(COLUNA_PRODUCAO_ESTIMADA, p.getProdEstimada());
        values.put(COLUNA_PRODUCAO_JAN, p.getMeses(0));
        values.put(COLUNA_PRODUCAO_FEV, p.getMeses(1));
        values.put(COLUNA_PRODUCAO_MAR, p.getMeses(2));
        values.put(COLUNA_PRODUCAO_ABR, p.getMeses(3));
        values.put(COLUNA_PRODUCAO_MAI, p.getMeses(4));
        values.put(COLUNA_PRODUCAO_JUN, p.getMeses(5));
        values.put(COLUNA_PRODUCAO_JUL, p.getMeses(6));
        values.put(COLUNA_PRODUCAO_AGO, p.getMeses(7));
        values.put(COLUNA_PRODUCAO_SET, p.getMeses(8));
        values.put(COLUNA_PRODUCAO_OUT, p.getMeses(9));
        values.put(COLUNA_PRODUCAO_NOV, p.getMeses(10));
        values.put(COLUNA_PRODUCAO_DEZ, p.getMeses(11));
        values.put(COLUNA_TOTAL, p.getTotal());
        values.put(COLUNA_ID_PROPRIEDADE, idPropriedade);

        this.bancoDeDados.insert(TABELA_PIQUETE_ATUAL, null, values);
    }

    /**
     * Método para recuperar todos os piquetes de determinada propriedade baseado no seu id.
     * @param idPropriedade Id da propriedade.
     * @return Array com todos os piquetes da propriedade.
     */
    public ArrayList<Piquete> getAllPiquetesByPropId(int idPropriedade){
        String sql_query = "SELECT * FROM " + TABELA_PIQUETE_ATUAL + " WHERE " + COLUNA_ID_PROPRIEDADE + "=\"" + idPropriedade + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        ArrayList<Piquete> listaPiquetes = new ArrayList<>();
        while(cursor.moveToNext()){
            Piquete p = new Piquete();
            p.setTipo(cursor.getString(1));
            p.setCondicao(cursor.getString(2));
            p.setArea(cursor.getDouble(3));
            p.setProdEstimada(cursor.getDouble(4));
            p.setMeses(cursor.getDouble(5), 0);
            p.setMeses(cursor.getDouble(6), 1);
            p.setMeses(cursor.getDouble(7), 2);
            p.setMeses(cursor.getDouble(8), 3);
            p.setMeses(cursor.getDouble(9), 4);
            p.setMeses(cursor.getDouble(10), 5);
            p.setMeses(cursor.getDouble(11), 6);
            p.setMeses(cursor.getDouble(12), 7);
            p.setMeses(cursor.getDouble(13), 8);
            p.setMeses(cursor.getDouble(14), 9);
            p.setMeses(cursor.getDouble(15), 10);
            p.setMeses(cursor.getDouble(16), 11);
            p.setTotal(cursor.getInt(17));
            listaPiquetes.add(p);
        }
        return listaPiquetes;
    }

    /**
     * Método para remover todos os piquetes de uma propriedade baseado no id.
     * @param idPropriedade Nome da propriedade a ser removida.
     */
    public void deletePiqueteByPropId(int idPropriedade){
        this.bancoDeDados.delete(TABELA_PIQUETE_ATUAL,COLUNA_ID_PROPRIEDADE + "=" + idPropriedade, null);
    }
}
