package com.example.projetoEpagri;


import java.io.Serializable;

//Classe usuario, criada inicialmente para inserir dados do usuario no banco
public class Usuario implements Serializable {


    /**
     * Criando os atributos da classe aluno
     */

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;


/**
 * Declarando método construtor
 */
    public Usuario( String nome, String email, String telefone, String senha){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Usuario() {

    }


    /**
     * Metodos getter e setter para todos os atributo de cima
     */

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

        return getNome();
    }

}
