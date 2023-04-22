package br.ufpe.cin.residencia.banco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;
    private MutableLiveData<List<Conta>> _listaAtual = new MutableLiveData<>();
    public LiveData<List<Conta>> contas = _listaAtual;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        //TODO implementar transferência entre contas (lembrar de salvar no BD os objetos Conta modificados)
    }

    void creditar(String numeroConta, double valor) {
        //TODO implementar creditar em conta (lembrar de salvar no BD o objeto Conta modificado)
    }

    void debitar(String numeroConta, double valor) {
        //TODO implementar debitar em conta (lembrar de salvar no BD o objeto Conta modificado)
    }

    void buscarPeloNome(String nomeCliente) {
        new Thread(() -> {
            List<Conta> listaContas = repository.buscarPeloNome(nomeCliente);
            _listaAtual.postValue(listaContas);
        }).start();
    }

    void buscarPeloCPF(String cpfCliente) {
        new Thread(() -> {
            List<Conta> listaContas = repository.buscarPeloCPF(cpfCliente);
            _listaAtual.postValue(listaContas);
        }).start();
    }

    void buscarPeloNumero(String numeroConta) {
        new Thread(() -> {
            Conta conta = repository.buscarPeloNumero(numeroConta);
            List<Conta> listaContas = Arrays.asList(conta);
            _listaAtual.postValue(listaContas);
        }).start();
    }

}
