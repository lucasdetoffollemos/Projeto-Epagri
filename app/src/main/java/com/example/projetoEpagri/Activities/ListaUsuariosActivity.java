package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.Dao.UsuarioDAO;
import com.example.projetoEpagri.R;

import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {
    private ListView listaUsuarios;
    private UsuarioDAO daoUser;
    private List<Usuario> usuarios;
    //private  List<Usuario> usuariosFiltrados = new ArrayList<>();
    ArrayAdapter<Usuario> adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        inicializa();
        setListeners();

        listaUsuarios = findViewById(R.id.lista_usuarios);
        daoUser = new UsuarioDAO(this);
        usuarios = daoUser.listarTodosUsuarios();
        //Apenas usuarios consultados
        //usuariosFiltrados.addAll(usuarios);

        /**
         * Adaptador de listas, adcionando a lista de usuarios dentro da listview do lista_usuarios.xml
         */
        ArrayAdapter<Usuario> adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usuarios);

    }



    private void inicializa() {

        listaUsuarios = findViewById(R.id.lista_usuarios);
        daoUser = new UsuarioDAO(this);
        usuarios = daoUser.listarTodosUsuarios();
        //Apenas usuarios consultados
        //usuariosFiltrados.addAll(usuarios);

        /**
         * Adaptador de listas, adcionando a lista de usuarios dentro da listview do lista_usuarios.xml
         */
        adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usuarios);
    }

    private void setListeners() {
        listaUsuarios.setAdapter(adaptador);
    }
}
