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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import java.util.Timer;
import java.util.TimerTask;

public class EditarPerfilFragment extends Fragment {
    private int id_usuario;
    private Usuario usuario;
    private DrawerLayout drawer_layout;

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
        et_editar_nome.setText(usuario.getNome());
        et_editar_email.setText(usuario.getEmail());
        et_editar_telefone.setText(usuario.getTelefone());
        et_editar_senha.setText(usuario.getSenha());

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
                String nome, email, telefone, senha;
                nome = et_editar_nome.getText().toString().trim();
                email = et_editar_email.getText().toString().trim();
                telefone = et_editar_telefone.getText().toString().trim();
                senha = et_editar_senha.getText().toString().trim();

                atualizarDados(nome, email, telefone, senha);
            }
        });

        bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario(id_usuario);
            }
        });
    }

    private void atualizarDados(final String nome, final String email, final String telefone, final String senha) {
        if(usuario != null){
            String nome_banco = usuario.getNome();
            String email_banco = usuario.getEmail();
            String telefone_banco = usuario.getTelefone();
            String senha_banco = usuario.getSenha();

            //Verifica se os novos dados são iguais aos anteriores.
            if(nome_banco.equals(nome) && email_banco.equals(email) && telefone_banco.equals(telefone) && senha_banco.equals(senha)){
                Toast.makeText(getActivity(), "Os novos dados devem ser diferentes dos existentes!", Toast.LENGTH_SHORT).show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("ATENÇÃO");
                builder.setMessage( "Tem certeza que deseja continuar?" );
                builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BancoDeDados.usuarioDAO.updateUsuario(id_usuario, nome, email, telefone, senha);
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
     * Método utilizado para fechar o menu quando um novo fragment é exibido.
     */
    public void onPause() {
        ((IndexActivity)getActivity()).fecharMenu(drawer_layout);
        super.onPause();
    }
}