package com.example.projetoEpagri.BancoDeDadosSchema;

public interface ITotalPiqueteMes {
    String TABELA_TOTAL_PIQUETE_MES_ATUAL = "total_piquete_mes_atual";
    String TABELA_TOTAL_PIQUETE_MES_PROPOSTA = "total_piquete_mes_proposta";
    String COLUNA_ID = "id";
    String COLUNA_TOTAL_JAN = "total_jan";
    String COLUNA_TOTAL_FEV = "total_fev";
    String COLUNA_TOTAL_MAR = "total_mar";
    String COLUNA_TOTAL_ABR = "total_abr";
    String COLUNA_TOTAL_MAI = "total_mai";
    String COLUNA_TOTAL_JUN = "total_jun";
    String COLUNA_TOTAL_JUL = "total_jul";
    String COLUNA_TOTAL_AGO = "total_ago";
    String COLUNA_TOTAL_SET = "total_set";
    String COLUNA_TOTAL_OUT = "total_out";
    String COLUNA_TOTAL_NOV = "total_nov";
    String COLUNA_TOTAL_DEZ = "total_dez";
    String COLUNA_ID_PROPRIEDADE = "id_propriedade";
    String TABELA_PROPRIEDADE = "propriedades";

    String CREATE_TABELA_TOTAL_PIQUETE_MES_ATUAL = "CREATE TABLE IF NOT EXISTS "
            + TABELA_TOTAL_PIQUETE_MES_ATUAL + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_TOTAL_JAN + " real, "
            + COLUNA_TOTAL_FEV + " real, "
            + COLUNA_TOTAL_MAR + " real, "
            + COLUNA_TOTAL_ABR + " real, "
            + COLUNA_TOTAL_MAI + " real, "
            + COLUNA_TOTAL_JUN + " real, "
            + COLUNA_TOTAL_JUL + " real, "
            + COLUNA_TOTAL_AGO + " real, "
            + COLUNA_TOTAL_SET + " real, "
            + COLUNA_TOTAL_OUT + " real, "
            + COLUNA_TOTAL_NOV + " real, "
            + COLUNA_TOTAL_DEZ + " real, "
            + COLUNA_ID_PROPRIEDADE + " integer, "
            + "CONSTRAINT fk_total_mes_atual_propriedade_id FOREIGN KEY ("+ COLUNA_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + " (" + COLUNA_ID + ") ON DELETE CASCADE)";

    String CREATE_TABELA_TOTAL_PIQUETE_MES_PROPOSTA = "CREATE TABLE IF NOT EXISTS "
            + TABELA_TOTAL_PIQUETE_MES_PROPOSTA + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_TOTAL_JAN + " real, "
            + COLUNA_TOTAL_FEV + " real, "
            + COLUNA_TOTAL_MAR + " real, "
            + COLUNA_TOTAL_ABR + " real, "
            + COLUNA_TOTAL_MAI + " real, "
            + COLUNA_TOTAL_JUN + " real, "
            + COLUNA_TOTAL_JUL + " real, "
            + COLUNA_TOTAL_AGO + " real, "
            + COLUNA_TOTAL_SET + " real, "
            + COLUNA_TOTAL_OUT + " real, "
            + COLUNA_TOTAL_NOV + " real, "
            + COLUNA_TOTAL_DEZ + " real, "
            + COLUNA_ID_PROPRIEDADE + " integer, "
            + "CONSTRAINT fk_total_mes_proposta_propriedade_id FOREIGN KEY ("+ COLUNA_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + " (" + COLUNA_ID + ") ON DELETE CASCADE)";
}
