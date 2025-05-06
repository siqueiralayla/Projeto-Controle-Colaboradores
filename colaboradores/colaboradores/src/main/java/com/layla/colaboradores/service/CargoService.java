package com.layla.colaboradores.service;

import com.layla.colaboradores.entity.Cargo;
import com.layla.colaboradores.repository.CargoRepository;
import com.layla.colaboradores.util.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CargoService  {

    @Autowired
    private CargoRepository cargoRepository;

    public void salvar(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    public void editar(Cargo cargo) {
        cargoRepository.save(cargo); // save também serve para atualizar
    }

    public void excluir(Long id) {
        cargoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Cargo buscarPorId(Long id) {
        return cargoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Cargo> buscarTodos() {
        return cargoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean cargoTemFuncionarios(Long id) {
        return buscarPorId(id) != null && !buscarPorId(id).getFuncionarios().isEmpty();
    }

    @Transactional(readOnly = true)
    public PaginacaoUtil<Cargo> buscarPorPagina(int pagina, String direcao) {
        int tamanho = 5; // tamanho da página
        Sort sort = Sort.by(Sort.Direction.fromString(direcao), "nome");
        PageRequest pageRequest = PageRequest.of(pagina - 1, tamanho, sort); // corrigido para página 0-based

        Page<Cargo> page = cargoRepository.findAll(pageRequest);

        return new PaginacaoUtil<>(
                page.getSize(),
                page.getNumber() + 1,
                page.getTotalPages(),
                direcao,
                page.getContent()
        );
    }

}
