package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

//Ver anotações TODO no código
public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;

    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    @WorkerThread
    public void inserir(Conta c) {
        dao.adicionar(c);
    }

    @WorkerThread
    public void atualizar(Conta c) {
        dao.atualizar(c);
    }

    @WorkerThread
    public void remover(Conta c) {
        dao.remover(c);
    }

    public Double saldoTotal(){ return dao.saldoTotal(); }

    @WorkerThread
    public List<Conta> buscarPeloNome(String nomeCliente) {
        return dao.buscarPeloNome(nomeCliente);
    }

    @WorkerThread
    public List<Conta> buscarPeloCPF(String cpfCliente) {
        return dao.buscarPeloCPF(cpfCliente);
    }

    @WorkerThread
    public Conta buscarPeloNumero(String numeroConta) {
        return dao.buscarPeloNumero(numeroConta);
    }
}
