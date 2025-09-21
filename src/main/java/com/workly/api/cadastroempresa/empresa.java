package com.workly.api.cadastroempresa;

import jakarta.persistence.*;

@Entity
@Table(name = "empresacadastrada") // nome da tabela no banco
public class empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento no banco
    private int id;

    private String nome;
    private String cnpj;
    private String cep;
    private String endereco;
    private String cidade;
    private String bairro;
    private String rh;
    private String ramo;
    private String email;
    private String filial;
    private String estagiario;
    private String funcionarios;
    private String inscricao;
    private String responsavel;
    private String telefones;
    private String como;
    private String matricula;

    // 🔹 Construtor vazio (obrigatório para o JPA)
    public empresa() {
    }

    // 🔹 Construtor completo (opcional, útil para testes ou inicialização)
    public empresa(int id, String nome, String cnpj, String cep, String endereco, String cidade, String bairro, String rh, String ramo, String email, String filial, String estagiario, String funcionarios, String inscricao, String responsavel, String telefones, String como, String matricula) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.cep = cep;
        this.endereco = endereco;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rh = rh;
        this.ramo = ramo;
        this.email = email;
        this.filial = filial;
        this.estagiario = estagiario;
        this.funcionarios = funcionarios;
        this.inscricao = inscricao;
        this.responsavel = responsavel;
        this.telefones = telefones;
        this.como = como;
        this.matricula = matricula;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(String estagiario) {
        this.estagiario = estagiario;
    }

    public String getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(String funcionarios) {
        this.funcionarios = funcionarios;
    }
    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefones() {
        return telefones;
    }

    public void setTelefones(String telefones) {
        this.telefones = telefones;
    }

    public String getComo() {
        return como;
    }

    public void setComo(String como) {
        this.como = como;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
}
