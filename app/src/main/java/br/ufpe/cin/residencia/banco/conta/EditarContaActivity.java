package br.ufpe.cin.residencia.banco.conta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
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
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);

        viewModel.buscarPeloNumero(numeroConta);

        viewModel.contaAtual.observe(this, conta -> {
            if (conta != null){
                campoNumero.setText(conta.numero);
                campoNome.setText(conta.nomeCliente);
                campoCPF.setText(conta.cpfCliente);
                campoSaldo.setText(String.valueOf(conta.saldo));
            }
        });

        btnAtualizar.setText(R.string.btn_editarConta_atualizar_editar);
        btnAtualizar.setOnClickListener(
                v -> {
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

        btnRemover.setOnClickListener(v -> {
            viewModel.remover(viewModel.contaAtual.getValue());
            finish();
        });
    }
}