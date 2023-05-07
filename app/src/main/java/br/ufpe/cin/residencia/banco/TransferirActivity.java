package br.ufpe.cin.residencia.banco;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.residencia.banco.conta.Conta;

//Ver anotações TODO no código
public class TransferirActivity extends AppCompatActivity {

    BancoViewModel viewModel;
    boolean contaOrigemExite = false;
    boolean contaDestinoExite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        valorOperacao.setHint(valorOperacao.getHint() + " " + getString(R.string.hint_valorOperacao));
        tipoOperacao.setText(R.string.txt_tipoOperacao);
        btnOperacao.setText(R.string.btn_Operacao);

        viewModel.contas.observe(this, contas -> {
            if (contas.get(0) == null)
                return ;
            for (Conta conta: contas){
                if (conta.numero.equals(numeroContaOrigem.getText().toString())){
                    numeroContaOrigem.setEnabled(false);
                    contaOrigemExite = true;
                }
                if (conta.numero.equals(numeroContaDestino.getText().toString())){
                    numeroContaDestino.setEnabled(false);
                    contaDestinoExite = true;
                }
            }
        });

        numeroContaOrigem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewModel.buscarPeloNumero(numeroContaOrigem.getText().toString());
                }
            }
        });

        numeroContaDestino.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewModel.buscarPeloNumero(numeroContaDestino.getText().toString());
                }
            }
        });

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    String numDestino = numeroContaDestino.getText().toString();
                    if (numOrigem.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero_origem, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!contaOrigemExite) {
                        Toast.makeText(this, R.string.tst_conta_origem_errada, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (numDestino.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero_destino, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!contaDestinoExite) {
                        Toast.makeText(this, R.string.tst_conta_destino_errada, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String valorDigitado = valorOperacao.getText().toString();
                    if (valorDigitado.length() == 0) {
                        Toast.makeText(this, R.string.tst_verificar_valor, Toast.LENGTH_LONG).show();
                        return;
                    }
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.transferir(numOrigem, numDestino, valor);
                    finish();
                }
        );

    }
}