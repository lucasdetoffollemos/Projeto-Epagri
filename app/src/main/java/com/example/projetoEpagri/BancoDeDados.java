package com.example.projetoEpagri;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    public void onCreate(SQLiteDatabase db) {
        //Tabela de dados do usuário
        db.execSQL("create table usuario(id integer primary key autoincrement,  nome varchar(50), email varchar(100), telefone varchar(50), senha varchar(50))");

        //Tabela de dados da pastagem do sul
        db.execSQL("create table dadosSul(tipoPastagem varchar(100) primary key, condicaoDegradada real, condicaoMedia real, condicaoOtima real, jan integer, fev integer, mar integer, abri integer, mai integer, jun integer,  jul integer, ago integer, setem integer, out integer, nov  integer, dez integer, totalPastagem integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists usuario");
            db.execSQL("drop table if exists dadosSul");
            onCreate(db);
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
}
