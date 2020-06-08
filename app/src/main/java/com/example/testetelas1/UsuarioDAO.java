package com.example.testetelas1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

// DAO = data access object
//
public class UsuarioDAO {

    private BancoDeDados bd;
    private SQLiteDatabase banco;


    /**
     * Este método conecta o banco com o arquivo conexao, e deixa ele liberado para escrita com a funcao getWritable...
     * @param context
     */

    public UsuarioDAO(Context context){
        bd = new BancoDeDados(context);
        banco = bd.getWritableDatabase();
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

    /**
     *
     * Método que faz a listagem dos usuário que se cadastraram
     */
    public List<Usuario> listarTodosUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();

        Cursor cursor = banco.query("usuario", new String[]{"id", "nome", "email", "telefone", "senha"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Usuario u = new Usuario(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            u.setId(cursor.getInt(0));
//            u.setNome(cursor.getString(1));
//            u.setEmail(cursor.getString(2));
//            u.setTelefone(cursor.getString(3));
//            u.setSenha(cursor.getString(4));
            usuarios.add(u);
        }
        return usuarios;
    }

    /**
     * Método que deleta todos os dados
     */
//    public void excluiTodosUsuarios(Usuario u){
//        banco.delete("usuario", null, new String[]{u.getId().toString()});
//    }
    // db.run(`DELETE FROM places, function(err){
    //     if(err){
    //         console.log(err)
    //     }

    //     console.log("Registro deletado com sucesso!")

    // })

}
