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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BancoDeDados{
    private static final String BANCO_DE_DADOS_NOME = "bancoDeDadosProjetoEpagri.db";
    private static final int BANCO_DE_DADOS_VERSAO = 1;

    private final Context context;
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
     * @throws SQLException representa qualquer exceção que possa ocorrer com o banco de dados.
     */
    public void abreConexao() throws SQLException {
        this.bancoHelper = new BancoHelper(this.context);
        sqLiteDatabase = bancoHelper.getWritableDatabase();

        this.usuarioDAO = new UsuarioDAO(sqLiteDatabase);
        this.dadosDAO = new DadosDAO(sqLiteDatabase);
        this.propriedadeDAO = new PropriedadeDAO(sqLiteDatabase);
        this.piqueteDAO = new PiqueteDAO(sqLiteDatabase);
        this.totalPiqueteMesDAO = new TotalPiqueteMesDAO(sqLiteDatabase);
        this.totalPiqueteEstacaoDAO = new TotalPiqueteEstacaoDAO(sqLiteDatabase);
        this.animaisDAO = new AnimaisDAO(sqLiteDatabase);
        this.totalAnimaisDAO = new TotalAnimaisDAO(sqLiteDatabase);
    }

    /**
     * Método responsável por encerrar as interações com o banco de dados.
     */
    public void fechaConexao(){
        sqLiteDatabase.close();
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

        while(!cursor.isAfterLast()){
            cursor.moveToNext();
            count++;
        }

        if(count > 0){
            vazia = false;
        }

        cursor.close();
        return vazia;
    }

    /**
     * Método para verificar a existência de um conteúdo de uma tabela baseado no valor, na coluna e na tabela.
     * @param valor Valor que deseja-se verificar a existência.
     * @param NOME_TABELA Nome da tabela.
     * @param NOME_COLUNA Coluna onde deseja-se procurar o valor.
     * @return true caso tenha encontrado o valor procurado na coluna desejada e false caso contrário.
     */
    public boolean verificaConteudoTabelaById(int valor, String NOME_TABELA, String NOME_COLUNA){
        String sql_query = "SELECT * FROM " + NOME_TABELA + " WHERE " + NOME_COLUNA + "=\"" + valor + "\"";
        Cursor cursor = this.sqLiteDatabase.rawQuery(sql_query, null);
        cursor.moveToFirst();

        int count = 0;

        while(!cursor.isAfterLast()){
            cursor.moveToNext();
            count++;
        }
        cursor.close();

        return count > 0;
    }

    /**
     * Método utilizado para gerar uma hash para determinada string.
     * @param string Representa a string para qual será gerada o hash.
     * @return Retorna o hash gerado em binário.
     */
    public byte[] generateHash(String string) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        if (digest != null) {
            digest.reset();
            return digest.digest(string.getBytes());
        }
        else{
            return null;
        }
    }

    /**
     * Método utilizado converter de binário para hexadecimal.
     * @param data Representa a informação binária que será convertida.
     * @return Retorna a informação em hexadecimal.
     */
    public String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
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

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_keys=ON");
        }
    }
}
