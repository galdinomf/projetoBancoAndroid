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

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                v -> {
                    String numeroConta = campoNumero.getText().toString();
                    if (numeroConta.length() != 3) {
                        Toast.makeText(this, "O número da conta deve ter 3 dígitos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String nomeCliente = campoNome.getText().toString();
                    if (nomeCliente.length() < 5) {
                        Toast.makeText(this, "O nome deve ter pelo menos 5 caracteres", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String cpfCliente = campoCPF.getText().toString();
                    if (cpfCliente.length() != 11) {
                        Toast.makeText(this, "O cpf deve ter 11 números", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String saldoConta = campoSaldo.getText().toString();
                    if (saldoConta.length() == 0) {
                        Toast.makeText(this, "Campo saldo não pode ficar vazio.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                    viewModel.inserir(c);
                    finish();
                }
        );

    }
}