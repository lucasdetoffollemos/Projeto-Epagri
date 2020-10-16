package com.example.projetoEpagri.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSulSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimaisAtual;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacaoAtual;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMesAtual;
import com.example.projetoEpagri.BancoDeDadosSchema.IUsuarioSchema;
import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.Dao.UsuarioDAO;

public class BancoDeDados{
    private final Context context;
    private static final String BANCO_DE_DADOS_NOME = "bancoDeDadosProjetoEpagri.db";
    private static final int BANCO_DE_DADOS_VERSAO = 1;

    private BancoHelper bancoHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static UsuarioDAO usuarioDAO;
    public static DadosSulDAO dadosSulDAO;

    public BancoDeDados(Context context) {
        this.context = context;
    }

    /**
     * Método responsável por inicializar as interações com o banco de dados.
     * Cria-se de fato o objeto do banco de dados (SQLiteDatabase) e inicializa-se os DAOs.
     * @return
     * @throws SQLException
     */
    public BancoDeDados abreConexao() throws SQLException {
        bancoHelper = new BancoHelper(this.context);
        sqLiteDatabase = bancoHelper.getWritableDatabase();

        usuarioDAO = new UsuarioDAO(sqLiteDatabase);
        dadosSulDAO = new DadosSulDAO(sqLiteDatabase);

        return this;
    }

    /**
     * Método responsável por encerrar as interações com o banco de dados.
     */
    public void fechaConexao(){
        bancoHelper.close();
    }

    /**
     * Método utilizado pra verificar se uma tabela existe no banco de dados.
     * @param tableName Nome da tabela.
     * @return True se a tabela existir e false se não existir.
     */
    public boolean verificaExistenciaTabela(String tableName) {
        boolean existe = false;
        Cursor cursor = this.sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
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
     * @param nomeTabela Nome da tabela.
     * @return True se a tabela estiver vazia e false se não estiver.
     */
    public boolean verificaTabelaVazia(String nomeTabela){
        boolean vazia = true;
        int count = 0;

        Cursor cursor = this.sqLiteDatabase.rawQuery("select * from '" + nomeTabela + "'", null);
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

    private static class BancoHelper extends SQLiteOpenHelper{
        BancoHelper(Context context){
            super(context, BANCO_DE_DADOS_NOME, null, BANCO_DE_DADOS_VERSAO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(IUsuarioSchema.CREATE_TABELA_USUARIO);
            db.execSQL(IDadosSulSchema.CREATE_TABELA_DADOS_SUL);
            //db.execSQL(IPiqueteSchema.CREATE_TABELA_PIQUETE_ATUAL);
            //db.execSQL(ITotalPiqueteMesAtual.CREATE_TABELA_TOTAL_PIQUETE_MES_ATUAL);
            //db.execSQL(ITotalPiqueteEstacaoAtual.CREATE_TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);
            //db.execSQL(IAnimaisSchema.CREATE_TABELA_ANIMAIS_ATUAL);
            //db.execSQL(ITotalAnimaisAtual.CREATE_TABELA_TOTAL_ANIMAIS_ATUAL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
