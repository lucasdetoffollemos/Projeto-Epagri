package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IAnimaisSchema {
    String TABELA_ANIMAIS_ATUAL = "animais_atual";
    String COLUNA_ID = "id";
    String COLUNA_CATEGORIA = "categoria";
    String COLUNA_CONSUMO = "consumo";
    String COLUNA_QTDE = "qtde";
    String COLUNA_ENTRADA_MES = "entrada_mes";
    String COLUNA_PESO_INICIAL = "peso_inicial";
    String COLUNA_PESO_FINAL = "peso_final";
    String COLUNA_GANHO_VER = "ganho_ver";
    String COLUNA_GANHO_OUT = "ganho_out";
    String COLUNA_GANHO_INV = "ganho_inv";
    String COLUNA_GANHO_PRIM = "ganho_prim";
    String COLUNA_PESO_JAN = "peso_jan";
    String COLUNA_PESO_FEV = "peso_fev";
    String COLUNA_PESO_MAR = "peso_mar";
    String COLUNA_PESO_ABR = "peso_abr";
    String COLUNA_PESO_MAI = "peso_mai";
    String COLUNA_PESO_JUN = "peso_jun";
    String COLUNA_PESO_JUL = "peso_jul";
    String COLUNA_PESO_AGO = "peso_ago";
    String COLUNA_PESO_SET = "peso_set";
    String COLUNA_PESO_OUT = "peso_out";
    String COLUNA_PESO_NOV = "peso_nov";
    String COLUNA_PESO_DEZ = "peso_dez";
    String COLUNA_ID_PROPRIEDADE = "id_propriedade";
    String TABELA_PROPRIEDADE = "propriedades";

    String CREATE_TABELA_ANIMAIS_ATUAL = "CREATE TABLE IF NOT EXISTS "
            + TABELA_ANIMAIS_ATUAL + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_CATEGORIA + " varchar(50),"
            + COLUNA_CONSUMO + " real, "
            + COLUNA_QTDE + " integer, "
            + COLUNA_ENTRADA_MES + " varchar(50), "
            + COLUNA_PESO_INICIAL + " real,"
            + COLUNA_PESO_FINAL + " real,  "
            + COLUNA_GANHO_VER + " real, "
            + COLUNA_GANHO_OUT + " real, "
            + COLUNA_GANHO_INV + " real, "
            + COLUNA_GANHO_PRIM + " real, "
            + COLUNA_PESO_JAN + " real, "
            + COLUNA_PESO_FEV + " real, "
            + COLUNA_PESO_MAR + " real, "
            + COLUNA_PESO_ABR + " real, "
            + COLUNA_PESO_MAI + " real, "
            + COLUNA_PESO_JUN + " real, "
            + COLUNA_PESO_JUL + " real, "
            + COLUNA_PESO_AGO + " real, "
            + COLUNA_PESO_SET + " real, "
            + COLUNA_PESO_OUT + " real, "
            + COLUNA_PESO_NOV + " real, "
            + COLUNA_PESO_DEZ + " real, "
            + "FOREIGN KEY (" + COLUNA_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + " ("+ COLUNA_ID + "))";

    String[] ANIMAIS_ATUAL_COLUNAS = new String[] {
            COLUNA_ID,
            COLUNA_CATEGORIA,
            COLUNA_CONSUMO,
            COLUNA_QTDE,
            COLUNA_ENTRADA_MES,
            COLUNA_PESO_INICIAL,
            COLUNA_PESO_FINAL,
            COLUNA_GANHO_VER,
            COLUNA_GANHO_OUT,
            COLUNA_GANHO_INV,
            COLUNA_GANHO_PRIM,
            COLUNA_PESO_JAN,
            COLUNA_PESO_FEV,
            COLUNA_PESO_MAR,
            COLUNA_PESO_ABR,
            COLUNA_PESO_MAI,
            COLUNA_PESO_JUN,
            COLUNA_PESO_JUL,
            COLUNA_PESO_AGO,
            COLUNA_PESO_SET,
            COLUNA_PESO_OUT,
            COLUNA_PESO_NOV,
            COLUNA_PESO_DEZ,
            COLUNA_ID_PROPRIEDADE
    };
}
