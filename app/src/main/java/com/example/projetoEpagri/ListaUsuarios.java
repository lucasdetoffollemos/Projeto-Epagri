package com.example.projetoEpagri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios extends AppCompatActivity {


    private ListView listaUsuarios;
    private UsuarioDAO daoUser;
    private List<Usuario> usuarios;
    //private  List<Usuario> usuariosFiltrados = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        listaUsuarios = findViewById(R.id.lista_usuarios);
        daoUser = new UsuarioDAO(this);
        usuarios = daoUser.listarTodosUsuarios();
        //Apenas usuarios consultados
        //usuariosFiltrados.addAll(usuarios);

        /**
         * Adaptador de listas, adcionando a lista de usuarios dentro da listview do lista_usuarios.xml
         */
        ArrayAdapter<Usuario> adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usuarios);
        listaUsuarios.setAdapter(adaptador);
    }
}
