package com.example.projetoEpagri.Classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class ListViewPropriedadesAdapter extends BaseAdapter {
    private final Context context;
    public ArrayList<Propriedade> listaPropriedades;
    private int idPropriedade;
    private static LayoutInflater inflater = null;

    public ListViewPropriedadesAdapter(Context context, ArrayList<Propriedade> lista){
        this.context = context;
        this.listaPropriedades = lista;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static class Holder{
        TextView tv_nome, tv_area, tv_qtde;
        Button bt_excluir;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        //Infla o layout definido em linha_listview_propriedade.xml.
        View row = inflater.inflate(R.layout.linha_listview_propriedade,null);
        row.setTag(this.getCount());

        //Identifica os elementos da linha.
        holder.tv_nome = row.findViewById(R.id.lv_tv_nome_propriedade);
        holder.tv_area = row.findViewById(R.id.lv_tv_area_propriedade);
        holder.tv_qtde = row.findViewById(R.id.lv_tv_total_animais_propriedade);
        holder.bt_excluir = row.findViewById(R.id.lv_bt_excluir);

        //Cria um objeto para cada item da lista.
        final Propriedade propriedade = (Propriedade) this.getItem(position);

        //Define os valores dos textviews.
        holder.tv_nome.setText(propriedade.getNome());
        holder.tv_area.setText(String.valueOf(propriedade.getArea()));
        holder.tv_qtde.setText(String.valueOf(propriedade.getQtdeAnimais()));

        idPropriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
        //final ArrayList<Piquete> listaPiqueteProposta = BancoDeDados.piqueteDAO.getAllPiquetesByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_PROPOSTA);
        //final ArrayList<Animais> listaAnimaisProposta = BancoDeDados.animaisDAO.getAllAnimaisByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);

        //Listener do botão "excluir"
        holder.bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("ATENÇÃO");
                builder.setMessage( "Tem certeza que deseja remover a propriedade?" );
                builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BancoDeDados.propriedadeDAO.deletePropriedade(propriedade.getNome());

                        listaPropriedades.remove(position);
                        notifyDataSetChanged();
                        notifyDataSetInvalidated();

                        Toast.makeText(context, "Propriedade removida com Sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(" NÃO ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        return row;
    }
}
