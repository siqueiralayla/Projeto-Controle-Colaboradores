package com.layla.colaboradores.service;


import com.layla.colaboradores.entity.Funcionario;
import com.layla.colaboradores.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FuncionarioService{

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void salvar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    public void editar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }


    @Transactional(readOnly = true)
    public List<Funcionario> buscarPorNome(String nome) {
        return funcionarioRepository.findByNomeContaining(nome);
    }

    @Transactional(readOnly = true)
    public List<Funcionario> buscarPorCargo(Long id) {
        return funcionarioRepository.findByCargoId(id);
    }

    @Transactional(readOnly = true)
    public List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida) {
        if (entrada != null && saida != null) {
            return funcionarioRepository.findByDataEntradaBetween(entrada, saida);
        } else if (entrada != null) {
            return funcionarioRepository.findByDataEntrada(entrada);
        } else if (saida != null) {
            return funcionarioRepository.findByDataSaida(saida);
        } else {
            return List.of();
        }
    }
}