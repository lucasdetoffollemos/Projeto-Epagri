package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.ITutorialSchema;
import com.example.projetoEpagri.Classes.Tutorial;

public class TutorialDAO implements ITutorialSchema {
    SQLiteDatabase bancoDeDados;

    public TutorialDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir um usuário no banco de dados (tabela usuarios).
     * @param introducao_index Representa o valor do tutorial na tela index antes de cadastrar alguma propriedade.
     * @param criar_propriedade Representa o valor do tutorial na tela "Cadastra Propriedade".
     * @param inserir_piquetes Representa o valor do tutorial na tela "Cadastrar Piquetes".
     * @param inserir_animais Representa o valor do tutorial na tela "Cadastrar Animais".
     * @param conclusao_index Representa o valor do tutorial na tela index após cadastrar uma propriedade.
     */
    public void inserir(int introducao_index, int criar_propriedade, int inserir_piquetes, int inserir_animais, int conclusao_index){
        ContentValues values = new ContentValues();
        values.put(COLUNA_INTRODUCAO_INDEX, introducao_index);
        values.put(COLUNA_CRIAR_PROPRIEDADE, criar_propriedade);
        values.put(COLUNA_INSERIR_PIQUETES, inserir_piquetes);
        values.put(COLUNA_INSERIR_ANIMAIS, inserir_animais);
        values.put(COLUNA_CONCLUSAO_INDEX, conclusao_index);

        this.bancoDeDados.insert(TABELA_TUTORIAL, null, values);
    }

    /**
     * Método responsável por atualizar o status do tutorial no banco de dados.
     * @param id id do tutorial.
     * @param introducao_index Representa o valor do tutorial na tela index antes de cadastrar alguma propriedade.
     * @param criar_propriedade Representa o valor do tutorial na tela "Cadastra Propriedade".
     * @param inserir_piquetes Representa o valor do tutorial na tela "Cadastrar Piquetes".
     * @param inserir_animais Representa o valor do tutorial na tela "Cadastrar Animais".
     * @param conclusao_index Representa o valor do tutorial na tela index após cadastrar uma propriedade.
     */
    public void update(int id, int introducao_index,  int criar_propriedade, int inserir_piquetes, int inserir_animais, int conclusao_index){
        ContentValues values = new ContentValues();
        values.put(COLUNA_INTRODUCAO_INDEX, introducao_index);
        values.put(COLUNA_CRIAR_PROPRIEDADE, criar_propriedade);
        values.put(COLUNA_INSERIR_PIQUETES, inserir_piquetes);
        values.put(COLUNA_INSERIR_ANIMAIS, inserir_animais);
        values.put(COLUNA_CONCLUSAO_INDEX, conclusao_index);

        this.bancoDeDados.update(TABELA_TUTORIAL, values, COLUNA_ID + " = " + id, null);
    }

    /**
     * Método responsável por retornar o objeto tutorial.
     * @return Objeto tutorial.
     */
    public Tutorial getTutorial(){
        String sql_query = "SELECT * FROM " + TABELA_TUTORIAL;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        Tutorial tutorial = new Tutorial();

        if (cursor != null && cursor.moveToFirst()) {
            tutorial.setId(cursor.getInt(0));
            tutorial.setIntroducao_index(cursor.getInt(1));
            tutorial.setCriar_propriedade(cursor.getInt(2));
            tutorial.setInserir_piquetes(cursor.getInt(3));
            tutorial.setInserir_animais(cursor.getInt(4));
            tutorial.setConclusao_index(cursor.getInt(5));
            cursor.close();
        }
        return tutorial;
    }
}
