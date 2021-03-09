package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.R;
import com.example.projetoEpagri.Classes.Usuario;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity {
    private EditText et_nome, et_email, et_telefone, et_senha;
    public Button bt_criar;
    private Spinner spinnerTipoPerfil, spinnerEstado, spinnerCidade;
    private ArrayList<String> tipoPerfilArray, estadosArray, cidadesArray, cidadesScArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializa();
        setListener();
    }

    /**
     * Método responsável por inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        //Esta linha de código faz com que o teclado nao seja habilitado quando o usuário entra nesta activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.et_nome = findViewById(R.id.et_nome);
        this.et_email = findViewById(R.id.et_email);
        this.et_telefone = findViewById(R.id.et_telefone);
        this.et_senha = findViewById(R.id.et_senha);
        this.bt_criar = findViewById(R.id.bt_criar);

        //arraylists
        tipoPerfilArray = new ArrayList();
        estadosArray = new ArrayList();
        cidadesArray = new ArrayList();
        cidadesScArray = new ArrayList();

        //Spinners
        spinnerTipoPerfil = (Spinner) findViewById(R.id.spinner_tipoPerfil);
        spinnerEstado = findViewById(R.id.spinner_estado);
        spinnerCidade = findViewById(R.id.spinner_cidade);


        setandoSpinners();
    }

    /**
     * Método responsável por setar os listener dos botões e tudo mais que for clicável na tela de perfil.
     */
    public void setListener(){
        this.bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome, email, telefone, senha;
                nome = et_nome.getText().toString();
                email = et_email.getText().toString();
                telefone = et_telefone.getText().toString();
                senha = et_senha.getText().toString();

                criarPerfil(nome, email, telefone, senha);
            }
        });



        //Spinners.
        spinnerTipoPerfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    Toast.makeText(PerfilActivity.this, "Tipo " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinners.
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    Toast.makeText(PerfilActivity.this, "Estado " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        //Spinners.
//        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position > 0){
//                    Toast.makeText(PerfilActivity.this, "Cidades " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });



    }

    /**
     * Método responsável por criar um usuário e salvar no banco de dados.
     */
    public void criarPerfil(String nome, String email, String telefone, String senha){
        Usuario u = new Usuario(nome, email, telefone, senha);
        BancoDeDados.usuarioDAO.inserirUsuario(u);

        Toast.makeText(this, "Usuario criado com sucesso! ", Toast.LENGTH_SHORT).show();


        //O código abaixo da um tempo da 2 seg até voltar a outra página.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String  nomeUsuario = et_nome.getText().toString();
                Intent i = new Intent(PerfilActivity.this, IndexActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("nome_usuario", nomeUsuario);
                startActivity(i);
            }
        }, 1000);
    }

    //Arrumando os spinners na activity perfil

    public void setandoSpinners(){

        //Spinner do tipo de perfil
        tipoPerfilArray.add("Tipo do perfil: ");
        tipoPerfilArray.add("Produtor");
        tipoPerfilArray.add("Técnico");
        tipoPerfilArray.add("Estudante");

        ArrayAdapter<String> tipoPerfilAdapter = new ArrayAdapter<>(PerfilActivity.this, android.R.layout.simple_spinner_item, tipoPerfilArray);
        tipoPerfilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPerfil.setAdapter(tipoPerfilAdapter);

        //Spinner dos estados
        estadosArray.add("Selecione o Estado: ");
        estadosArray.add("Paraná");
        estadosArray.add("Santa Catarina");
        estadosArray.add("Rio Grande do Sul");

        ArrayAdapter<String> estadosAdapter = new ArrayAdapter<>(PerfilActivity.this, android.R.layout.simple_spinner_item, estadosArray);
        estadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(estadosAdapter);


        //Spinner das cidades
        cidadesArray.add("Selecione a Cidade: ");

        //Array de cidades de santa catarina
        cidadesScArray.add("Lages");
        cidadesScArray.add("Opa");
        cidadesScArray.add("sdsi");
        cidadesScArray.add("dgdfgd");

        for(int i =0; i<cidadesScArray.size(); i++){
            String a = cidadesScArray.get(i);
            cidadesArray.add(a);
        }


        ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<>(PerfilActivity.this, android.R.layout.simple_spinner_item, cidadesArray);
        cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(cidadesAdapter);
    }



    /**
     * Chamado no arquivo de layout
     */
    public void clicarVoltarPerfil(View view) {
        finish();
    }
}
