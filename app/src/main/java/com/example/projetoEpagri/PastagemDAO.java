package com.example.projetoEpagri;

import android.content.ContentValues;
import android.content.Context;

public class PastagemDAO {

    private BancoDeDados bd;

    /**
     * Este método conecta o banco com o arquivo Banco de Dados.
     * @param context
     */
    public PastagemDAO(Context context){
        bd = new BancoDeDados(context);
    }


    public long inserirPastagem(Pastagem p){
        ContentValues values = new ContentValues();
        values.put("nomePastagem", p.getTipo());

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

}
