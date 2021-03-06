package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.projetoEpagri.BancoDeDadosSchema.IPropriedadeSchema;
import com.example.projetoEpagri.Classes.Propriedade;

import java.util.ArrayList;

public class PropriedadeDAO implements IPropriedadeSchema {
    private final SQLiteDatabase bancoDeDados;

    public PropriedadeDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir uma propriedade no banco de dados (tabela propriedades).
     * @param p Representa o objeto da propriedade.
     * @param idUsuario Representa o id do usuário logado.
     */
    public void inserirPropriedade(Propriedade p, int idUsuario){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, p.getNome());
        values.put(COLUNA_REGIAO, p.getRegiao());
        values.put(COLUNA_AREA, p.getArea());
        values.put(COLUNA_QTDE_ANIMAIS, p.getQtdeAnimais());
        values.put(COLUNA_ID_USUARIO, idUsuario);

        this.bancoDeDados.insert(TABELA_PROPRIEDADE, null, values);
    }

    /**
     * Método para recuperar uma propriedade do banco de dados baseado no id.
     * @param nome Representa o id da propriedade que deseja-se recuperar.
     * @return Propriedade.
     */
    public Propriedade getPropriedade(String nome){
        String sql_query = "SELECT * FROM " + TABELA_PROPRIEDADE + " WHERE " + COLUNA_NOME + "=" + "\"" + nome + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        Propriedade p = new Propriedade();
        if (cursor != null && cursor.moveToFirst()) {
            p.setNome(cursor.getString(1));
            p.setRegiao(cursor.getString(2));
            p.setArea(cursor.getDouble(3));
            p.setQtdeAnimais(cursor.getInt(4));
            p.setListaPiqueteAtual(null); //Precisa consultar na outra tabela.
            p.setListaAnimaisAtual(null);
            cursor.close();
        }

        return p;
    }

    public Propriedade getPropriedadeById(int id){
        String sql_query = "SELECT * FROM " + TABELA_PROPRIEDADE + " WHERE " + COLUNA_ID + "=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        Propriedade p = new Propriedade();
        if (cursor != null && cursor.moveToFirst()) {
            p.setNome(cursor.getString(1));
            p.setRegiao(cursor.getString(2));
            p.setArea(cursor.getDouble(3));
            p.setQtdeAnimais(cursor.getInt(4));
            p.setListaPiqueteAtual(null); //Precisa consultar na outra tabela.
            p.setListaAnimaisAtual(null);
            cursor.close();
        }

        return p;
    }

    /**
     * Método responsável por retornar o id de uma determinada propriedade baseada no seu nome.
     * @param nome Nome da propriedade.
     * @return Retorna o id da propriedade especificada.
     */
    public int getPropriedadeId(String nome){
        String sql_query = "SELECT * FROM " + TABELA_PROPRIEDADE + " WHERE " + COLUNA_NOME + "=" + "\"" + nome + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        int id = -1;
        if (cursor.moveToLast()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public String getPropriedadeRegiao(String nome){
        String sql_query = "SELECT * FROM " + TABELA_PROPRIEDADE + " WHERE " + COLUNA_NOME + "=" + "\"" + nome + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        String regiao = "";
        if (cursor.moveToLast()) {
            regiao = cursor.getString(2);
        }
        cursor.close();
        return regiao;
    }

    /**
     * Método para atualizar uma propriedade no banco de dados (tabela propriedades) baseado no id.
     * @param id Id da propriedade que deseja-se atualizar.
     * @param nome Novo nome da propriedade.
     * @param area Nova área da propriedade.
     * @param qtdeAnimais Nova qtde de animais da propriedade.
     */
    public void updatePropriedade(int id, String nome, String regiao, double area, int qtdeAnimais){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME,nome);
        values.put(COLUNA_REGIAO, regiao);
        values.put(COLUNA_AREA, area);
        values.put(COLUNA_QTDE_ANIMAIS, qtdeAnimais);

        this.bancoDeDados.update(TABELA_PROPRIEDADE, values, COLUNA_ID + " = " + id, null);
    }

    public void updatePropriedade(int id, double area){
        ContentValues values = new ContentValues();
        values.put(COLUNA_AREA, area);

        this.bancoDeDados.update(TABELA_PROPRIEDADE, values, COLUNA_ID + " = " + id, null);
    }

    public void updatePropriedade(int id, int qtdeAnimais){
        ContentValues values = new ContentValues();
        values.put(COLUNA_QTDE_ANIMAIS, qtdeAnimais);

        this.bancoDeDados.update(TABELA_PROPRIEDADE, values, COLUNA_ID + " = " + id, null);
    }

    /**
     * Método para recuperar todas as propriedades cadastradas.
     */
    public ArrayList<Propriedade> getAllPropriedades(){
        ArrayList<Propriedade> listaPropriedades = new ArrayList<>();

        Cursor cursor = this.bancoDeDados.query(TABELA_PROPRIEDADE, PROPRIEDADE_COLUNAS,
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Propriedade p = new Propriedade();
            p.setNome(cursor.getString(1));
            p.setRegiao(cursor.getString(2));
            p.setArea(cursor.getDouble(3));
            p.setQtdeAnimais(cursor.getInt(4));
            p.setListaPiqueteAtual(null); //Precisa consultar na outra tabela.
            p.setListaAnimaisAtual(null);
            listaPropriedades.add(p);
        }
        cursor.close();
        return listaPropriedades;
    }

    /**
     * Método para recuperar todas as propriedades cadastradas.
     */
    public ArrayList<Propriedade> getAllPropriedadesByUserId(int usuarioId){
        ArrayList<Propriedade> listaPropriedades = new ArrayList<>();

        String sql_query = "SELECT * FROM " + TABELA_PROPRIEDADE + " WHERE " + COLUNA_ID_USUARIO + "=" + "\"" + usuarioId + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        while(cursor.moveToNext()){
            Propriedade p = new Propriedade();
            p.setNome(cursor.getString(1));
            p.setRegiao(cursor.getString(2));
            p.setArea(cursor.getDouble(3));
            p.setQtdeAnimais(cursor.getInt(4));
            p.setListaPiqueteAtual(null); //Precisa consultar na outra tabela.
            p.setListaAnimaisAtual(null);
            listaPropriedades.add(p);
        }
        cursor.close();
        return listaPropriedades;
    }

    /**
     * Método para remover uma única propriedade baseado no nome.
     * @param nome Nome da propriedade a ser removida.
     */
    public void deletePropriedade(String nome){
        this.bancoDeDados.delete(TABELA_PROPRIEDADE,COLUNA_NOME + "=?", new String[]{nome});
    }

    /**
     * Método para remover uma única propriedade baseado no id.
     * @param id Representa o id da propriedade.
     */
    public void deletePropriedadeById(int id){
        this.bancoDeDados.delete(TABELA_PROPRIEDADE,COLUNA_ID_USUARIO + "=?", new String[]{Integer.toString(id)});
    }

    /**
     * Método para remover todas as propriedades.
     */
    public void deleteAllPropriedades(){
        this.bancoDeDados.execSQL("DELETE from " + TABELA_PROPRIEDADE);
    }
}
