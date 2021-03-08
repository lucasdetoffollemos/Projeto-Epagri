package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

public class EsqueceuSenhaActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1 ;
    private EditText et_email;
    private Button bt_modifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);
        inicializa();
        setListeners();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    private void inicializa() {
        //Esta linha de código faz com que o teclado nao seja habilitado quando o usuário entra nesta activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        et_email = findViewById(R.id.et_email);
        bt_modifica = findViewById(R.id.bt_modificar);
    }

    /**
     * Método utilizado para setar os listener dos botões.
     */
    private void setListeners() {
        bt_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                verificaEmail();
            }
        });
    }

    //Chamado no arquivo layout
    public void clicarVoltarRecuperaSenha(View v){
        finish();
    }

    /**
     *  Método para verificar se o email do usuário existe,
     *  se existir leva para outra página com o email e a senha atual, se não existir apenas apresenta um toast.
     */
    private void verificaEmail() {
        String email = et_email.getText().toString();
        Boolean usuarioCheck = BancoDeDados.usuarioDAO.verificaEmailUsuario(email);
        if(usuarioCheck){
            Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(email);
            vaiParaActivityModificaSenha(usuario.getNome(), usuario.getSenha());
        }
        else{
            Toast.makeText(EsqueceuSenhaActivity.this, "Usuário inexistente", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método responsável por inicializar a activity ModificaSenha.
     * @param nome
     * @param senha
     */
    private void vaiParaActivityModificaSenha(String nome, String senha) {
        Intent i = new Intent(EsqueceuSenhaActivity.this, ModificaSenhaActivity.class);
        i.putExtra("nome_usuario", nome);
        i.putExtra("senha_usuario", senha);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            finish();
        }
    }
}