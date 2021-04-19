package com.example.projetoEpagri.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Propriedade;
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

public class EditarPerfilFragment extends Fragment {
    private int id_usuario;
    private Usuario usuario;
    private DrawerLayout drawer_layout;
    private ArrayList<String> cidades_pr, cidades_rs, cidades_sc, cidades;
    private boolean alterou_estado = false;

    public EditarPerfilFragment() {}

    public static EditarPerfilFragment newInstance() {
        EditarPerfilFragment fragment = new EditarPerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id_usuario = BancoDeDados.usuarioDAO.getUSuarioId(IndexActivity.nome_usuario);
        usuario = BancoDeDados.usuarioDAO.getUsuario(id_usuario);

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
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_menu = getView().findViewById(R.id.iv_menu);
        final EditText et_editar_nome = getView().findViewById(R.id.et_editar_nome);
        final EditText et_editar_email = getView().findViewById(R.id.et_editar_email);
        final EditText et_editar_telefone = getView().findViewById(R.id.et_editar_telefone);
        final Spinner spinner_editar_perfil = getView().findViewById(R.id.spinner_tipoPerfil);
        final Spinner spinner_editar_estado = getView().findViewById(R.id.spinner_estado);
        final Spinner spinner_editar_cidade = getView().findViewById(R.id.spinner_cidade);
        final EditText et_editar_senha = getView().findViewById(R.id.et_editar_senha);
        final Button bt_atualizar = getView().findViewById(R.id.bt_atualizar);
        final Button bt_cancelar = getView().findViewById(R.id.bt_cancelar);
        final Button bt_excluir = getView().findViewById(R.id.bt_excluir);
        et_editar_nome.setText(usuario.getNome());
        et_editar_email.setText(usuario.getEmail());
        et_editar_telefone.setText(usuario.getTelefone());
        et_editar_senha.setText(IndexActivity.senha);

        drawer_layout = getView().findViewById(R.id.drawerLayout);
        final View menu_drawer = getView().findViewById(R.id.included_nav_drawer);
        final LinearLayout ll_menu_inicio = getView().findViewById(R.id.ll_menu_inicio);
        final LinearLayout ll_menu_perfil = getView().findViewById(R.id.ll_menu_perfil);
        final LinearLayout ll_menu_sobre = getView().findViewById(R.id.ll_menu_sobre);

        TextView nome, email;
        nome = menu_drawer.findViewById(R.id.tv_nomeDinamico);
        email = menu_drawer.findViewById(R.id.tv_emailDinamico);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());

        setUpSpinners(spinner_editar_perfil, spinner_editar_estado, spinner_editar_cidade);
        spinner_editar_perfil.setSelection(getSpinnerIndex(spinner_editar_perfil, usuario.getTipo_perfil()));
        spinner_editar_estado.setSelection(getSpinnerIndex(spinner_editar_estado, usuario.getEstado()));

