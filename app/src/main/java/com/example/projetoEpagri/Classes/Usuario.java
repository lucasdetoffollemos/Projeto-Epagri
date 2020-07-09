package com.example.projetoEpagri.Classes;

import java.io.Serializable;
import java.util.Arrays;

//Classe usuario, criada inicialmente para inserir dados do usuario no banco.
public class Usuario {
    private Integer id;
    private String nome, email, telefone, senha;

    public Usuario( String nome, String email, String telefone, String senha){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Usuario() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    /**
     * Mostrando na lista apenas o campo do nome.
     * @return
     */
    @Override
    public  String toString(){
        //Retornando um elemento apenas
        //return getNome();

        //Retornando varios elementos com um array
        String [] arrayUsers = new String[]{" Nome: "+getNome(), " Email: "+getEmail(), " Telefone: "+getTelefone(), " Senha: "+getSenha()};
        //return Arrays.toString(array);
        String tirandoColchetes = Arrays.toString(arrayUsers).replaceAll("(^\\[|\\]$)", "");

        return tirandoColchetes;
    }
}
