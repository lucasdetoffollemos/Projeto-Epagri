package com.example.projetoEpagri;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

// DAO = data access object
//
public class UsuarioDAO {

    private BancoDeDados bd;



    /**
     * Este método conecta o banco com o arquivo Banco de Dados.
     * @param context
     */
    public UsuarioDAO(Context context){
        bd = new BancoDeDados(context);
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
        return bd.getBanco().insert("usuario", null, values);
    }

    /**
     *
     * Método que faz a listagem dos usuário que se cadastraram
     */
    public List<Usuario> listarTodosUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();

        Cursor cursor = bd.getBanco().query("usuario", new String[]{"id", "nome", "email", "telefone", "senha"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Usuario u = new Usuario();
            u.setId(cursor.getInt(0));
            u.setNome(cursor.getString(1));
            u.setEmail(cursor.getString(2));
            u.setTelefone(cursor.getString(3));
            u.setSenha(cursor.getString(4));
            usuarios.add(u);
        }
        return usuarios;
    }

    /**
     * Método que deleta todos os dados do usuario, está sendo chamado chamado na activity Perfil
     */
    public void excluiTodosUsuarios(Usuario u){

        bd.getBanco().execSQL("DELETE from usuario");
    }


    /**
     * Método chamado na MainActivity, que tem como obejtivo, pegar o nome e a senha cadastrados no banco de dados, e fazer a verificaçao se condizem com os campos digitados.
     * @param nome
     * @param senha
     * @return
     */
    public boolean logar(String nome, String senha){
        Cursor cursor = bd.getBanco().rawQuery("SELECT * FROM usuario WHERE nome=? and senha=?", new String[]{nome, senha});
        if(cursor.getCount()>0){ return true;}
        else {return false;}
    }


}




