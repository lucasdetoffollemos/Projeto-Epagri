package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetoEpagri.BancoDeDadosSchema.IUsuarioSchema;
import com.example.projetoEpagri.Classes.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioSchema {
    SQLiteDatabase bancoDeDados;

    public UsuarioDAO(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }

    /**
     * Método para inserir um usuário no banco de dados (tabela usuarios).
     * @param usuario
     * @return
     */
    public long inserirUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_EMAIL, usuario.getEmail());
        values.put(COLUNA_TELEFONE, usuario.getTelefone());
        values.put(COLUNA_SENHA, usuario.getSenha());

        return this.bancoDeDados.insert(TABELA_USUARIO, null, values);
    }

    /**
     * Método para recuperar um usuário baseado no id;
     * @param id
     * @return
     */
    public Usuario getUsuario(int id){
        String sql_query = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_ID + "=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        Usuario usuario = new Usuario();
        if (cursor.getCount() > 0) {
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setTelefone(cursor.getString(3));
            usuario.setSenha(cursor.getString(4));
        }
        return usuario;
    }

    /**
     * Método para recuperar o id de um usuário baseado no nome.
     * @param nome Nome do usuário que deseja-se saber o id.
     * @return id do usuário.
     */
    public int getUSuarioId(String nome){
        String sql_query = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_NOME + "=\"" + nome + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        int id = -1;
        if (cursor.moveToLast()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    /**
     * Método para atualizar um usuário.
     * @param id
     * @param nome
     * @param email
     * @param telefone
     * @param senha
     */
    public void updateUsuario(int id, String nome, String email, String telefone, String senha){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME,nome); //These Fields should be your String values of actual column names
        values.put(COLUNA_EMAIL, email);
        values.put(COLUNA_TELEFONE, telefone);
        values.put(COLUNA_SENHA, senha);

        this.bancoDeDados.update(TABELA_USUARIO, values, COLUNA_ID + " = " + id, null);
    }

    /**
     * Método para recuperar todos os usuários cadastrados.
     */
    public List<Usuario> getAllUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();

        Cursor cursor = this.bancoDeDados.query(TABELA_USUARIO, USUARIO_COLUNAS,
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setTelefone(cursor.getString(3));
            usuario.setSenha(cursor.getString(4));
            listaUsuarios.add(usuario);
        }
        return listaUsuarios;
    }

    /**
     * Método para remover um único usuário baseado no nome e na senha.
     * @param nome
     * @param senha
     */
    public void deleteUsuario(String nome, String senha){
        this.bancoDeDados.delete(TABELA_USUARIO,COLUNA_NOME + "=? and "+ COLUNA_SENHA +"=?", new String[]{nome, senha});
    }

    /**
     * Método para remover todos os usuários.
     */
    public void deleteAllUsuarios(){
        this.bancoDeDados.execSQL("DELETE from " + TABELA_USUARIO);
    }


    /**
     * Método para validar os dados informados no login.
     * @param nome
     * @param senha
     * @return
     */
    public boolean login(String nome, String senha){
        String sql_query = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_NOME + "=? and "+ COLUNA_SENHA + "=?";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, new String[]{nome, senha});

        if(cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
}
