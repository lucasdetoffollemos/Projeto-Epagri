package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.Dao.UsuarioDAO;
import com.example.projetoEpagri.R;

import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {
    private ListView lv_usuarios;
    private List<Usuario> listaUsuarios;
    ArrayAdapter<Usuario> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        inicializa();
        setListeners();
    }

    private void inicializa() {
        this.lv_usuarios = findViewById(R.id.lista_usuarios);
        this.listaUsuarios = LoginActivity.bancoDeDados.usuarioDAO.getAllUsuarios();

        this.adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, this.listaUsuarios);
    }

    private void setListeners() {
        lv_usuarios.setAdapter(adaptador);
    }
}
