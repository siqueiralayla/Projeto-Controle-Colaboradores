package com.layla.colaboradores.service;

import com.layla.colaboradores.entity.Departamento;
import com.layla.colaboradores.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;


    public void salvar(Departamento departamento) {
        departamentoRepository.save(departamento);
    }


    public void editar(Departamento departamento) {
        departamentoRepository.save(departamento); // save serve tanto para criar quanto para editar
    }


    public void excluir(Long id) {
        departamentoRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Departamento> buscarTodos() {
        return departamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean departamentoTemCargos(Long id) {
        Departamento departamento = buscarPorId(id);
        return departamento != null && !departamento.getCargos().isEmpty();
    }
}
