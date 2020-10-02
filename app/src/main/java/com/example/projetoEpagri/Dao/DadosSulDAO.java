package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Piquete;

import java.util.ArrayList;

public class DadosSulDAO {
    private BancoDeDados bd;

    /**
     * Este método conecta o banco com o arquivo Banco de Dados.
     * @param context
     */
    public DadosSulDAO(Context context){
        bd = new BancoDeDados(context);
    }

    /**
     * Método para inserir os dados da tabela dadosSul no banco de dados.
     * @return
     */
    public long inserirPastagem(String pastagem, double condicaoDegradada, double condicaoMedia, double condicaoOtima, int [] meses, int total){
        ContentValues values = new ContentValues();
        values.put("tipoPastagem", pastagem);

        values.put("condicaoDegradada", condicaoDegradada);
        values.put("condicaoMedia", condicaoMedia);
        values.put("condicaoOtima", condicaoOtima);

        values.put("jan", meses[0]);
        values.put("fev",  meses[1]);
        values.put("mar",  meses[2]);
        values.put("abri",  meses[3]);
        values.put("mai",  meses[4]);
        values.put("jun",  meses[5]);
        values.put("jul",  meses[6]);
        values.put("ago",  meses[7]);
        values.put("setem", meses[8]);
        values.put("out",  meses[9]);
        values.put("nov",  meses[10]);
        values.put("dez",  meses[11]);

        values.put("totalPastagem", total);

        return bd.getBanco().insert("dadosSul", null, values);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getTiposPastagem() {
        ArrayList<String> arrayList=new ArrayList<>();


        Cursor cursor =  bd.getBanco().rawQuery( "select * from dadosSul", null );
        cursor.moveToFirst();

        if (cursor != null){
            while(cursor.isAfterLast() == false){
                arrayList.add(cursor.getString(cursor.getColumnIndex("tipoPastagem")));
                Log.d("tipoPastagem",arrayList.toString());

                cursor.moveToNext();
            }}
        return arrayList;
    }

    //Se ele escolheu “grama” no tipo e “média” na condição,
    //então eu tenho que retornar do banco o valor 5,
    //por exemplo, se for “grama” e “ótima”, retorna 7, e assim por diante
    //QUERY COM O WHERE
    //select * from dadosSul WHERE tipoPastagem = tipo

    /**
     * Método responsável por associar o tipo da pastagem, com a sua condição. e retornar o valor da condicao da respectiva pastagem.
     * @param tipo
     * @param condicao
     * @return
     */
    public Double getCondicao(String tipo, String condicao) {
        Double valorCondicao = 1.0;
        int posicaoColuna = 1;

        //Aqui é feito a query selecionando os dados vindo do arquivo BancoDeDados.java
        //Seleciona toda a linha da tabela dadosSul, onde o tipo da Pastagem guardada no banco de dados é igual ao tipo  selecionado pelo usuário no spinner.
        Cursor c = bd.getBanco().rawQuery("SELECT * FROM dadosSul WHERE tipoPastagem = '" + tipo +"'", null);

        switch (condicao) {
            case "Degradada":
                posicaoColuna = 1;
                break;
            case "Média":
                posicaoColuna = 2;
                break;
            case "Ótima":
                posicaoColuna = 3;
                break;
        }

        while (c.moveToNext()) {
            valorCondicao = (c.getDouble(posicaoColuna));
        }

        return valorCondicao;
    }


    public int getMeses(int mes, String tipo){
        int posicaoColuna;
        int valorDoMes = 1;

        Cursor c = bd.getBanco().rawQuery("SELECT * FROM dadosSul WHERE tipoPastagem = '"+ tipo +"'", null);

        switch (mes){
            case 1: posicaoColuna = 4; break;
            case 2: posicaoColuna = 5; break;
            case 3: posicaoColuna = 6; break;
            case 4: posicaoColuna = 7; break;
            case 5: posicaoColuna = 8; break;
            case 6: posicaoColuna = 9; break;
            case 7: posicaoColuna = 10; break;
            case 8: posicaoColuna = 11; break;
            case 9: posicaoColuna = 12; break;
            case 10: posicaoColuna = 13; break;
            case 11: posicaoColuna = 14; break;
            case 12: posicaoColuna = 15; break;
            default: posicaoColuna = 1; break;
        }

        while (c.moveToNext()){
            valorDoMes = (c.getInt(posicaoColuna));
        }

        return  valorDoMes;
    }

    public BancoDeDados getBd() {
        return bd;
    }

    public void setBd(BancoDeDados bd) {
        this.bd = bd;
    }
}
