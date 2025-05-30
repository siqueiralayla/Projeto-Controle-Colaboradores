package com.layla.colaboradores.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@SuppressWarnings("serial")
@Entity
@Table(name = "FUNCIONARIOS")
public class Funcionario extends AbstractEntity<Long> {


    @NotBlank
    @Size(max = 255, min = 3)
    @Column(nullable = false, unique = true)
    private String nome;

    @NotNull
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal salario = BigDecimal.ZERO;

    @NotNull
    @PastOrPresent(message = "{PastOrPresent.funcionario.dataEntrada}")
    @DateTimeFormat(iso = ISO.DATE, pattern = "")
    @Column(name= "data_entrada", nullable = false, columnDefinition = "DATE")
    private LocalDate dataEntrada;

    @DateTimeFormat(iso = ISO.DATE)
    @Column(name = "data_saida", columnDefinition = "DATE")
    private LocalDate dataSaida;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id_fk")
    private Endereco endereco;

    @NotNull(message = "{NotNull.funcionario.cargo}")
    @ManyToOne
    @JoinColumn(name = "cargo_id_fk")
    private Cargo cargo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}