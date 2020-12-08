package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;

import java.util.ArrayList;

public class TotalPiqueteEstacaoDAO implements ITotalPiqueteEstacao {
    private final SQLiteDatabase bancoDeDados;

    public TotalPiqueteEstacaoDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir o total produzido em cada estação no banco de dados (tabela total_piquete_estacao_atual).
     * @param listaTotaisEstacao ArrayList com os totais de cada estação.
     * @param idPropriedade Id da propriedade.
     */
    public void inserirTotalEstacao(ArrayList<Double> listaTotaisEstacao, int idPropriedade, String NOME_TABELA){
        ContentValues values = new ContentValues();
        values.put(COLUNA_TOTAL_VER, listaTotaisEstacao.get(0));
        values.put(COLUNA_TOTAL_OUT, listaTotaisEstacao.get(1));
        values.put(COLUNA_TOTAL_INV, listaTotaisEstacao.get(2));
        values.put(COLUNA_TOTAL_PRIM, listaTotaisEstacao.get(3));
        values.put(COLUNA_ID_PROPRIEDADE, idPropriedade);

        this.bancoDeDados.insert(NOME_TABELA, null, values);
    }

    /**
     * Método para recuperar os totais de todas as estações de determinada propriedade baseado no seu id.
     * @param idPropriedade Id da propriedade.
     * @return Array com os totais de todas as estações da propriedade.
     */
    public ArrayList<Double> getTotalEstacaoByPropId(int idPropriedade, String NOME_TABELA){
        String sql_query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_ID_PROPRIEDADE + "=\"" + idPropriedade + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        ArrayList<Double> totais = new ArrayList<>();
        while(cursor.moveToLast()){
            totais.add(cursor.getDouble(1));
            totais.add(cursor.getDouble(2));
            totais.add(cursor.getDouble(3));
            totais.add(cursor.getDouble(4));
        }
        cursor.close();
        return totais;
    }

    /**
     * Método para remover todos os totais de uma propriedade baseado no id.
     * @param idPropriedade Id da propriedade a ser removida.
     */
    public void deleteTotalEstacaoByPropId(int idPropriedade, String NOME_TABELA){
        this.bancoDeDados.delete(NOME_TABELA,COLUNA_ID_PROPRIEDADE + "=" + idPropriedade, null);
    }
}
