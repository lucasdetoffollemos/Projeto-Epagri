package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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
     * @param usuario Representa o objeto usuário que será salvo no banco de dados.
     */
    public void inserirUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_EMAIL, usuario.getEmail());
        values.put(COLUNA_TELEFONE, usuario.getTelefone());
        values.put(COLUNA_SENHA, usuario.getSenha());

        this.bancoDeDados.insert(TABELA_USUARIO, null, values);
    }

    /**
     * Método para recuperar um usuário baseado no id;
     * @param id Representa o id do usuário que deseja-se recuperar.
     * @return Retorna o usuário recuperado.
     */
    public Usuario getUsuario(int id){
        String sql_query = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_ID + "=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        Usuario usuario = new Usuario();
        if (cursor != null && cursor.moveToFirst()) {
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setTelefone(cursor.getString(3));
            usuario.setSenha(cursor.getString(4));
            cursor.close();
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
            cursor.close();
        }
        return id;
    }

    /**
     * Método para atualizar um usuário.
     * @param id Representa o id do usuário que deseja-se atualizar os dados.
     * @param nome Representa o novo nome do usuário.
     * @param email Representa o novo email do usuário.
     * @param telefone Representa o novo telefone do usuário.
     * @param senha Representa a nova senha do usuário.
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
        cursor.close();
        return listaUsuarios;
    }

    /**
     * Método para remover um único usuário baseado no nome e na senha.
     * @param nome Representa o nome do usuário.
     * @param senha Representa a senha do usuário.
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
     * @param nome Representa o nome digitado no campo "nome" do login.
     * @param senha Representa a senha digitada no campo "senha" do login.
     * @return Retorna true caso o login seja válido e falso caso contrário.
     */
    public boolean login(String nome, String senha){
        String sql_query = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_NOME + "=? and "+ COLUNA_SENHA + "=?";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, new String[]{nome, senha});

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean verificaNomeTelefoneUusario(String nome, String telefone){
        String sql_query = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_NOME + "=? and "+ COLUNA_TELEFONE + "=?";
        Cursor c = this.bancoDeDados.rawQuery(sql_query, new String[]{nome, telefone});
        if(c.getCount()>0)
        {
            return true;
        }else{
            return false;
        }

    }
}
