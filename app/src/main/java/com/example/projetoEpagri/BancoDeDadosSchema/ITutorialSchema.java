package com.example.projetoEpagri.BancoDeDadosSchema;

public interface ITutorialSchema {
    String TABELA_TUTORIAL = "tutorial";
    String COLUNA_ID = "id";
    String COLUNA_INTRODUCAO_INDEX = "introducao_index";
    String COLUNA_CRIAR_PROPRIEDADE = "criar_propriedade";
    String COLUNA_INSERIR_PIQUETES = "inserir_piquetes";
    String COLUNA_INSERIR_ANIMAIS = "inserir_animais";
    String COLUNA_CONCLUSAO_INDEX = "conclusao_index";

    String CREATE_TABELA_TUTORIAL = "CREATE TABLE IF NOT EXISTS "
            + TABELA_TUTORIAL + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_INTRODUCAO_INDEX + " integer, "
            + COLUNA_CRIAR_PROPRIEDADE + " integer, "
            + COLUNA_INSERIR_PIQUETES + " integer, "
            + COLUNA_INSERIR_ANIMAIS + " integer, "
            + COLUNA_CONCLUSAO_INDEX + " integer)";

    String[] USUARIO_COLUNAS = new String[] {
            COLUNA_ID,
            COLUNA_INTRODUCAO_INDEX,
            COLUNA_CRIAR_PROPRIEDADE,
            COLUNA_INSERIR_PIQUETES,
            COLUNA_INSERIR_ANIMAIS,
            COLUNA_CONCLUSAO_INDEX,
    };
}
