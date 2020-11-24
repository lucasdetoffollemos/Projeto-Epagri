package com.example.projetoEpagri.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.GraficoActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.Activities.TabsActivity;
import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class ListViewPropriedadesAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<Propriedade> listaPropriedades;
    private String nomeUsuario;
    private int idPropriedade;
    private TextView tv_nome, tv_area, tv_qtde;
    private Button bt_ver_dados, bt_grafico_atual, bt_grafico_proposta, bt_excluir;
    private int codigoRequisicao = 1; //Código para identificar a activity no método onActivityResult.
    //Atributo para animação no botão

    public ListViewPropriedadesAdapter(Context context, ArrayList<Propriedade> lista, String nomeUsuario){
        this.context = context;
        this.listaPropriedades = lista;
        this.nomeUsuario = nomeUsuario;
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
        bt_grafico_atual = row.findViewById(R.id.lv_bt_grafico_atual);
        bt_grafico_proposta = row.findViewById(R.id.lv_bt_grafico_proposta);
        bt_excluir = row.findViewById(R.id.lv_bt_excluir);

        //Cria um objeto para cada item da lista.
        final Propriedade propriedade = (Propriedade) this.getItem(position);

        //Define os valores dos textviews.
        tv_nome.setText(propriedade.getNome());
        tv_area.setText(String.valueOf(propriedade.getArea()));
        tv_qtde.setText(String.valueOf(propriedade.getQtdeAnimais()));


        idPropriedade = MainActivity.bancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
        final ArrayList<Piquete> listaPiqueteProposta = MainActivity.bancoDeDados.piqueteDAO.getAllPiquetesByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_PROPOSTA);
        final ArrayList<Animais> listaAnimaisProposta = MainActivity.bancoDeDados.animaisDAO.getAllAnimaisByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);

        if(listaPiqueteProposta.size() > 0 && listaAnimaisProposta.size() > 0){
            bt_grafico_proposta.setEnabled(true);
        }else{
            bt_grafico_proposta.setEnabled(false);
        }

        //Listener do botão "ver dados"
        bt_ver_dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TabsActivity.class);
                i.putExtra("nomePropriedade", propriedade.getNome());
                i.putExtra("nome_usuario", nomeUsuario);
                ((Activity) context).startActivityForResult(i, codigoRequisicao);
            }
        });

        bt_grafico_atual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPropriedade = MainActivity.bancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
                ArrayList<Double> totaisPiqueteMes = MainActivity.bancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                ArrayList<Double> totaisAnimalMes = MainActivity.bancoDeDados.totalAnimaisDAO.getTotalMesByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

                Intent i = new Intent(context, GraficoActivity.class);
                i.putExtra("totaisPiqueteMes", totaisPiqueteMes);
                i.putExtra("totaisAnimalMes", totaisAnimalMes);
                context.startActivity(i);
                //i.putExtra("nomePropriedade", listaPropriedades.get(position).getNome());

            }
        });

        bt_grafico_proposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPropriedade = MainActivity.bancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
                ArrayList<Double> totaisPiqueteMes = MainActivity.bancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
                ArrayList<Double> totaisAnimalMes = MainActivity.bancoDeDados.totalAnimaisDAO.getTotalMesByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);

                Intent i = new Intent(context, GraficoActivity.class);
                i.putExtra("totaisPiqueteMes", totaisPiqueteMes);
                i.putExtra("totaisAnimalMes", totaisAnimalMes);
                //i.putExtra("nomePropriedade", listaPropriedades.get(position).getNome());
                context.startActivity(i);
            }
        });

        //Listener do botão "excluir"
        bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.bancoDeDados.propriedadeDAO.deletePropriedade(propriedade.getNome());
                MainActivity.bancoDeDados.piqueteDAO.deletePiqueteByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
                MainActivity.bancoDeDados.totalPiqueteMesDAO.deleteTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                MainActivity.bancoDeDados.totalPiqueteEstacaoDAO.deleteTotalEstacaoByPropId(idPropriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);
                MainActivity.bancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
                MainActivity.bancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

                if(listaPiqueteProposta.size() > 0){
                    MainActivity.bancoDeDados.piqueteDAO.deletePiqueteByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_PROPOSTA);
                    MainActivity.bancoDeDados.totalPiqueteMesDAO.deleteTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
                    MainActivity.bancoDeDados.totalPiqueteEstacaoDAO.deleteTotalEstacaoByPropId(idPropriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_PROPOSTA);
                }

                if(listaAnimaisProposta.size() > 0){
                    MainActivity.bancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);
                    MainActivity.bancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);
                }

                listaPropriedades.remove(position);
                notifyDataSetChanged();
                notifyDataSetInvalidated();
            }
        });

        return row;
    }

}
