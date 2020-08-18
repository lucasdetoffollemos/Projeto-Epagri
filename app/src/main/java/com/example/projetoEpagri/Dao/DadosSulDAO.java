package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.TextView;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.DadosSul;

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
     * @param p
     * @return
     */
    public long inserirPastagem(DadosSul p){
        ContentValues values = new ContentValues();
        values.put("tipoPastagem", p.getTipo());

        values.put("condicaoDegradada", p.getCondicao(0));
        values.put("condicaoMedia", p.getCondicao(1));
        values.put("condicaoOtima", p.getCondicao(2));

        values.put("jan", p.getMeses(0));
        values.put("fev", p.getMeses(1));
        values.put("mar", p.getMeses(2));
        values.put("abri", p.getMeses(3));
        values.put("mai", p.getMeses(4));
        values.put("jun", p.getMeses(5));
        values.put("jul", p.getMeses(6));
        values.put("ago", p.getMeses(7));
        values.put("setem", p.getMeses(8));
        values.put("out", p.getMeses(9));
        values.put("nov", p.getMeses(10));
        values.put("dez", p.getMeses(11));

        values.put("totalPastagem", p.getTotal());

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

            //dadosSul.add(d);
        }


        return valorCondicao;
    }




    public int getMeses(int mes, String tipo){



        int posicaoColuna = 1;


        Cursor c = bd.getBanco().rawQuery("SELECT * FROM dadosSul WHERE tipoPastagem = '"+ tipo +"'", null);

        if (mes == 1){
             posicaoColuna = 4;
        }
        else if(mes == 2){
            posicaoColuna = 5;
        }

        else if(mes == 3){
            posicaoColuna = 6;
        }

        else if(mes == 4){
            posicaoColuna = 7;
        }

        else if(mes == 5){
            posicaoColuna = 8;
        }

        else if(mes == 6){
            posicaoColuna = 9;
        }

        else if(mes == 7){
            posicaoColuna = 10;
        }

        else if(mes == 8){
            posicaoColuna = 11;
        }

        else if(mes == 9){
            posicaoColuna = 12;
        }

        else if(mes == 10){
            posicaoColuna = 13;
        }

        else if(mes == 11){
            posicaoColuna = 14;
        }

        else if(mes == 12){
            posicaoColuna = 15;
        }



        while (c.moveToNext()){
            posicaoColuna = (c.getInt(posicaoColuna));
        }

        return  posicaoColuna;

    }
}
