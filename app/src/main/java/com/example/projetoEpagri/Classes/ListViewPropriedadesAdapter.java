package com.example.projetoEpagri.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.Activities.TabsActivity;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class ListViewPropriedadesAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<Propriedade> listaPropriedades;
    private TextView tv_nome, tv_area, tv_qtde;
    private Button bt_ver_dados, bt_excluir;
    //Atributo para animação no botão

    public ListViewPropriedadesAdapter(Context context, ArrayList<Propriedade> lista){
        this.context = context;
        this.listaPropriedades = lista;
    }

    public ArrayList<Propriedade> getData() {
        return this.listaPropriedades;
    }

    @Override
    public int getCount() {
        return this.listaPropriedades.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaPropriedades.get(position);
    }

    //Implementar para pegar o id da propriedade caso necessário.
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Infla o layout definido em linha_listview_propriedade.xml.
        View row = layoutInflater.inflate(R.layout.linha_listview_propriedade, parent, false);
        row.setTag(this.getCount());

        //Identifica os elementos da linha.
        tv_nome = row.findViewById(R.id.lv_tv_nome_propriedade);
        tv_area = row.findViewById(R.id.lv_tv_area_propriedade);
        tv_qtde = row.findViewById(R.id.lv_tv_total_animais_propriedade);
        bt_ver_dados = row.findViewById(R.id.lv_bt_ver_dados);
        bt_excluir = row.findViewById(R.id.lv_bt_excluir);


        //Cria um objeto para cada item da lista.
        final Propriedade propriedade = (Propriedade) this.getItem(position);

        //Define os valores dos textviews.
        tv_nome.setText(propriedade.getNome());
        tv_area.setText(String.valueOf(propriedade.getArea()));
        tv_qtde.setText(String.valueOf(propriedade.getQtdeAnimais()));

        //Listener do botão "ver dados"
        bt_ver_dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TabsActivity.class);
                context.startActivity(i);
            }
        });

        //Listener do botão "excluir"
        bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
                MainActivity.bancoDeDados.propriedadeDAO.deletePropriedade(propriedade.getNome());
                listaPropriedades.remove(position);
                notifyDataSetChanged();
                notifyDataSetInvalidated();
            }
        });

        
        return row;

    }

}
