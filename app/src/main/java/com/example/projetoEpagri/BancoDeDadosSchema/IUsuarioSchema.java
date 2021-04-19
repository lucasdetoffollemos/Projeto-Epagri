package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IUsuarioSchema {
    String TABELA_USUARIO = "usuarios";
    String COLUNA_ID = "id";
    String COLUNA_NOME = "nome";
    String COLUNA_EMAIL = "email";
    String COLUNA_TELEFONE = "telefone";
    String COLUNA_TIPO_PERFIL = "perfil";
    String COLUNA_ESTADO = "estado";
    String COLUNA_CIDADE = "cidade";
    String COLUNA_SENHA = "senha";

    String CREATE_TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS "
        + TABELA_USUARIO + "("
        + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
        + COLUNA_NOME + " varchar(50) UNIQUE, "
        + COLUNA_EMAIL + " varchar(50), "
        + COLUNA_TELEFONE + " varchar(50), "
        + COLUNA_TIPO_PERFIL + " varchar(50),"
        + COLUNA_ESTADO + " varchar(50), "
        + COLUNA_CIDADE + " varchar(50), "
        + COLUNA_SENHA + " varchar(64))";

    String[] USUARIO_COLUNAS = new String[] {
        COLUNA_ID,
        COLUNA_NOME,
        COLUNA_EMAIL,
        COLUNA_TELEFONE,
        COLUNA_TIPO_PERFIL,
        COLUNA_ESTADO,
        COLUNA_CIDADE,
        COLUNA_SENHA
    };
}
