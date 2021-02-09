package com.example.projetoEpagri.Classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.GraficoActivity;
import com.example.projetoEpagri.Activities.TabsActivity;
import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class ListViewPropriedadesAdapter extends BaseAdapter {
    private final Context context;
    public ArrayList<Propriedade> listaPropriedades;
    private final String nomeUsuario;
    private int idPropriedade;
    private final int codigoRequisicao = 1; //Código para identificar a activity no método onActivityResult.
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
        @SuppressLint("ViewHolder") View row = layoutInflater.inflate(R.layout.linha_listview_propriedade, parent, false);
        row.setTag(this.getCount());

        //Identifica os elementos da linha.
        TextView tv_nome = row.findViewById(R.id.lv_tv_nome_propriedade);
        TextView tv_area = row.findViewById(R.id.lv_tv_area_propriedade);
        TextView tv_qtde = row.findViewById(R.id.lv_tv_total_animais_propriedade);
        Button bt_ver_dados = row.findViewById(R.id.lv_bt_ver_dados);
        Button bt_grafico_atual = row.findViewById(R.id.lv_bt_grafico_atual);
        Button bt_grafico_proposta = row.findViewById(R.id.lv_bt_grafico_proposta);
        Button bt_excluir = row.findViewById(R.id.lv_bt_excluir);

        //Cria um objeto para cada item da lista.
        final Propriedade propriedade = (Propriedade) this.getItem(position);

        //Define os valores dos textviews.
        tv_nome.setText(propriedade.getNome());
        tv_area.setText(String.valueOf(propriedade.getArea()));
        tv_qtde.setText(String.valueOf(propriedade.getQtdeAnimais()));


        idPropriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
        final ArrayList<Piquete> listaPiqueteProposta = BancoDeDados.piqueteDAO.getAllPiquetesByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_PROPOSTA);
        final ArrayList<Animais> listaAnimaisProposta = BancoDeDados.animaisDAO.getAllAnimaisByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);

        bt_grafico_proposta.setEnabled(listaPiqueteProposta.size() > 0 && listaAnimaisProposta.size() > 0);

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
                idPropriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
                ArrayList<Double> totaisPiqueteMes = BancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                ArrayList<Double> totaisAnimalMes = BancoDeDados.totalAnimaisDAO.getTotalMesByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

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
                idPropriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());
                ArrayList<Double> totaisPiqueteMes = BancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
                ArrayList<Double> totaisAnimalMes = BancoDeDados.totalAnimaisDAO.getTotalMesByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("ATENÇÃO");
                builder.setMessage( "Tem certeza que deseja remover a propriedade?" );
                builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BancoDeDados.propriedadeDAO.deletePropriedade(propriedade.getNome());
                        BancoDeDados.piqueteDAO.deletePiqueteByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
                        BancoDeDados.totalPiqueteMesDAO.deleteTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                        BancoDeDados.totalPiqueteEstacaoDAO.deleteTotalEstacaoByPropId(idPropriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);
                        BancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
                        BancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

                        if(listaPiqueteProposta.size() > 0){
                            BancoDeDados.piqueteDAO.deletePiqueteByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_PROPOSTA);
                            BancoDeDados.totalPiqueteMesDAO.deleteTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
                            BancoDeDados.totalPiqueteEstacaoDAO.deleteTotalEstacaoByPropId(idPropriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_PROPOSTA);
                        }

                        if(listaAnimaisProposta.size() > 0){
                            BancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);
                            BancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);
                        }

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
