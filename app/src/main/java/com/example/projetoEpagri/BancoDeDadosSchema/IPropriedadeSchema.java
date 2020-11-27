package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IPropriedadeSchema {
    String TABELA_PROPRIEDADE = "propriedades";
    String COLUNA_ID = "id";
    String COLUNA_NOME = "nome";
    String COLUNA_REGIAO = "regiao";
    String COLUNA_AREA = "area";
    String COLUNA_QTDE_ANIMAIS = "qtde_animais";
    String COLUNA_ID_USUARIO = "id_usuario";
    String TABELA_USUARIO = "usuarios";

    String CREATE_TABELA_PROPRIEDADE = "CREATE TABLE IF NOT EXISTS "
            + TABELA_PROPRIEDADE + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_NOME + " varchar(50), "
            + COLUNA_REGIAO + " varchar(5), "
            + COLUNA_AREA + " real, "
            + COLUNA_QTDE_ANIMAIS + " integer, "
            + COLUNA_ID_USUARIO + " integer, "
            + "FOREIGN KEY (" + COLUNA_ID_USUARIO + ") REFERENCES " + TABELA_USUARIO + " (" + COLUNA_ID + "))";

    String[] PROPRIEDADE_COLUNAS = new String[] {
            COLUNA_ID,
            COLUNA_NOME,
            COLUNA_REGIAO,
            COLUNA_AREA,
            COLUNA_QTDE_ANIMAIS,
            COLUNA_ID_USUARIO
    };
}
