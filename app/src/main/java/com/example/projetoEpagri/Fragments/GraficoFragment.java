package com.example.projetoEpagri.Fragments;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GraficoFragment extends Fragment {

    private static final String ARG_PARAM1 = "id_propriedade";
    private static final String ARG_PARAM2 = "atual";

    private int id_propriedade;
    private ArrayList<Double> totaisPiqueteMes, totaisAnimalMes;

    public GraficoFragment() {}

    public static GraficoFragment newInstance(int id, int atual) {
        GraficoFragment fragment = new GraficoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, atual);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_propriedade = getArguments().getInt(ARG_PARAM1);
            int atual_ou_proposta = getArguments().getInt(ARG_PARAM2);

            //Atual
            if(atual_ou_proposta == 1){
                totaisPiqueteMes = BancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(id_propriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                totaisAnimalMes = BancoDeDados.totalAnimaisDAO.getTotalMesByPropId(id_propriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);
            }//Proposta
            else{
                totaisPiqueteMes = BancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(id_propriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
                totaisAnimalMes = BancoDeDados.totalAnimaisDAO.getTotalMesByPropId(id_propriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return inflater.inflate(R.layout.fragment_grafico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);

        GraphView grafico = getView().findViewById(R.id.gv_grafico);
        LineGraphSeries<DataPoint> oferta = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> demanda = new LineGraphSeries<>();

        //Cria as séries baseados nos valores retornados do banco de dados.
        for(int i=1; i<=12; i++){
            oferta.appendData(new DataPoint(i-1, totaisPiqueteMes.get(i-1)), true, 12);
            demanda.appendData(new DataPoint(i-1, totaisAnimalMes.get(i-1)), true, 12);
        }

        oferta.setTitle("Oferta");
        oferta.setColor(Color.rgb(0, 156, 78));
        oferta.setDrawBackground(true);
        oferta.setBackgroundColor(Color.argb(80,66, 245, 155));
        oferta.setDrawDataPoints(true);
        oferta.setDataPointsRadius(6f);

        demanda.setTitle("Demanda");
        demanda.setColor(Color.rgb(255, 66, 66));
        demanda.setDrawBackground(true);
        demanda.setBackgroundColor(Color.argb(80,255, 145, 145));
        demanda.setDrawDataPoints(true);
        demanda.setDataPointsRadius(6f);

        grafico.addSeries(oferta);
        grafico.addSeries(demanda);
        grafico.setTitle("Oferta x Demanda");
        grafico.setTitleTextSize(30);
        grafico.getLegendRenderer().setVisible(true);
        grafico.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        grafico.getViewport().setScalable(true);
        grafico.getViewport().setScalableY(true);

        //Altera as label do eixo X (estático - meses).
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(grafico);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"});
        grafico.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        //Altera as label do eixo Y (dinâmico).
        grafico.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                //Eixo x.
                if (isValueX) {
                    if(value == 0){ return "Jan"; }
                    if(value == 1){ return "Fev"; }
                    if(value == 2){ return "Mar"; }
                    if(value == 3){ return "Abr"; }
                    if(value == 4){ return "Mai"; }
                    if(value == 5){ return "Jun"; }
                    if(value == 6){ return "Jul"; }
                    if(value == 7){ return "Ago"; }
                    if(value == 8){ return "Set"; }
                    if(value == 9){ return "Out"; }
                    if(value == 10){ return "Nov"; }
                    if(value == 11){ return "Dez"; }

                    return super.formatLabel(value, isValueX);
                } else { //Eixo y
                    return super.formatLabel(value, isValueX) + " kg";
                }
            }
        });

        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}