        //Clique no ícone do menu.
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IndexActivity)getActivity()).abrirMenu(drawer_layout);
            }
        });

        //Clique no item "Início" do menu.
        ll_menu_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Clique no item "Perfil" do menu.
        ll_menu_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IndexActivity)getActivity()).fecharMenu(drawer_layout);
            }
        });

        //Clique no item "Sobre"do menu.
        ll_menu_sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                ((IndexActivity)getActivity()).clickMenuItemSobre(v);
            }
        });

        //Spinner perfil
        spinner_editar_perfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinner estado
        spinner_editar_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Lógica feita, para que quando o usuário clicar no estado x, o algoritimo irá adcionar no arrayList de cidades as cidades deste respectivo estado, e irá remover os arrayLists das cidades dos outros estados.

                if (position == 1) {
                    cidades = new ArrayList<>(cidades_pr);
                } else if (position == 2) {
                    cidades = new ArrayList<>(cidades_rs);
                } else if (position == 3) {
                    cidades = new ArrayList<>(cidades_sc);
                }

                cidades.add(0, "Selecione a cidade");
                clearSpinner(spinner_editar_cidade);

                ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cidades);
                cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_editar_cidade.setAdapter(cidadesAdapter);

                if(alterou_estado == false){
                    spinner_editar_cidade.setSelection(getSpinnerIndex(spinner_editar_cidade, usuario.getCidade()));
                    alterou_estado = true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner cidade
        spinner_editar_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String nome, email, telefone, tipo_perfil, estado, cidade, senha;
                nome = et_editar_nome.getText().toString().trim();
                email = et_editar_email.getText().toString().trim();
                telefone = et_editar_telefone.getText().toString().trim();
                tipo_perfil = spinner_editar_perfil.getSelectedItem().toString();
                estado = spinner_editar_estado.getSelectedItem().toString();
                cidade = spinner_editar_cidade.getSelectedItem().toString();
                senha = et_editar_senha.getText().toString().trim();

                atualizarDados(nome, email, telefone, tipo_perfil, estado, cidade, senha);
            }
        });

        bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario(id_usuario, bt_excluir);
            }
        });
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
        array_estados.add("Selecione o Estado");
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

    private void atualizarDados(final String nome, final String email, final String telefone, final String tipoPerfil, final String estado, final String cidade,final String senha) {
        if(usuario != null){
            String nome_banco = usuario.getNome();
            String email_banco = usuario.getEmail();
            String telefone_banco = usuario.getTelefone();
            String tipo_perfil_banco = usuario.getTipo_perfil();
            String estado_banco = usuario.getEstado();
            String cidade_banco = usuario.getCidade();
            String senha_banco_hashed = usuario.getSenha();
            final String senha_hashed = MainActivity.bancoDeDados.bin2hex(MainActivity.bancoDeDados.generateHash((nome_banco+senha)));

            //Verifica se todos os campos estão preenchidos.
            if(nome.length() > 0 && email.length() > 0 && telefone.length() > 0 && tipoPerfil.length() > 0 && estado.length() > 0 && cidade.length() > 0 && senha.length() > 0 ){
                //Verifica se o formato de e-mail é válido.
                if(email.contains("@") && email.contains(".")){
                    if(telefone.length() == 11){
                        //Verifica se os novos dados são diferentes dos anteriores.
                        if(nome_banco.equals(nome) && email_banco.equals(email) && telefone_banco.equals(telefone) && tipo_perfil_banco.equals(tipoPerfil) && estado_banco.equals(estado) && cidade_banco.equals(cidade) && senha_banco_hashed.equals(senha_hashed)){
                            Toast.makeText(getActivity(), "Os novos dados devem ser diferentes dos existentes!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("ATENÇÃO");
                            builder.setMessage( "Tem certeza que deseja continuar?" );
                            builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BancoDeDados.usuarioDAO.updateUsuario(id_usuario, nome, email, telefone, tipoPerfil, estado, cidade, senha_hashed);
                                    Toast.makeText(getActivity(), "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();

                                    //Essa linha é necessária pois a ActivityIndex não é recriada após a atualização do nome
                                    //no EditarPerfilFragment. Sendo assim, o valor atualizado do nome é perdido e ao voltar
                                    //para o EditarPerfilFragment, não consegue-se recuperar os dados do banco a partir do nome.
                                    IndexActivity.nome_usuario = nome;
                                    IndexActivity.senha = senha;
                                    getFragmentManager().popBackStack();
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
                    }
                    else{
                        Toast.makeText(getActivity(), "Formato de telefone inválido!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Formato de e-mail inválido! ", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getActivity(), "Por favor, preencha todos os campos! ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * Método responsável por excluir a conta do usuário e fazer o logout.
     * @param id Id do usuário.
     */
    private void excluirUsuario(final int id, final Button bt_excluir) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("ATENÇÃO");
        builder.setMessage( "Tem certeza que deseja excluir seu perfil?" );
        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BancoDeDados.usuarioDAO.deleteUsuarioById(id);

                Toast.makeText(getActivity(), "Conta excluída com sucesso!", Toast.LENGTH_SHORT).show();
                bt_excluir.setEnabled(false);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                }, 1000);

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

    /**
     * Método utilizado para fechar o menu quando um novo fragment é exibido.
     */
    public void onPause() {
        ((IndexActivity)getActivity()).fecharMenu(drawer_layout);
        super.onPause();
    }

    /**
     * Método responsável por identificar a posição de um determinado valor dentro do spinner.
     * @param spinner Representa o spinner que contém os valores.
     * @param s Representa o valor que deseja-se encontrar.
     * @return Retorna a posição do valor dentro da lista de valores do spinner.
     */
    private int getSpinnerIndex(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    /**
     * Método utilizado cada vez que o usuário trocar o estado, limpar o spinner cidade(Para nao ficar cidades do estado )
     */
    public void clearSpinner(Spinner s){
        s.setSelection(0,true);
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