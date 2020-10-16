package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IPropriedadeSchema {
    String TABELA_PROPRIEDADE = "propriedades";
    String COLUNA_ID = "id";
    String COLUNA_NOME = "nome";
    String COLUNA_ID_USUARIO = "id_usuario";
    String TABELA_USUARIO = "usuarios";

    String CREATE_TABELA_PROPRIEDADE = "CREATE TABLE IF NOT EXISTS "
            + TABELA_PROPRIEDADE + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_NOME + " varchar(50), "
            + "FOREIGN KEY (" + COLUNA_ID_USUARIO + ") REFERENCES " + TABELA_USUARIO + " (" + COLUNA_ID + "))";

    String[] PROPRIEDADE_COLUNAS = new String[] {
            COLUNA_ID,
            COLUNA_NOME,
            COLUNA_ID_USUARIO
    };
}
