package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IUsuarioSchema {
    String TABELA_USUARIO = "usuarios";
    String COLUNA_ID = "id";
    String COLUNA_NOME = "nome";
    String COLUNA_EMAIL = "email";
    String COLUNA_TELEFONE = "telefone";
    String COLUNA_SENHA = "senha";

    String CREATE_TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS "
        + TABELA_USUARIO + "("
        + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT,  "
        + COLUNA_NOME + " varchar(50), "
        + COLUNA_EMAIL + " varchar(50), "
        + COLUNA_TELEFONE + " varchar(50), "
        + COLUNA_SENHA + " varchar(50))";

    String[] USUARIO_COLUNAS = new String[] {
        COLUNA_ID,
        COLUNA_NOME,
        COLUNA_EMAIL,
        COLUNA_TELEFONE,
        COLUNA_SENHA
    };
}
