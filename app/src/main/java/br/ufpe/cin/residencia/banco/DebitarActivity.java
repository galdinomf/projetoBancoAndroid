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
public class DebitarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
     boolean contaExiste = false;
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

        valorOperacao.setHint(valorOperacao.getHint() + " " + getString(R.string.hint_valorOperacao_debitar));
        tipoOperacao.setText(R.string.txt_tipoOperacao_debitar);
        btnOperacao.setText(R.string.btn_Operacao_debitar);

        viewModel.contas.observe(this, contas -> {
            if (contas.get(0) == null){
                contaExiste = false;
                return ;
            }
            for (Conta conta : contas){
                if (conta.numero.equals(numeroContaOrigem.getText().toString())){
                    contaExiste = true;
                    return ;
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

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    if (numOrigem.length() != 3) {
                        Toast.makeText(this, R.string.tst_verificar_numero, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!contaExiste) {
                        Toast.makeText(this, "conta errada", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String valorDigitado = valorOperacao.getText().toString();
                    if (valorDigitado.length() == 0) {
                        Toast.makeText(this, R.string.tst_verificar_valor, Toast.LENGTH_LONG).show();
                        return;
                    }
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.debitar(numOrigem, valor);
                    finish();
                }
        );
    }
}