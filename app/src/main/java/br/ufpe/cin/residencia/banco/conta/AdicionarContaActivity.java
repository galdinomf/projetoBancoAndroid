package br.ufpe.cin.residencia.banco.conta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        btnAtualizar.setText(R.string.btn_add_conta_atualizar);
        btnRemover.setVisibility(View.GONE);

        campoNumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewModel.buscarPeloNumero(campoNumero.getText().toString());
                }
            }
        });

        btnAtualizar.setOnClickListener(
                v -> {
                    String numeroConta = campoNumero.getText().toString();
                    if (numeroConta.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero, Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    viewModel.contaAtual.observe(this, conta -> {
//                        if (conta != null){
//                            Toast.makeText(this, "Conta já existente.", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    });
                    if (viewModel.contaAtual.getValue() != null) {
                        Toast.makeText(this, R.string.tst_contaJaExistente, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String nomeCliente = campoNome.getText().toString();
                    if (nomeCliente.length() < 5) {
                        Toast.makeText(this, R.string.tst_verificar_nome, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String cpfCliente = campoCPF.getText().toString();
                    if (cpfCliente.length() != 11) {
                        Toast.makeText(this, R.string.tst_verificar_cpf, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String saldoConta = campoSaldo.getText().toString();
                    if (saldoConta.length() == 0) {
                        Toast.makeText(this, R.string.tst_verificar_saldo, Toast.LENGTH_LONG).show();
                        return;
                    }
                    Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                    viewModel.inserir(c);
                    finish();
                }
        );

    }
}