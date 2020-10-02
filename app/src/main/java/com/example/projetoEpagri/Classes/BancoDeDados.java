package com.example.projetoEpagri.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Nesta classe é conectado o banco com o projeto, e aqui sao criados os bancos, as tabelas e as colunas de cada tabela.
 */
public class BancoDeDados extends SQLiteOpenHelper {
    /**
     * Atributos de nome e versao do banco
     */
    private static final  String name = "banco.db";
    private static final int version = 1;
    private SQLiteDatabase banco;

    /**
     * Este metodo herda o nome e a versao do banco
     * @param context
     */
    public BancoDeDados(Context context) {
        super(context, name, null, version);
        banco = this.getWritableDatabase();
    }

    /**
     * Cria as tabelas do banco
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("banco", "Criei as tabelas!");
        //Tabela de dados do usuário
        db.execSQL("create table usuario(id integer primary key autoincrement,  nome varchar(50), email varchar(100), telefone varchar(50), senha varchar(50))");
        //Tabela de dados da pastagem do sul
        db.execSQL("create table dadosSul(tipoPastagem varchar(100) primary key, condicaoDegradada real, condicaoMedia real, condicaoOtima real, jan integer, fev integer, mar integer, abri integer, mai integer, jun integer,  jul integer, ago integer, setem integer, out integer, nov  integer, dez integer, totalPastagem integer)");
    }

    //OBS: Por enquanto esse método não está sendo executado!!!
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists usuario");
            db.execSQL("drop table if exists dadosSul");
    }

    /**
     * Métodos getter e setter para o atributo banco e bd
     * @return
     */
    public SQLiteDatabase getBanco() {
        return banco;
    }

    public void setBanco(SQLiteDatabase banco) {
        this.banco = banco;
    }

    /**
     * Método utilizado pra verificar se uma tabela existe no banco de dados.
     * @param tableName
     * @return
     */
    public boolean verificaExistenciaTabela(String tableName) {
        boolean existe = false;
        Cursor cursor = this.banco.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                existe = true;
            }
            cursor.close();
        }
        return existe;
    }

    /**
     * Método utilizado para verificar se uma tabela existente está vazia.
     * @param nomeTabela
     * @return
     */
    public boolean verificaTabelaVazia(String nomeTabela){
        boolean vazia = true;
        int count = 0;

        Cursor cursor = this.banco.rawQuery("select * from '" + nomeTabela + "'", null);
        cursor.moveToFirst();

        if (cursor != null){
            while(cursor.isAfterLast() == false){
                cursor.moveToNext();
                count++;
            }
        }

        if(count > 0){
            vazia = false;
        }

        return vazia;
    }
}
