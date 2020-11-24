package com.example.projetoEpagri.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPropriedadeSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.BancoDeDadosSchema.IUsuarioSchema;
import com.example.projetoEpagri.Dao.AnimaisDAO;
import com.example.projetoEpagri.Dao.DadosDAO;
import com.example.projetoEpagri.Dao.PiqueteDAO;
import com.example.projetoEpagri.Dao.PropriedadeDAO;
import com.example.projetoEpagri.Dao.TotalAnimaisDAO;
import com.example.projetoEpagri.Dao.TotalPiqueteEstacaoDAO;
import com.example.projetoEpagri.Dao.TotalPiqueteMesDAO;
import com.example.projetoEpagri.Dao.UsuarioDAO;

public class BancoDeDados{
    private final Context context;
    private static final String BANCO_DE_DADOS_NOME = "bancoDeDadosProjetoEpagri.db";
    private static final int BANCO_DE_DADOS_VERSAO = 1;

    private BancoHelper bancoHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static UsuarioDAO usuarioDAO;
    public static DadosDAO dadosDAO;
    public static PropriedadeDAO propriedadeDAO;
    public static PiqueteDAO piqueteDAO;
    public static TotalPiqueteMesDAO totalPiqueteMesDAO;
    public static TotalPiqueteEstacaoDAO totalPiqueteEstacaoDAO;
    public static AnimaisDAO animaisDAO;
    public static TotalAnimaisDAO totalAnimaisDAO;

    public BancoDeDados(Context context) {
        this.context = context;
    }

    /**
     * Método responsável por inicializar as interações com o banco de dados.
     * Cria-se de fato o objeto do banco de dados (SQLiteDatabase) e inicializa-se os DAOs.
     * @return Retorna o contexto da classe.
     * @throws SQLException
     */
    public BancoDeDados abreConexao() throws SQLException {
        bancoHelper = new BancoHelper(this.context);
        sqLiteDatabase = bancoHelper.getWritableDatabase();

        usuarioDAO = new UsuarioDAO(sqLiteDatabase);
        dadosDAO = new DadosDAO(sqLiteDatabase);
        propriedadeDAO = new PropriedadeDAO(sqLiteDatabase);
        piqueteDAO = new PiqueteDAO(sqLiteDatabase);
        totalPiqueteMesDAO = new TotalPiqueteMesDAO(sqLiteDatabase);
        totalPiqueteEstacaoDAO = new TotalPiqueteEstacaoDAO(sqLiteDatabase);
        animaisDAO = new AnimaisDAO(sqLiteDatabase);
        totalAnimaisDAO = new TotalAnimaisDAO(sqLiteDatabase);

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
            while(!cursor.isAfterLast()){
                cursor.moveToNext();
                count++;
            }
        }

        if(count > 0){
            vazia = false;
        }

        cursor.close();
        return vazia;
    }

    private static class BancoHelper extends SQLiteOpenHelper{
        BancoHelper(Context context){
            super(context, BANCO_DE_DADOS_NOME, null, BANCO_DE_DADOS_VERSAO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(IUsuarioSchema.CREATE_TABELA_USUARIO);
            db.execSQL(IDadosSchema.CREATE_TABELA_DADOS_NORTE);
            db.execSQL(IDadosSchema.CREATE_TABELA_DADOS_SUL);
            db.execSQL(IPropriedadeSchema.CREATE_TABELA_PROPRIEDADE);
            //Atual
            db.execSQL(IPiqueteSchema.CREATE_TABELA_PIQUETE_ATUAL);
            db.execSQL(ITotalPiqueteMes.CREATE_TABELA_TOTAL_PIQUETE_MES_ATUAL);
            db.execSQL(ITotalPiqueteEstacao.CREATE_TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);
            db.execSQL(IAnimaisSchema.CREATE_TABELA_ANIMAIS_ATUAL);
            db.execSQL(ITotalAnimais.CREATE_TABELA_TOTAL_ANIMAIS_ATUAL);
            //Proposta
            db.execSQL(IPiqueteSchema.CREATE_TABELA_PIQUETE_PROPOSTA);
            db.execSQL(ITotalPiqueteMes.CREATE_TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
            db.execSQL(ITotalPiqueteEstacao.CREATE_TABELA_TOTAL_PIQUETE_ESTACAO_PROPOSTA);
            db.execSQL(IAnimaisSchema.CREATE_TABELA_ANIMAIS_PROPOSTA);
            db.execSQL(ITotalAnimais.CREATE_TABELA_TOTAL_ANIMAIS_PROPOSTA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
}
