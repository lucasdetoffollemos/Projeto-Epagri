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
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class EditarPerfilFragment extends Fragment {
    private int id_usuario;
    private Usuario usuario;
    private DrawerLayout drawer_layout;
    private Spinner spinnerTipoPerfil, spinnerEstado, spinnerCidade;
    private ArrayList<String> tipoPerfilArray, estadosArray, cidadesArray, cidadesPrArray, cidadesScArray, cidadesRsArray;
    private String [] cidadesSc = new String[293], cidadesPr = new String[399], cidadesRs = new String[496];
    private int posicaoSpinnerTipoPerfil, posicaoSpinnerEstado, posicaoSpinnerCidade;

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
        final EditText et_editar_senha = getView().findViewById(R.id.et_editar_senha);
        final Button bt_atualizar = getView().findViewById(R.id.bt_atualizar);
        final Button bt_cancelar = getView().findViewById(R.id.bt_cancelar);
        final Button bt_excluir = getView().findViewById(R.id.bt_excluir);



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

        ///
        //arraylists
        tipoPerfilArray = new ArrayList();
        estadosArray = new ArrayList();
        cidadesArray = new ArrayList();

        //Arrays da cidades de cada estado
        cidadesPrArray = new ArrayList();
        cidadesScArray = new ArrayList();
        cidadesRsArray = new ArrayList();

        //Spinners
        spinnerTipoPerfil = (Spinner) getView().findViewById(R.id.spinner_tipoPerfil);
        spinnerEstado = getView().findViewById(R.id.spinner_estado);
        spinnerCidade = getView().findViewById(R.id.spinner_cidade);

        //colocando os dados que foram inseridos no cadastro do usuário, no edit text's e spinners respectivos de cada campo
        et_editar_nome.setText(usuario.getNome());
        et_editar_email.setText(usuario.getEmail());
        et_editar_telefone.setText(usuario.getTelefone());

        //pegar o valor dos spinners pra setar

        et_editar_senha.setText(usuario.getSenha());


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
                tipo_perfil = spinnerTipoPerfil.getSelectedItem().toString();
                estado = spinnerEstado.getSelectedItem().toString();
                cidade = spinnerCidade.getSelectedItem().toString();
                senha = et_editar_senha.getText().toString().trim();

                if((nome.length() > 0) && (email.length() > 0) && (telefone.length() > 0) && ( posicaoSpinnerTipoPerfil > 0) && (posicaoSpinnerEstado > 0) && (posicaoSpinnerCidade > 0) && (senha.length() > 0 )){
                    atualizarDados(nome, email, telefone, tipo_perfil, estado, cidade, senha);

                }
                else{
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario(id_usuario);
            }
        });

        setandoSpinners();
        //Spinner perfil
        spinnerTipoPerfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicaoSpinnerTipoPerfil = position;
                if(position > 0){
                    //Logic
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinner estado
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicaoSpinnerEstado = position;
                //Lógica feita, para que quando o usuário clicar no estado x, o algoritimo irá adcionar no arrayList de cidades as cidades deste respectivo estado, e irá remover os arrayLists das cidades dos outros estados.
                if(position == 1){
                    clearSpinnerCidade();
                    for(int i =0; i<cidadesPrArray.size(); i++){
                        String cidade = cidadesPrArray.get(i);
                        cidadesArray.add(cidade);
                    }
                    cidadesArray.removeAll(cidadesScArray);
                    cidadesArray.removeAll(cidadesRsArray);

                }
                else if(position  == 2){
                    clearSpinnerCidade();
                    for(int i =0; i<cidadesScArray.size(); i++){
                        String cidade = cidadesScArray.get(i);
                        cidadesArray.add(cidade);
                    }
                    cidadesArray.removeAll(cidadesPrArray);
                    cidadesArray.removeAll(cidadesRsArray);

                }
                else if(position  == 3){
                    clearSpinnerCidade();
                    for(int i =0; i<cidadesRsArray.size(); i++){
                        String cidade = cidadesRsArray.get(i);
                        cidadesArray.add(cidade);
                    }
                    cidadesArray.removeAll(cidadesPrArray);
                    cidadesArray.removeAll(cidadesScArray);
                }
                else{
                    cidadesArray.removeAll(cidadesPrArray);
                    cidadesArray.removeAll(cidadesScArray);
                    cidadesArray.removeAll(cidadesRsArray);
                    clearSpinnerCidade();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinner cidade
        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicaoSpinnerCidade = position;
                //Logic
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        spinnerTipoPerfil.setSelection(getSpinnerIndex(spinnerTipoPerfil, usuario.getTipo_perfil()));
        spinnerEstado.setSelection(getSpinnerIndex(spinnerEstado, usuario.getEstado()));
        spinnerCidade.setSelection(getSpinnerIndex(spinnerCidade, usuario.getCidade()));

    }

    private void atualizarDados(final String nome, final String email, final String telefone, final String tipoPerfil, final String estado, final String cidade, final String senha) {
        if(usuario != null){
            String nome_banco = usuario.getNome();
            String email_banco = usuario.getEmail();
            String telefone_banco = usuario.getTelefone();
            String tipo_perfil_banco = usuario.getTipo_perfil();
            String estado_banco = usuario.getEstado();
            String cidade_banco = usuario.getCidade();
            String senha_banco = usuario.getSenha();

            //Verifica se os novos dados são iguais aos anteriores.
            if(nome_banco.equals(nome) && email_banco.equals(email) && telefone_banco.equals(telefone) && tipo_perfil_banco.equals(tipoPerfil) && estado_banco.equals(estado) && cidade_banco.equals(cidade) && senha_banco.equals(senha)){
                Toast.makeText(getActivity(), "Os novos dados devem ser diferentes dos existentes!", Toast.LENGTH_SHORT).show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("ATENÇÃO");
                builder.setMessage( "Tem certeza que deseja continuar?" );
                builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BancoDeDados.usuarioDAO.updateUsuario(id_usuario, nome, email, telefone, tipoPerfil, estado, cidade, senha);
                        Toast.makeText(getActivity(), "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();

                        //Essa linha é necessária pois a ActivityIndex não é recriada após a atualização do nome
                        //no EditarPerfilFragment. Sendo assim, o valor atualizado do nome é perdido e ao voltar
                        //para o EditarPerfilFragment, não consegue-se recuperar os dados do banco a partir do nome.
                        IndexActivity.nome_usuario = nome;
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
    }

    /**
     * Método responsável por excluir a conta do usuário e fazer o logout.
     * @param id Id do usuário.
     */
    private void excluirUsuario(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("ATENÇÃO");
        builder.setMessage( "Tem certeza que deseja excluir seu perfil?" );
        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BancoDeDados.propriedadeDAO.deletePropriedadeById(id_usuario);
                BancoDeDados.usuarioDAO.deleteUsuarioById(id);
                Toast.makeText(getActivity(), "Conta excluída com sucesso!", Toast.LENGTH_SHORT).show();

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
     * Método utilizado cada vez que o usuário trocar o estado, limpar o spinner cidade(Para nao ficar cidades do estado )
     */
    public void clearSpinnerCidade(){
        spinnerCidade.setSelection(0,true);
    }

    /**
     * Método responsável por adicionar cada arrayList no seu respectivo Spinner
     */
    public void setandoSpinners(){

        inicalizaArray();

        //Spinner do tipo de perfil
        tipoPerfilArray.add("Tipo do perfil: ");
        tipoPerfilArray.add("Produtor");
        tipoPerfilArray.add("Técnico");
        tipoPerfilArray.add("Estudante");

        ArrayAdapter<String> tipoPerfilAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tipoPerfilArray);
        tipoPerfilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPerfil.setAdapter(tipoPerfilAdapter);

        //Spinner dos estados
        estadosArray.add("Selecione o Estado: ");
        estadosArray.add("Paraná");
        estadosArray.add("Santa Catarina");
        estadosArray.add("Rio Grande do Sul");

        ArrayAdapter<String> estadosAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, estadosArray);
        estadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(estadosAdapter);


        //Spinner das cidades
        cidadesArray.add("Selecione a Cidade: ");

        //Array de cidades do parana
        cidadesPrArray = new ArrayList<>(Arrays.asList(cidadesPr));

        //Array de cidades de santa catarina
        cidadesScArray = new ArrayList<>(Arrays.asList(cidadesSc));

        //Array de cidades do rio grande do sul
        cidadesRsArray = new ArrayList<>(Arrays.asList(cidadesRs));

        //Adionando o array de cidades dentro do spinner cidade (Obs: O array cidade é determinado no método setListener, dentro do onItemSelect dos estados, onde para cada estado, será adicionado uma lista de cidades diferentes.)
        ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cidadesArray);
        cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(cidadesAdapter);
    }


    private int getSpinnerIndex(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    /**
     * Método utilizado para fechar o menu quando um novo fragment é exibido.
     */
    public void onPause() {
        ((IndexActivity)getActivity()).fecharMenu(drawer_layout);
        super.onPause();
    }

    /**
     * Método que contém os array's com as cidades de cada estado do sul, estes arrays serão chamados no método setandoSpinners(), e serão convertidos em arrayLists.
     */
    public void inicalizaArray() {
        cidadesPr = new String[]{
                "Abatiá",
                "Adrianópolis",
                "Agudos do Sul",
                "Almirante Tamandaré",
                "Altamira do Paraná",
                "Alto Paraná",
                "Alto Piquiri",
                "Altônia",
                "Alvorada do Sul",
                "Amaporã",
                "Ampére",
                "Anahy",
                "Andirá",
                "Ângulo",
                "Antonina",
                "Antônio Olinto",
                "Apucarana",
                "Arapongas",
                "Arapoti",
                "Arapuã",
                "Araruna",
                "Araucária",
                "Ariranha do Ivaí",
                "Assaí",
                "Assis Chateaubriand",
                "Astorga",
                "Atalaia",
                "Balsa Nova",
                "Bandeirantes",
                "Barbosa Ferraz",
                "Barra do Jacaré",
                "Barracão",
                "Bela Vista da Caroba",
                "Bela Vista do Paraíso",
                "Bituruna",
                "Boa Esperança",
                "Boa Esperança do Iguaçu",
                "Boa Ventura de São Roque",
                "Boa Vista da Aparecida",
                "Bocaiúva do Sul",
                "Bom Jesus do Sul",
                "Bom Sucesso",
                "Bom Sucesso do Sul",
                "Borrazópolis",
                "Braganey",
                "Brasilândia do Sul",
                "Cafeara",
                "Cafelândia",
                "Cafezal do Sul",
                "Califórnia",
                "Cambará",
                "Cambé",
                "Cambira",
                "Campina da Lagoa",
                "Campina do Simão",
                "Campina Grande do Sul",
                "Campo Bonito",
                "Campo do Tenente",
                "Campo Largo",
                "Campo Magro",
                "Campo Mourão",
                "Cândido de Abreu",
                "Candói",
                "Cantagalo",
                "Capanema",
                "Capitão Leônidas Marques",
                "Carambeí",
                "Carlópolis",
                "Cascavel",
                "Castro",
                "Catanduvas",
                "Centenário do Sul",
                "Cerro Azul",
                "Céu Azul",
                "Chopinzinho",
                "Cianorte",
                "Cidade Gaúcha",
                "Clevelândia",
                "Colombo",
                "Colorado",
                "Congonhinhas",
                "Conselheiro Mairinck",
                "Contenda",
                "Corbélia",
                "Cornélio Procópio",
                "Coronel Domingos Soares",
                "Coronel Vivida",
                "Corumbataí do Sul",
                "Cruz Machado",
                "Cruzeiro do Iguaçu",
                "Cruzeiro do Oeste",
                "Cruzeiro do Sul",
                "Cruzmaltina",
                "Curitiba",
                "Curiúva",
                "Diamante d'Oeste",
                "Diamante do Norte",
                "Diamante do Sul",
                "Dois Vizinhos",
                "Douradina",
                "Doutor Camargo",
                "Doutor Ulysses",
                "Enéas Marques",
                "Engenheiro Beltrão",
                "Entre Rios do Oeste",
                "Esperança Nova",
                "Espigão Alto do Iguaçu",
                "Farol",
                "Faxinal",
                "Fazenda Rio Grande",
                "Fênix",
                "Fernandes Pinheiro",
                "Figueira",
                "Flor da Serra do Sul",
                "Floraí",
                "Floresta",
                "Florestópolis",
                "Flórida",
                "Formosa do Oeste",
                "Foz do Iguaçu",
                "Foz do Jordão",
                "Francisco Alves",
                "Francisco Beltrão",
                "General Carneiro",
                "Godoy Moreira",
                "Goioerê",
                "Goioxim",
                "Grandes Rios",
                "Guaíra",
                "Guairaçá",
                "Guamiranga",
                "Guapirama",
                "Guaporema",
                "Guaraci",
                "Guaraniaçu",
                "Guarapuava",
                "Guaraqueçaba",
                "Guaratuba",
                "Honório Serpa",
                "Ibaiti",
                "Ibema",
                "Ibiporã",
                "Icaraíma",
                "Iguaraçu",
                "Iguatu",
                "Imbaú",
                "Imbituva",
                "Inácio Martins",
                "Inajá",
                "Indianópolis",
                "Ipiranga",
                "Iporã",
                "Iracema do Oeste",
                "Irati",
                "Iretama",
                "Itaguajé",
                "Itaipulândia",
                "Itambaracá",
                "Itambé",
                "Itapejara d'Oeste",
                "Itaperuçu",
                "Itaúna do Sul",
                "Ivaí",
                "Ivaiporã",
                "Ivaté",
                "Ivatuba",
                "Jaboti",
                "Jacarezinho",
                "Jaguapitã",
                "Jaguariaíva",
                "Jandaia do Sul",
                "Janiópolis",
                "Japira",
                "Japurá",
                "Jardim Alegre",
                "Jardim Olinda",
                "Jataizinho",
                "Jesuítas",
                "Joaquim Távora",
                "Jundiaí do Sul",
                "Juranda",
                "Jussara",
                "Kaloré",
                "Lapa",
                "Laranjal",
                "Laranjeiras do Sul",
                "Leópolis",
                "Lidianópolis",
                "Lindoeste",
                "Loanda",
                "Lobato",
                "Londrina",
                "Luiziana",
                "Lunardelli",
                "Lupionópolis",
                "Mallet",
                "Mamborê",
                "Mandaguaçu",
                "Mandaguari",
                "Mandirituba",
                "Manfrinópolis",
                "Mangueirinha",
                "Manoel Ribas",
                "Marechal Cândido Rondon",
                "Maria Helena",
                "Marialva",
                "Marilândia do Sul",
                "Marilena",
                "Mariluz",
                "Maringá",
                "Mariópolis",
                "Maripá",
                "Marmeleiro",
                "Marquinho",
                "Marumbi",
                "Matelândia",
                "Matinhos",
                "Mato Rico",
                "Mauá da Serra",
                "Medianeira",
                "Mercedes",
                "Mirador",
                "Miraselva",
                "Missal",
                "Moreira Sales",
                "Morretes",
                "Munhoz de Melo",
                "Nossa Senhora das Graças",
                "Nova Aliança do Ivaí",
                "Nova América da Colina",
                "Nova Aurora",
                "Nova Cantu",
                "Nova Esperança",
                "Nova Esperança do Sudoeste",
                "Nova Fátima",
                "Nova Laranjeiras",
                "Nova Londrina",
                "Nova Olímpia",
                "Nova Prata do Iguaçu",
                "Nova Santa Bárbara",
                "Nova Santa Rosa",
                "Nova Tebas",
                "Novo Itacolomi",
                "Ortigueira",
                "Ourizona",
                "Ouro Verde do Oeste",
                "Paiçandu",
                "Palmas",
                "Palmeira",
                "Palmital",
                "Palotina",
                "Paraíso do Norte",
                "Paranacity",
                "Paranaguá",
                "Paranapoema",
                "Paranavaí",
                "Pato Bragado",
                "Pato Branco",
                "Paula Freitas",
                "Paulo Frontin",
                "Peabiru",
                "Perobal",
                "Pérola",
                "Pérola d'Oeste",
                "Piên",
                "Pinhais",
                "Pinhal de São Bento",
                "Pinhalão",
                "Pinhão",
                "Piraí do Sul",
                "Piraquara",
                "Pitanga",
                "Pitangueiras",
                "Planaltina do Paraná",
                "Planalto",
                "Ponta Grossa",
                "Pontal do Paraná",
                "Porecatu",
                "Porto Amazonas",
                "Porto Barreiro",
                "Porto Rico",
                "Porto Vitória",
                "Prado Ferreira",
                "Pranchita",
                "Presidente Castelo Branco",
                "Primeiro de Maio",
                "Prudentópolis",
                "Quarto Centenário",
                "Quatiguá",
                "Quatro Barras",
                "Quatro Pontes",
                "Quedas do Iguaçu",
                "Querência do Norte",
                "Quinta do Sol",
                "Quitandinha",
                "Ramilândia",
                "Rancho Alegre",
                "Rancho Alegre d'Oeste",
                "Realeza",
                "Rebouças",
                "Renascença",
                "Reserva",
                "Reserva do Iguaçu",
                "Ribeirão Claro",
                "Ribeirão do Pinhal",
                "Rio Azul",
                "Rio Bom",
                "Rio Bonito do Iguaçu",
                "Rio Branco do Ivaí",
                "Rio Branco do Sul",
                "Rio Negro",
                "Rolândia",
                "Roncador",
                "Rondon",
                "Rosário do Ivaí",
                "Sabáudia",
                "Salgado Filho",
                "Salto do Itararé",
                "Salto do Lontra",
                "Santa Amélia",
                "Santa Cecília do Pavão",
                "Santa Cruz Monte Castelo",
                "Santa Fé",
                "Santa Helena",
                "Santa Inês",
                "Santa Isabel do Ivaí",
                "Santa Izabel do Oeste",
                "Santa Lúcia",
                "Santa Maria do Oeste",
                "Santa Mariana",
                "Santa Mônica",
                "Santa Tereza do Oeste",
                "Santa Terezinha de Itaipu",
                "Santana do Itararé",
                "Santo Antônio da Platina",
                "Santo Antônio do Caiuá",
                "Santo Antônio do Paraíso",
                "Santo Antônio do Sudoeste",
                "Santo Inácio",
                "São Carlos do Ivaí",
                "São Jerônimo da Serra",
                "São João",
                "São João do Caiuá",
                "São João do Ivaí",
                "São João do Triunfo",
                "São Jorge d'Oeste",
                "São Jorge do Ivaí",
                "São Jorge do Patrocínio",
                "São José da Boa Vista",
                "São José das Palmeiras",
                "São José dos Pinhais",
                "São Manoel do Paraná",
                "São Mateus do Sul",
                "São Miguel do Iguaçu",
                "São Pedro do Iguaçu",
                "São Pedro do Ivaí",
                "São Pedro do Paraná",
                "São Sebastião da Amoreira",
                "São Tomé",
                "Sapopema",
                "Sarandi",
                "Saudade do Iguaçu",
                "Sengés",
                "Serranópolis do Iguaçu",
                "Sertaneja",
                "Sertanópolis",
                "Siqueira Campos",
                "Sulina",
                "Tamarana",
                "Tamboara",
                "Tapejara",
                "Tapira",
                "Teixeira Soares",
                "Telêmaco Borba",
                "Terra Boa",
                "Terra Rica",
                "Terra Roxa",
                "Tibagi",
                "Tijucas do Sul",
                "Toledo",
                "Tomazina",
                "Três Barras do Paraná",
                "Tunas do Paraná",
                "Tuneiras do Oeste",
                "Tupãssi",
                "Turvo",
                "Ubiratã",
                "Umuarama",
                "União da Vitória",
                "Uniflor",
                "Uraí",
                "Ventania",
                "Vera Cruz do Oeste",
                "Verê",
                "Vila Alta",
                "Virmond",
                "Vitorino",
                "Wenceslau Braz",
                "Xambrê"
        };

        cidadesSc = new String[]{
                "Abdon Batista",
                "Abelardo Luz",
                "Agrolândia",
                "Agronômica",
                "Água Doce",
                "Águas de Chapecó",
                "Águas Frias",
                "Águas Mornas",
                "Alfredo Wagner",
                "Alto Bela Vista",
                "Anchieta",
                "Angelina",
                "Anita Garibaldi",
                "Anitápolis",
                "Antônio Carlos",
                "Apiúna",
                "Arabutã",
                "Araquari",
                "Araranguá",
                "Armazém",
                "Arroio Trinta",
                "Arvoredo",
                "Ascurra",
                "Atalanta",
                "Aurora",
                "Balneário Arroio do Silva",
                "Balneário Barra do Sul",
                "Balneário Camboriú",
                "Balneário Gaivota",
                "Bandeirante",
                "Barra Bonita",
                "Barra Velha",
                "Bela Vista do Toldo",
                "Belmonte",
                "Benedito Novo",
                "Biguaçu",
                "Blumenau",
                "Bocaina do Sul",
                "Bom Jardim da Serra",
                "Bom Jesus",
                "Bom Jesus do Oeste",
                "Bom Retiro",
                "Bombinhas",
                "Botuverá",
                "Braço do Norte",
                "Braço do Trombudo",
                "Brunópolis",
                "Brusque",
                "Caçador",
                "Caibi",
                "Calmon",
                "Camboriú",
                "Campo Alegre",
                "Campo Belo do Sul",
                "Campo Erê",
                "Campos Novos",
                "Canelinha",
                "Canoinhas",
                "Capão Alto",
                "Capinzal",
                "Capivari de Baixo",
                "Catanduvas",
                "Caxambu do Sul",
                "Celso Ramos",
                "Cerro Negro",
                "Chapadão do Lageado",
                "Chapecó",
                "Cocal do Sul",
                "Concórdia",
                "Cordilheira Alta",
                "Coronel Freitas",
                "Coronel Martins",
                "Correia Pinto",
                "Corupá",
                "Criciúma",
                "Cunha Porã",
                "Cunhataí",
                "Curitibanos",
                "Descanso",
                "Dionísio Cerqueira",
                "Dona Emma",
                "Doutor Pedrinho",
                "Entre Rios",
                "Ermo",
                "Erval Velho",
                "Faxinal dos Guedes",
                "Flor do Sertão",
                "Florianópolis",
                "Formosa do Sul",
                "Forquilhinha",
                "Fraiburgo",
                "Frei Rogério",
                "Galvão",
                "Garopaba",
                "Garuva",
                "Gaspar",
                "Governador Celso Ramos",
                "Grão Pará",
                "Gravatal",
                "Guabiruba",
                "Guaraciaba",
                "Guaramirim",
                "Guarujá do Sul",
                "Guatambú",
                "Herval d'Oeste",
                "Ibiam",
                "Ibicaré",
                "Ibirama",
                "Içara",
                "Ilhota",
                "Imaruí",
                "Imbituba",
                "Imbuia",
                "Indaial",
                "Iomerê",
                "Ipira",
                "Iporã do Oeste",
                "Ipuaçu",
                "Ipumirim",
                "Iraceminha",
                "Irani",
                "Irati",
                "Irineópolis",
                "Itá",
                "Itaiópolis",
                "Itajaí",
                "Itapema",
                "Itapiranga",
                "Itapoá",
                "Ituporanga",
                "Jaborá",
                "Jacinto Machado",
                "Jaguaruna",
                "Jaraguá do Sul",
                "Jardinópolis",
                "Joaçaba",
                "Joinville",
                "José Boiteux",
                "Jupiá",
                "Lacerdópolis",
                "Lages",
                "Laguna",
                "Lajeado Grande",
                "Laurentino",
                "Lauro Muller",
                "Lebon Régis",
                "Leoberto Leal",
                "Lindóia do Sul",
                "Lontras",
                "Luiz Alves",
                "Luzerna",
                "Macieira",
                "Mafra",
                "Major Gercino",
                "Major Vieira",
                "Maracajá",
                "Maravilha",
                "Marema",
                "Massaranduba",
                "Matos Costa",
                "Meleiro",
                "Mirim Doce",
                "Modelo",
                "Mondaí",
                "Monte Carlo",
                "Monte Castelo",
                "Morro da Fumaça",
                "Morro Grande",
                "Navegantes",
                "Nova Erechim",
                "Nova Itaberaba",
                "Nova Trento",
                "Nova Veneza",
                "Novo Horizonte",
                "Orleans",
                "Otacílio Costa",
                "Ouro",
                "Ouro Verde",
                "Paial",
                "Painel",
                "Palhoça",
                "Palma Sola",
                "Palmeira",
                "Palmitos",
                "Papanduva",
                "Paraíso",
                "Passo de Torres",
                "Passos Maia",
                "Paulo Lopes",
                "Pedras Grandes",
                "Penha",
                "Peritiba",
                "Petrolândia",
                "Piçarras",
                "Pinhalzinho",
                "Pinheiro Preto",
                "Piratuba",
                "Planalto Alegre",
                "Pomerode",
                "Ponte Alta",
                "Ponte Alta do Norte",
                "Ponte Serrada",
                "Porto Belo",
                "Porto União",
                "Pouso Redondo",
                "Praia Grande",
                "Presidente Castelo Branco",
                "Presidente Getúlio",
                "Presidente Nereu",
                "Princesa",
                "Quilombo",
                "Rancho Queimado",
                "Rio das Antas",
                "Rio do Campo",
                "Rio do Oeste",
                "Rio do Sul",
                "Rio dos Cedros",
                "Rio Fortuna",
                "Rio Negrinho",
                "Rio Rufino",
                "Riqueza",
                "Rodeio",
                "Romelândia",
                "Salete",
                "Saltinho",
                "Salto Veloso",
                "Sangão",
                "Santa Cecília",
                "Santa Helena",
                "Santa Rosa de Lima",
                "Santa Rosa do Sul",
                "Santa Terezinha",
                "Santa Terezinha do Progresso",
                "Santiago do Sul",
                "Santo Amaro da Imperatriz",
                "São Bento do Sul",
                "São Bernardino",
                "São Bonifácio",
                "São Carlos",
                "São Cristovão do Sul",
                "São Domingos",
                "São Francisco do Sul",
                "São João Batista",
                "São João do Itaperiú",
                "São João do Oeste",
                "São João do Sul",
                "São Joaquim",
                "São José",
                "São José do Cedro",
                "São José do Cerrito",
                "São Lourenço do Oeste",
                "São Ludgero",
                "São Martinho",
                "São Miguel da Boa Vista",
                "São Miguel do Oeste",
                "São Pedro de Alcântara",
                "Saudades",
                "Schroeder",
                "Seara",
                "Serra Alta",
                "Siderópolis",
                "Sombrio",
                "Sul Brasil",
                "Taió",
                "Tangará",
                "Tigrinhos",
                "Tijucas",
                "Timbé do Sul",
                "Timbó",
                "Timbó Grande",
                "Três Barras",
                "Treviso",
                "Treze de Maio",
                "Treze Tílias",
                "Trombudo Central",
                "Tubarão",
                "Tunápolis",
                "Turvo",
                "União do Oeste",
                "Urubici",
                "Urupema",
                "Urussanga",
                "Vargeão",
                "Vargem",
                "Vargem Bonita",
                "Vidal Ramos",
                "Videira",
                "Vitor Meireles",
                "Witmarsum",
                "Xanxerê",
                "Xavantina",
                "Xaxim",
                "Zortéa"
        };

        cidadesRs = new String[]{
                "Aceguá",
                "Água Santa",
                "Agudo",
                "Ajuricaba",
                "Alecrim",
                "Alegrete",
                "Alegria",
                "Almirante Tamandaré do Sul",
                "Alpestre",
                "Alto Alegre",
                "Alto Feliz",
                "Alvorada",
                "Amaral Ferrador",
                "Ametista do Sul",
                "André da Rocha",
                "Anta Gorda",
                "Antônio Prado",
                "Arambaré",
                "Araricá",
                "Aratiba",
                "Arroio do Meio",
                "Arroio do Padre",
                "Arroio do Sal",
                "Arroio do Tigre",
                "Arroio dos Ratos",
                "Arroio Grande",
                "Arvorezinha",
                "Augusto Pestana",
                "Áurea",
                "Bagé",
                "Balneário Pinhal",
                "Barão",
                "Barão de Cotegipe",
                "Barão do Triunfo",
                "Barra do Guarita",
                "Barra do Quaraí",
                "Barra do Ribeiro",
                "Barra do Rio Azul",
                "Barra Funda",
                "Barracão",
                "Barros Cassal",
                "Benjamin Constan do Sul",
                "Bento Gonçalves",
                "Boa Vista das Missões",
                "Boa Vista do Buricá",
                "Boa Vista do Cadeado",
                "Boa Vista do Incra",
                "Boa Vista do Sul",
                "Bom Jesus",
                "Bom Princípio",
                "Bom Progresso",
                "Bom Retiro do Sul",
                "Boqueirão do Leão",
                "Bossoroca",
                "Bozano",
                "Braga",
                "Brochier",
                "Butiá",
                "Caçapava do Sul",
                "Cacequi",
                "Cachoeira do Sul",
                "Cachoeirinha",
                "Cacique Doble",
                "Caibaté",
                "Caiçara",
                "Camaquã",
                "Camargo",
                "Cambará do Sul",
                "Campestre da Serra",
                "Campina das Missões",
                "Campinas do Sul",
                "Campo Bom",
                "Campo Novo",
                "Campos Borges",
                "Candelária",
                "Cândido Godói",
                "Candiota",
                "Canela",
                "Canguçu",
                "Canoas",
                "Canudos do Vale",
                "Capão Bonito do Sul",
                "Capão da Canoa",
                "Capão do Cipó",
                "Capão do Leão",
                "Capela de Santana",
                "Capitão",
                "Capivari do Sul",
                "Caraá",
                "Carazinho",
                "Carlos Barbosa",
                "Carlos Gomes",
                "Casca",
                "Caseiros",
                "Catuípe",
                "Caxias do Sul",
                "Centenário",
                "Cerrito",
                "Cerro Branco",
                "Cerro Grande",
                "Cerro Grande do Sul",
                "Cerro Largo",
                "Chapada",
                "Charqueadas",
                "Charrua",
                "Chiapeta",
                "Chuí",
                "Chuvisca",
                "Cidreira",
                "Ciríaco",
                "Colinas",
                "Colorado",
                "Condor",
                "Constantina",
                "Coqueiro Baixo",
                "Coqueiros do Sul",
                "Coronel Barros",
                "Coronel Bicaco",
                "Coronel Pilar",
                "Cotiporã",
                "Coxilha",
                "Crissiumal",
                "Cristal",
                "Cristal do Sul",
                "Cruz Alta",
                "Cruzaltense",
                "Cruzeiro do Sul",
                "David Canabarro",
                "Derrubadas",
                "Dezesseis de Novembro",
                "Dilermando de Aguiar",
                "Dois Irmãos",
                "Dois Irmãos das Missões",
                "Dois Lajeados",
                "Dom Feliciano",
                "Dom Pedrito",
                "Dom Pedro de Alcântara",
                "Dona Francisca",
                "Doutor Maurício Cardoso",
                "Doutor Ricardo",
                "Eldorado do Sul",
                "Encantado",
                "Encruzilhada do Sul",
                "Engenho Velho",
                "Entre Rios do Sul",
                "Entre-Ijuís",
                "Erebango",
                "Erechim",
                "Ernestina",
                "Erval Grande",
                "Erval Seco",
                "Esmeralda",
                "Esperança do Sul",
                "Espumoso",
                "Estação",
                "Estância Velha",
                "Esteio",
                "Estrela",
                "Estrela Velha",
                "Eugênio de Castro",
                "Fagundes Varela",
                "Farroupilha",
                "Faxinal do Soturno",
                "Faxinalzinho",
                "Fazenda Vilanova",
                "Feliz",
                "Flores da Cunha",
                "Floriano Peixoto",
                "Fontoura Xavier",
                "Formigueiro",
                "Forquetinha",
                "Fortaleza dos Valos",
                "Frederico Westphalen",
                "Garibaldi",
                "Garruchos",
                "Gaurama",
                "General Câmara",
                "Gentil",
                "Getúlio Vargas",
                "Giruá",
                "Glorinha",
                "Gramado",
                "Gramado dos Loureiros",
                "Gramado Xavier",
                "Gravataí",
                "Guabiju",
                "Guaíba",
                "Guaporé",
                "Guarani das Missões",
                "Harmonia",
                "Herval",
                "Herveiras",
                "Horizontina",
                "Hulha Negra",
                "Humaitá",
                "Ibarama",
                "Ibiaçá",
                "Ibiraiaras",
                "Ibirapuitã",
                "Ibirubá",
                "Igrejinha",
                "Ijuí",
                "Ilópolis",
                "Imbé",
                "Imigrante",
                "Independência",
                "Inhacorá",
                "Ipê",
                "Ipiranga do Sul",
                "Iraí",
                "Itaara",
                "Itacurubi",
                "Itapuca",
                "Itaqui",
                "Itati",
                "Itatiba do Sul",
                "Ivorá",
                "Ivoti",
                "Jaboticaba",
                "Jacuizinho",
                "Jacutinga",
                "Jaguarão",
                "Jaguari",
                "Jaquirana",
                "Jari",
                "Jóia",
                "Júlio de Castilhos",
                "Lagoa Bonita do Sul",
                "Lagoa dos Três Cantos",
                "Lagoa Vermelha",
                "Lagoão",
                "Lajeado",
                "Lajeado do Bugre",
                "Lavras do Sul",
                "Liberato Salzano",
                "Lindolfo Collor",
                "Linha Nova",
                "Maçambara",
                "Machadinho",
                "Mampituba",
                "Manoel Viana",
                "Maquiné",
                "Maratá",
                "Marau",
                "Marcelino Ramos",
                "Mariana Pimentel",
                "Mariano Moro",
                "Marques de Souza",
                "Mata",
                "Mato Castelhano",
                "Mato Leitão",
                "Mato Queimado",
                "Maximiliano de Almeida",
                "Minas do Leão",
                "Miraguaí",
                "Montauri",
                "Monte Alegre dos Campos",
                "Monte Belo do Sul",
                "Montenegro",
                "Mormaço",
                "Morrinhos do Sul",
                "Morro Redondo",
                "Morro Reuter",
                "Mostardas",
                "Muçum",
                "Muitos Capões",
                "Muliterno",
                "Não-Me-Toque",
                "Nicolau Vergueiro",
                "Nonoai",
                "Nova Alvorada",
                "Nova Araçá",
                "Nova Bassano",
                "Nova Boa Vista",
                "Nova Bréscia",
                "Nova Candelária",
                "Nova Esperança do Sul",
                "Nova Hartz",
                "Nova Pádua",
                "Nova Palma",
                "Nova Petrópolis",
                "Nova Prata",
                "Nova Ramada",
                "Nova Roma do Sul",
                "Nova Santa Rita",
                "Novo Barreiro",
                "Novo Cabrais",
                "Novo Hamburgo",
                "Novo Machado",
                "Novo Tiradentes",
                "Novo Xingu",
                "Osório",
                "Paim Filho",
                "Palmares do Sul",
                "Palmeira das Missões",
                "Palmitinho",
                "Panambi",
                "Pântano Grande",
                "Paraí",
                "Paraíso do Sul",
                "Pareci Novo",
                "Parobé",
                "Passa Sete",
                "Passo do Sobrado",
                "Passo Fundo",
                "Paulo Bento",
                "Paverama",
                "Pedras Altas",
                "Pedro Osório",
                "Pejuçara",
                "Pelotas",
                "Picada Café",
                "Pinhal",
                "Pinhal da Serra",
                "Pinhal Grande",
                "Pinheirinho do Vale",
                "Pinheiro Machado",
                "Pirapó",
                "Piratini",
                "Planalto",
                "Poço das Antas",
                "Pontão",
                "Ponte Preta",
                "Portão",
                "Porto Alegre",
                "Porto Lucena",
                "Porto Mauá",
                "Porto Vera Cruz",
                "Porto Xavier",
                "Pouso Novo",
                "Presidente Lucena",
                "Progresso",
                "Protásio Alves",
                "Putinga",
                "Quaraí",
                "Quatro Irmãos",
                "Quevedos",
                "Quinze de Novembro",
                "Redentora",
                "Relvado",
                "Restinga Seca",
                "Rio dos Índios",
                "Rio Grande",
                "Rio Pardo",
                "Riozinho",
                "Roca Sales",
                "Rodeio Bonito",
                "Rolador",
                "Rolante",
                "Ronda Alta",
                "Rondinha",
                "Roque Gonzales",
                "Rosário do Sul",
                "Sagrada Família",
                "Saldanha Marinho",
                "Salto do Jacuí",
                "Salvador das Missões",
                "Salvador do Sul",
                "Sananduva",
                "Santa Bárbara do Sul",
                "Santa Cecília do Sul",
                "Santa Clara do Sul",
                "Santa Cruz do Sul",
                "Santa Margarida do Sul",
                "Santa Maria",
                "Santa Maria do Herval",
                "Santa Rosa",
                "Santa Tereza",
                "Santa Vitória do Palmar",
                "Santana da Boa Vista",
                "Santana do Livramento",
                "Santiago",
                "Santo Ângelo",
                "Santo Antônio da Patrulha",
                "Santo Antônio das Missões",
                "Santo Antônio do Palma",
                "Santo Antônio do Planalto",
                "Santo Augusto",
                "Santo Cristo",
                "Santo Expedito do Sul",
                "São Borja",
                "São Domingos do Sul",
                "São Francisco de Assis",
                "São Francisco de Paula",
                "São Gabriel",
                "São Jerônimo",
                "São João da Urtiga",
                "São João do Polêsine",
                "São Jorge",
                "São José das Missões",
                "São José do Herval",
                "São José do Hortêncio",
                "São José do Inhacorá",
                "São José do Norte",
                "São José do Ouro",
                "São José do Sul",
                "São José dos Ausentes",
                "São Leopoldo",
                "São Lourenço do Sul",
                "São Luiz Gonzaga",
                "São Marcos",
                "São Martinho",
                "São Martinho da Serra",
                "São Miguel das Missões",
                "São Nicolau",
                "São Paulo das Missões",
                "São Pedro da Serra",
                "São Pedro das Missões",
                "São Pedro do Butiá",
                "São Pedro do Sul",
                "São Sebastião do Caí",
                "São Sepé",
                "São Valentim",
                "São Valentim do Sul",
                "São Valério do Sul",
                "São Vendelino",
                "São Vicente do Sul",
                "Sapiranga",
                "Sapucaia do Sul",
                "Sarandi",
                "Seberi",
                "Sede Nova",
                "Segredo",
                "Selbach",
                "Senador Salgado Filho",
                "Sentinela do Sul",
                "Serafina Corrêa",
                "Sério",
                "Sertão",
                "Sertão Santana",
                "Sete de Setembro",
                "Severiano de Almeida",
                "Silveira Martins",
                "Sinimbu",
                "Sobradinho",
                "Soledade",
                "Tabaí",
                "Tapejara",
                "Tapera",
                "Tapes",
                "Taquara",
                "Taquari",
                "Taquaruçu do Sul",
                "Tavares",
                "Tenente Portela",
                "Terra de Areia",
                "Teutônia",
                "Tio Hugo",
                "Tiradentes do Sul",
                "Toropi",
                "Torres",
                "Tramandaí",
                "Travesseiro",
                "Três Arroios",
                "Três Cachoeiras",
                "Três Coroas",
                "Três de Maio",
                "Três Forquilhas",
                "Três Palmeiras",
                "Três Passos",
                "Trindade do Sul",
                "Triunfo",
                "Tucunduva",
                "Tunas",
                "Tupanci do Sul",
                "Tupanciretã",
                "Tupandi",
                "Tuparendi",
                "Turuçu",
                "Ubiretama",
                "União da Serra",
                "Unistalda",
                "Uruguaiana",
                "Vacaria",
                "Vale do Sol",
                "Vale Real",
                "Vale Verde",
                "Vanini",
                "Venâncio Aires",
                "Vera Cruz",
                "Veranópolis",
                "Vespasiano Correa",
                "Viadutos",
                "Viamão",
                "Vicente Dutra",
                "Victor Graeff",
                "Vila Flores",
                "Vila Lângaro",
                "Vila Maria",
                "Vila Nova do Sul",
                "Vista Alegre",
                "Vista Alegre do Prata",
                "Vista Gaúcha",
                "Vitória das Missões",
                "Westfália",
                "Xangri-lá"
        };

    }
}