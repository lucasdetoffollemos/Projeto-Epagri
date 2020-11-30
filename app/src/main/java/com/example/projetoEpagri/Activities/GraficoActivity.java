package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.projetoEpagri.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;

public class GraficoActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> oferta;
    private LineGraphSeries<DataPoint> demanda;
    private ArrayList<Double> totaisPiqueteMes, totaisAnimalMes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        inicializa();
        criaGrafico();
    }

    public void inicializa(){
        totaisPiqueteMes = new ArrayList<>();
        totaisAnimalMes = new ArrayList<>();

        Intent i = getIntent();
        totaisPiqueteMes = (ArrayList<Double>) i.getSerializableExtra("totaisPiqueteMes");
        totaisAnimalMes = (ArrayList<Double>) i.getSerializableExtra("totaisAnimalMes");
    }

    public void criaGrafico(){
        GraphView grafico = findViewById(R.id.gv_grafico);
        oferta = new LineGraphSeries<>();
        demanda = new LineGraphSeries<>();

        //Log.i("vetor", String.valueOf(totaisPiqueteMes));
        //Log.i("vetor", String.valueOf(totaisAnimalMes));

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
        /*grafico.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    //Eixo x
                    return super.formatLabel(value, isValueX);
                } else {
                    //Eixo y
                    return super.formatLabel(value, isValueX) + " UA";
                }
            }
        });*/
    }

    public void clicarVoltarGrafico(View v){
        finish();
    }
}