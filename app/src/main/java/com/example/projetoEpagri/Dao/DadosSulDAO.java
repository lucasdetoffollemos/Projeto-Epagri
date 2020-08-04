package com.example.projetoEpagri.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
        arrayList.add("Selecione");

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

    public double getCondicao(String tipo, String condicao){

        //QUERY COM O WHERE
        //select * from dadosSul WHERE tipoPastagem = tipo

        return -1;
    }
}
