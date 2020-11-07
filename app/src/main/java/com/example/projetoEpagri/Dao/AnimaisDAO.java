package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.Classes.Animais;

import java.util.ArrayList;

public class AnimaisDAO implements IAnimaisSchema {
    SQLiteDatabase bancoDeDados;

    public AnimaisDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir um animal no banco de dados (tabela piquetes_atual).
     * @param a Representa o objeto do Animal.
     * @param idPropriedade Representa o id da propriedade a qual pertencem os animais.
     */
    public void inserirAnimal(Animais a, int idPropriedade, String NOME_TABELA){
        ContentValues values = new ContentValues();
        values.put(COLUNA_CATEGORIA, a.getCategoria());
        values.put(COLUNA_CONSUMO, a.getConsumo());
        values.put(COLUNA_QTDE, a.getNumAnimais());
        values.put(COLUNA_ENTRADA_MES, a.getEntradaMes());
        values.put(COLUNA_PESO_INICIAL, a.getPesoInicial());
        values.put(COLUNA_PESO_FINAL, a.getPesoFinal());
        values.put(COLUNA_GANHO_VER, a.getPesoGanhoVer());
        values.put(COLUNA_GANHO_OUT, a.getPesoGanhoOut());
        values.put(COLUNA_GANHO_INV, a.getPesoGanhoInv());
        values.put(COLUNA_GANHO_PRIM, a.getPesoGanhoPrim());
        values.put(COLUNA_PESO_JAN, a.getMeses(0));
        values.put(COLUNA_PESO_FEV, a.getMeses(1));
        values.put(COLUNA_PESO_MAR, a.getMeses(2));
        values.put(COLUNA_PESO_ABR, a.getMeses(3));
        values.put(COLUNA_PESO_MAI, a.getMeses(4));
        values.put(COLUNA_PESO_JUN, a.getMeses(5));
        values.put(COLUNA_PESO_JUL, a.getMeses(6));
        values.put(COLUNA_PESO_AGO, a.getMeses(7));
        values.put(COLUNA_PESO_SET, a.getMeses(8));
        values.put(COLUNA_PESO_OUT, a.getMeses(9));
        values.put(COLUNA_PESO_NOV, a.getMeses(10));
        values.put(COLUNA_PESO_DEZ, a.getMeses(11));
        values.put(COLUNA_ID_PROPRIEDADE, idPropriedade);

        this.bancoDeDados.insert(NOME_TABELA, null, values);
    }

    /**
     * Método para recuperar todos os piquetes de determinada propriedade baseado no seu id.
     * @param idPropriedade Id da propriedade.
     * @return Array com todos os piquetes da propriedade.
     */
    public ArrayList<Animais> getAllAnimaisByPropId(int idPropriedade, String NOME_TABELA){
        String sql_query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_ID_PROPRIEDADE + "=\"" + idPropriedade + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        ArrayList<Animais> listaAnimais = new ArrayList<>();
        while(cursor.moveToNext()){
            Animais a = new Animais();
            a.setCategoria(cursor.getString(1));
            a.setConsumo(cursor.getDouble(2));
            a.setNumAnimais(cursor.getInt(3));
            a.setEntradaMes(cursor.getString(4));
            a.setPesoInicial(cursor.getDouble(5));
            a.setPesoFinal(cursor.getDouble(6));
            a.setPesoGanhoVer(cursor.getDouble(7));
            a.setPesoGanhoOut(cursor.getDouble(8));
            a.setPesoGanhoInv(cursor.getDouble(9));
            a.setPesoGanhoPrim(cursor.getDouble(10));
            a.setMeses(cursor.getDouble(11), 0);
            a.setMeses(cursor.getDouble(12), 1);
            a.setMeses(cursor.getDouble(13), 2);
            a.setMeses(cursor.getDouble(14), 3);
            a.setMeses(cursor.getDouble(15), 4);
            a.setMeses(cursor.getDouble(16), 5);
            a.setMeses(cursor.getDouble(17), 6);
            a.setMeses(cursor.getDouble(18), 7);
            a.setMeses(cursor.getDouble(19), 8);
            a.setMeses(cursor.getDouble(20), 9);
            a.setMeses(cursor.getDouble(21), 10);
            a.setMeses(cursor.getDouble(22), 11);
            listaAnimais.add(a);
        }
        cursor.close();
        return listaAnimais;
    }

    /**
     * Método para remover todos os piquetes de uma propriedade baseado no id.
     * @param idPropriedade Nome da propriedade a ser removida.
     */
    public void deleteAnimalByPropId(int idPropriedade, String NOME_TABELA){
        this.bancoDeDados.delete(NOME_TABELA,COLUNA_ID_PROPRIEDADE + "=" + idPropriedade, null);
    }
}
