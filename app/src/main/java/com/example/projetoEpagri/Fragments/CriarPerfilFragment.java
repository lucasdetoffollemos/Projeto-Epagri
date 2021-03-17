package com.example.projetoEpagri.Fragments;

import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CriarPerfilFragment extends Fragment {
    private String nome_usuario;
    private ArrayList<String> cidades_pr, cidades_rs, cidades_sc, cidades;
    private boolean alterou_estado = false;

    public CriarPerfilFragment() {}

    public static CriarPerfilFragment newInstance() {
        CriarPerfilFragment fragment = new CriarPerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            cidades = new ArrayList<>();
            cidades_pr = parseJSON("cidades-pr.json");
            cidades_rs = parseJSON("cidades-rs.json");
            cidades_sc = parseJSON("cidades-sc.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return inflater.inflate(R.layout.fragment_criar_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View toolbar = getView().findViewById(R.id.included_toolbar);
        final ImageView iv_voltar = toolbar.findViewById(R.id.iv_voltar);
        final EditText et_nome = getView().findViewById(R.id.et_nome);
        final EditText et_email = getView().findViewById(R.id.et_email);
        final EditText et_telefone = getView().findViewById(R.id.et_telefone);
        final Spinner spinner_tipo_perfil = getView().findViewById(R.id.spinner_tipoPerfil);
        final Spinner spinner_estado = getView().findViewById(R.id.spinner_estado);
        final Spinner spinner_cidade = getView().findViewById(R.id.spinner_cidade);
        final EditText et_senha = getView().findViewById(R.id.et_senha);
        final Button bt_criar = getView().findViewById(R.id.bt_criar);

        setUpSpinners(spinner_tipo_perfil, spinner_estado, spinner_cidade);

        //Clique no botão "voltar".
        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Spinner perfil
        spinner_tipo_perfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinner estado
        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Lógica feita, para que quando o usuário clicar no estado x, o algoritimo irá adcionar no arrayList de cidades as cidades deste respectivo estado, e irá remover os arrayLists das cidades dos outros estados.

                if(position == 0){
                    cidades = new ArrayList<>();
                }else if (position == 1) {
                    cidades = new ArrayList<>(cidades_pr);
                } else if (position == 2) {
                    cidades = new ArrayList<>(cidades_rs);
                } else if (position == 3) {
                    cidades = new ArrayList<>(cidades_sc);
                }

                cidades.add(0, "Selecione a cidade");
                clearSpinner(spinner_cidade);

                ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cidades);
                cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_cidade.setAdapter(cidadesAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinner cidade
        spinner_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Logic
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    //Clique no botão "Criar".
        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, telefone, tipoPerfil, estado, cidade, senha;
                nome_usuario = et_nome.getText().toString().trim();  //trim remove espaços em branco antes e após a palavra.
                email = et_email.getText().toString().trim();
                telefone = et_telefone.getText().toString().trim();
                tipoPerfil = spinner_tipo_perfil.getSelectedItem().toString();
                estado = spinner_estado.getSelectedItem().toString();
                cidade = spinner_cidade.getSelectedItem().toString();
                senha = et_senha.getText().toString().trim();

                if((nome_usuario.length() > 0) && (email.length() > 0) && (telefone.length() > 0) && !tipoPerfil.equals("Selecione o perfil") && !estado.equals("Selecione o Estado") && !cidade.equals("Selecione a cidade") && (senha.length() > 0 )){
                    if(email.contains("@") && email.contains(".")){
                        criarPerfil(nome_usuario, email, telefone, tipoPerfil, estado, cidade, senha);
                        bt_criar.setEnabled(false);
                    }
                    else{
                        Toast.makeText(getActivity(), "Formato de e-mail inválido! ", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método utilizado cada vez que o usuário trocar o estado, limpar o spinner cidade(Para nao ficar cidades do estado )
     */
    public void clearSpinner(Spinner s){
        s.setSelection(0,true);
    }

    public void setUpSpinners(Spinner spinner_tipo_perfil, Spinner spinner_estado, Spinner spinner_cidade){
        //Spinner perfil.
        ArrayList<String> array_perfil = new ArrayList<>();
        array_perfil.add("Selecione o perfil");
        array_perfil.add("Produtor");
        array_perfil.add("Técnico");
        array_perfil.add("Estudante");

        ArrayAdapter<String> tipoPerfilAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_perfil);
        tipoPerfilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_perfil.setAdapter(tipoPerfilAdapter);

        //Spinner estados.
        ArrayList<String> array_estados = new ArrayList<>();
        array_estados.add("Selecione o estado");
        array_estados.add("Paraná");
        array_estados.add("Rio Grande do Sul");
        array_estados.add("Santa Catarina");

        ArrayAdapter<String> estadosAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_estados);
        estadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado.setAdapter(estadosAdapter);

        //Spinner das cidades
        cidades.add("Selecione a cidade");

        //Adionando o array de cidades dentro do spinner cidade
        // (Obs: O array cidade é determinado no método setListener, dentro do onItemSelect dos estados, onde para cada estado,
        // será adicionado uma lista de cidades diferentes.)
        ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cidades);
        cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cidade.setAdapter(cidadesAdapter);
    }

    /**
     * Método responsável por criar um usuário e salvar no banco de dados.
     */
    public void criarPerfil(String nome, String email, String telefone, String tipoPerfil, String estado, String cidade, String senha){
        Usuario u = new Usuario(nome, email, telefone, tipoPerfil, estado, cidade, senha);
        BancoDeDados.usuarioDAO.inserirUsuario(u);
        Toast.makeText(getActivity(), "Usuario criado com sucesso! ", Toast.LENGTH_SHORT).show();


        //O código abaixo aguarda 2 seg e redireciona o usuário para o IndexFragment.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivityIndex(nome_usuario);
            }
        }, 1000);
    }

    /**
     * Método responsável por iniciar a ActivityIndex.
     * @param nome_usuario
     */
    public void startActivityIndex(String nome_usuario){
        Intent i = new Intent(getActivity(), IndexActivity.class);
        i.putExtra("nome_usuario", nome_usuario);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        //Destroi a MainActivity.
        getActivity().finish();
    }

    /**
     * Método responsável por retornar o arquivo JSON.
     * @param nome_arquivo Nome do arquivo.
     * @return Arquivo JSON.
     */
    public String loadJSONFromAsset(String nome_arquivo) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(nome_arquivo);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Método responsável por percorrer o arquivo JSON e adicionar as cidades numa lista de strings.
     * @param nome_arquivo Nome do arquivo JSON.
     * @return Lista de string contendo as cidades encontradas no arquivo.
     * @throws JSONException Lança uma exceção caso não consiga criar um objeto a partir do arquivo JSON.
     */
    public ArrayList<String> parseJSON(String nome_arquivo) throws JSONException {
        ArrayList<String> lista_cidades = new ArrayList<>();

        JSONObject object = new JSONObject(loadJSONFromAsset(nome_arquivo));
        JSONArray cidades = object.getJSONArray("cidades");

        for (int j = 0; j < cidades.length(); j++) {
            lista_cidades.add(cidades.getString(j));
        }

        return lista_cidades;
    }
}