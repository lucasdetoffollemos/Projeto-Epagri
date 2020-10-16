package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IPiqueteSchema {
    String TABELA_PIQUETE_ATUAL = "piquetes_atual";
    String COLUNA_ID = "id";
    String COLUNA_TIPO = "tipo";
    String COLUNA_CONDICAO = "condicao";
    String COLUNA_AREA = "area";
    String COLUNA_PRODUCAO_ESTIMADA = "prod_estimada";
    String COLUNA_PRODUCAO_JAN = "prod_jan";
    String COLUNA_PRODUCAO_FEV = "prod_fev";
    String COLUNA_PRODUCAO_MAR = "prod_mar";
    String COLUNA_PRODUCAO_ABR = "prod_abr";
    String COLUNA_PRODUCAO_MAI = "prod_mai";
    String COLUNA_PRODUCAO_JUN = "prod_jun";
    String COLUNA_PRODUCAO_JUL = "prod_jul";
    String COLUNA_PRODUCAO_AGO = "prod_ago";
    String COLUNA_PRODUCAO_SET = "prod_set";
    String COLUNA_PRODUCAO_OUT = "prod_out";
    String COLUNA_PRODUCAO_NOV = "prod_nov";
    String COLUNA_PRODUCAO_DEZ = "prod_dez";
    String COLUNA_ID_PROPRIEDADE = "id_propriedade";
    String TABELA_PROPRIEDADE = "propriedades";

    String CREATE_TABELA_PIQUETE_ATUAL = "CREATE TABLE IF NOT EXISTS "
            + TABELA_PIQUETE_ATUAL + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_TIPO + " varchar(50), "
            + COLUNA_CONDICAO + " varchar(50), "
            + COLUNA_AREA + " real, "
            + COLUNA_PRODUCAO_ESTIMADA + " real, "
            + COLUNA_PRODUCAO_JAN + " real, "
            + COLUNA_PRODUCAO_FEV + " real, "
            + COLUNA_PRODUCAO_MAR + " real, "
            + COLUNA_PRODUCAO_ABR + " real, "
            + COLUNA_PRODUCAO_MAI + " real, "
            + COLUNA_PRODUCAO_JUN + " real, "
            + COLUNA_PRODUCAO_JUL + " real, "
            + COLUNA_PRODUCAO_AGO + " real, "
            + COLUNA_PRODUCAO_SET + " real, "
            + COLUNA_PRODUCAO_OUT + " real, "
            + COLUNA_PRODUCAO_NOV + " real, "
            + COLUNA_PRODUCAO_DEZ + " real, "
            + "FOREIGN KEY (" + COLUNA_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + " ("+ COLUNA_ID + "))";

    String[] PIQUETE_ATUAL_COLUNAS = new String[] {
            COLUNA_ID,
            COLUNA_TIPO,
            COLUNA_CONDICAO,
            COLUNA_AREA,
            COLUNA_PRODUCAO_ESTIMADA,
            COLUNA_PRODUCAO_JAN,
            COLUNA_PRODUCAO_FEV,
            COLUNA_PRODUCAO_MAR,
            COLUNA_PRODUCAO_ABR,
            COLUNA_PRODUCAO_MAI,
            COLUNA_PRODUCAO_JUN,
            COLUNA_PRODUCAO_JUL,
            COLUNA_PRODUCAO_AGO,
            COLUNA_PRODUCAO_SET,
            COLUNA_PRODUCAO_OUT,
            COLUNA_PRODUCAO_NOV,
            COLUNA_PRODUCAO_DEZ,
            COLUNA_ID_PROPRIEDADE
    };
}
