package com.example.projetoEpagri.Classes;

import java.util.Arrays;

//Classe usuario, criada inicialmente para inserir dados do usuario no banco.
public class Usuario {
    private Integer id;
    private String nome, telefone, tipo_perfil, estado, cidade, senha;

    public Usuario( String nome, String telefone, String tipo_perfil, String estado, String cidade, String senha){
        this.nome = nome;
        this.telefone = telefone;
        this.tipo_perfil = tipo_perfil;
        this.estado = estado;
        this.cidade = cidade;
        this.senha = senha;
    }

    public Usuario() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTipo_perfil() {
        return tipo_perfil;
    }

    public void setTipo_perfil(String tipo_perfil) {
        this.tipo_perfil = tipo_perfil;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    /**
     * Mostrando na lista apenas o campo do nome.
     * @return Retorna todos os dados do usuário no formato de String.
     */
    @Override
    public  String toString(){
        //Retornando um elemento apenas
        //return getNome();

        //Retornando varios elementos com um array
        String [] arrayUsers = new String[]{" Nome: "+getNome(), " Telefone: "+getTelefone()," Tipo perfil: "+ getTipo_perfil(), " Estado: "+ getEstado(), " Cidade: "+ getCidade(), " Senha: "+getSenha()};

        return Arrays.toString(arrayUsers).replaceAll("(^\\[|$)", "");
    }
}
