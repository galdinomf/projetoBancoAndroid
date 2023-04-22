package br.ufpe.cin.residencia.banco;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.ufpe.cin.residencia.banco.BancoViewModel;
import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

//Ver anotações TODO no código
public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        btnPesquisar.setOnClickListener(
                v -> {
                    RadioButton rdBtnNome = findViewById(R.id.peloNomeCliente);
                    RadioButton rdBtnCPF = findViewById(R.id.peloCPFcliente);
                    RadioButton rdBtnNumero = findViewById(R.id.peloNumeroConta);

                    String oQueFoiDigitado = aPesquisar.getText().toString();
                    RadioButton rdBtnSelecionado = findViewById(
                            tipoPesquisa.getCheckedRadioButtonId()
                    );
                    if (rdBtnSelecionado == rdBtnNome){
                        viewModel.buscarPeloNome(oQueFoiDigitado);
                    }
                    if (rdBtnSelecionado == rdBtnCPF){
                        viewModel.buscarPeloCPF(oQueFoiDigitado);
                    }
                    if (rdBtnSelecionado == rdBtnNumero){
                        viewModel.buscarPeloNumero(oQueFoiDigitado);
                    }

                    viewModel.contas.observe(this, contas -> {
                        adapter.submitList(contas);
                    });
                }
        );

        //TODO atualizar o RecyclerView com resultados da busca na medida que encontrar


    }
}