package com.example.projetoEpagri.BancoDeDadosSchema;

public interface ITotalPiqueteEstacao {
    String TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL = "total_piquete_estacao_atual";
    String TABELA_TOTAL_PIQUETE_ESTACAO_PROPOSTA = "total_piquete_estacao_proposta";
    String COLUNA_ID = "id";
    String COLUNA_TOTAL_VER = "total_ver";
    String COLUNA_TOTAL_OUT = "total_out";
    String COLUNA_TOTAL_INV = "total_inv";
    String COLUNA_TOTAL_PRIM = "total_prim";
    String COLUNA_ID_PROPRIEDADE = "id_propriedade";
    String TABELA_PROPRIEDADE = "propriedades";

    String CREATE_TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL = "CREATE TABLE IF NOT EXISTS "
            + TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_TOTAL_VER + " real, "
            + COLUNA_TOTAL_OUT + " real, "
            + COLUNA_TOTAL_INV + " real, "
            + COLUNA_TOTAL_PRIM + " real, "
            + COLUNA_ID_PROPRIEDADE + " integer, "
            + "CONSTRAINT fk_total_estacao_atual_propriedade_id FOREIGN KEY (" + COLUNA_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + " (" + COLUNA_ID + ") ON DELETE CASCADE)";

    String CREATE_TABELA_TOTAL_PIQUETE_ESTACAO_PROPOSTA = "CREATE TABLE IF NOT EXISTS "
            + TABELA_TOTAL_PIQUETE_ESTACAO_PROPOSTA + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_TOTAL_VER + " real, "
            + COLUNA_TOTAL_OUT + " real, "
            + COLUNA_TOTAL_INV + " real, "
            + COLUNA_TOTAL_PRIM + " real, "
            + COLUNA_ID_PROPRIEDADE + " integer, "
            + "CONSTRAINT fk_total_estacao_proposta_propriedade_id FOREIGN KEY (" + COLUNA_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + " (" + COLUNA_ID + ") ON DELETE CASCADE)";
}
