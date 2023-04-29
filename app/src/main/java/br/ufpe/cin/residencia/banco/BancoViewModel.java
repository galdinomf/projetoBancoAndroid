package br.ufpe.cin.residencia.banco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;
import br.ufpe.cin.residencia.banco.conta.ContaViewModel;

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
        new Thread(() -> {
            Conta contaOrigem = repository.buscarPeloNumero(numeroContaOrigem);
            Conta contaDestino = repository.buscarPeloNumero(numeroContaDestino);
            contaOrigem.transferir(contaDestino, valor);
            repository.atualizar(contaOrigem);
            repository.atualizar(contaDestino);
        }).start();
    }

    void creditar(String numeroConta, double valor) {
        new Thread(() -> {
            Conta conta = repository.buscarPeloNumero(numeroConta);
            conta.creditar(valor);
            repository.atualizar(conta);
        }).start();
    }

    void debitar(String numeroConta, double valor) {
        new Thread(() -> {
            Conta conta = repository.buscarPeloNumero(numeroConta);
            conta.debitar(valor);
            repository.atualizar(conta);
        }).start();
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
    public LiveData<Double> getSaldoTotal() {
        MutableLiveData<Double> saldoTotal = new MutableLiveData<>();
        new Thread(() -> saldoTotal.postValue(repository.saldoTotal())).start();
        return saldoTotal;
    }

}
