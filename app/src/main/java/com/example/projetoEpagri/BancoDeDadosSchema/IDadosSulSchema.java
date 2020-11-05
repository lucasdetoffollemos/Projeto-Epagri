package com.example.projetoEpagri.BancoDeDadosSchema;

public interface IDadosSulSchema {
    String TABELA_DADOS_SUL = "dados_sul";
    String COLUNA_TIPO = "tipo_pastagem";
    String COLUNA_CONDICAO_DEGRADADA = "condicao_degradada";
    String COLUNA_CONDICAO_MEDIA = "condicao_media";
    String COLUNA_CONDICAO_OTIMA = "condicao_otima";
    String COLUNA_JAN = "jan";
    String COLUNA_FEV = "fev";
    String COLUNA_MAR = "mar";
    String COLUNA_ABR = "abr";
    String COLUNA_MAI = "mai";
    String COLUNA_JUN = "jun";
    String COLUNA_JUL = "jul";
    String COLUNA_AGO = "ago";
    String COLUNA_SET = "setem";
    String COLUNA_OUT = "out";
    String COLUNA_NOV = "nov";
    String COLUNA_DEZ = "dez";
    String COLUNA_TOTAL_PASTAGEM = "total_pastagem";

    String CREATE_TABELA_DADOS_SUL = "CREATE TABLE IF NOT EXISTS "
            + TABELA_DADOS_SUL + "("
            + COLUNA_TIPO + " varchar(50) PRIMARY KEY, "
            + COLUNA_CONDICAO_DEGRADADA + " real, "
            + COLUNA_CONDICAO_MEDIA + " real, "
            + COLUNA_CONDICAO_OTIMA + " real, "
            + COLUNA_JAN + " integer, "
            + COLUNA_FEV + " integer, "
            + COLUNA_MAR + " integer, "
            + COLUNA_ABR + " integer, "
            + COLUNA_MAI + " integer, "
            + COLUNA_JUN + " integer, "
            + COLUNA_JUL + " integer, "
            + COLUNA_AGO + " integer, "
            + COLUNA_SET + " integer, "
            + COLUNA_OUT + " integer, "
            + COLUNA_NOV + " integer, "
            + COLUNA_DEZ + " integer, "
            + COLUNA_TOTAL_PASTAGEM + " integer)";

    String[] DADOS_SUL_COLUNAS = new String[] {
            COLUNA_TIPO,
            COLUNA_CONDICAO_DEGRADADA,
            COLUNA_CONDICAO_MEDIA,
            COLUNA_CONDICAO_OTIMA,
            COLUNA_JAN,
            COLUNA_FEV,
            COLUNA_MAR,
            COLUNA_ABR,
            COLUNA_MAI,
            COLUNA_JUN,
            COLUNA_JUL,
            COLUNA_AGO,
            COLUNA_SET,
            COLUNA_OUT,
            COLUNA_NOV,
            COLUNA_DEZ,
            COLUNA_TOTAL_PASTAGEM,
    };
}