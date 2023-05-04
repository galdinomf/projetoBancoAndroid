package br.ufpe.cin.residencia.banco;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

//Ver anotações TODO no código
public class CreditarActivity extends AppCompatActivity {
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
        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);
        valorOperacao.setHint(valorOperacao.getHint() + " " + getString(R.string.hint_valorOperacao_creditar));
        tipoOperacao.setText(R.string.txt_tipoOperacao_creditar);
        btnOperacao.setText(R.string.btn_Operacao_creditar);

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    if (numOrigem.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String valorDigitado = valorOperacao.getText().toString();
                    if (valorDigitado.length() == 0) {
                        Toast.makeText(this, R.string.tst_verificar_valor, Toast.LENGTH_LONG).show();
                        return;
                    }
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.creditar(numOrigem,valor);
                    finish();
                }
        );
    }
}