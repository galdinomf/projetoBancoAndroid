package br.ufpe.cin.residencia.banco;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

//Ver anotações TODO no código
public class TransferirActivity extends AppCompatActivity {

    BancoViewModel viewModel;

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

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    String numDestino = numeroContaDestino.getText().toString();
                    if (numOrigem.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero_origem, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (numDestino.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero_destino, Toast.LENGTH_SHORT).show();
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