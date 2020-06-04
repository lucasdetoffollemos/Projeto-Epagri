package com.example.testetelas1;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

// DAO = data access object
//
public class UsuarioDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;


    /**
     * Este método conecta o banco com o arquivo conexao, e deixa ele liberado para escrita com a funcao getWritable...
     * @param context
     */
    public UsuarioDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }


    /**
     * Inseri os dados da classe usuário na tabela usuario
     * @param usuario
     * @return
     */
    public long inserirUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("telefone", usuario.getTelefone());
        values.put("senha", usuario.getSenha());
        return banco.insert("usuario", null, values);
    }

}
