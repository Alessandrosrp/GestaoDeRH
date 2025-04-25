package com.workly.api.controle;

import java.util.List;

// Importações relacionadas ao Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.workly.api.modelo.Cliente;
import com.workly.api.modelo.Pessoa;
import com.workly.api.repositorio.Repositorio;
import com.workly.api.servico.Servico;

import jakarta.validation.Valid;

// Define que esta classe é um controlador REST
@RestController
public class Controle {

    // Injeção de dependência do Repositório
    @Autowired
    private Repositorio acao;

    // Injeção de dependência do Serviço
    @Autowired
    private Servico servico;

    // Endpoint para cadastrar uma Pessoa
    @PostMapping("/api")
    public ResponseEntity<?> cadastrar(@RequestBody Pessoa obj) {
        return servico.cadastrar(obj);
    }

    // Endpoint para selecionar todas as Pessoas
    @GetMapping("/api")
    public ResponseEntity<?> selecionar() {
        return servico.selecionar();
    }

    // Endpoint para selecionar uma Pessoa pelo código
    @GetMapping("/api/{codigo}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int codigo) {
        return servico.selecionarPeloCodigo(codigo);
    }

    // Endpoint para editar uma Pessoa
    @PutMapping("/api")
    public ResponseEntity<?> editar(@RequestBody Pessoa obj) {
        return servico.editar(obj);
    }

    // Endpoint para remover uma Pessoa pelo código
    @DeleteMapping("/api/{codigo}")
    public ResponseEntity<?> remover(@PathVariable int codigo) {

        return servico.remover(codigo);
    }

    // Endpoint para receber um Cliente
    @PostMapping("/cliente")
    public void cliente(@Valid @RequestBody Cliente obj) {

    }
    
    // Endpoint para contar o número de registros no repositório
    @GetMapping("/api/contador")
    public long contador() {
        return acao.count();
    }

    // Endpoint para ordenar as Pessoas pelo nome
    @GetMapping("/api/ordenarNomes")
    public List<Pessoa> ordenarNomes() {
        return acao.findByOrderByNome();
    }

    // Endpoint para buscar Pessoas com um nome específico e ordenar por idade
    @GetMapping("/api/ordenarNomes2")
    public List<Pessoa> ordenarNomes2() {
        return acao.findByNomeOrderByIdade("Giani");
    }

    // Endpoint para buscar Pessoas cujo nome contém uma substring específica
    @GetMapping("/api/nomeContem")
    public List<Pessoa> nomeContem() {
        return acao.findByNomeContaining("g");
    }

    // Endpoint para buscar Pessoas cujo nome começa com uma substring específica
    @GetMapping("/api/iniciaCom")
    public List<Pessoa> iniciaCom() {
        return acao.findByNomeStartsWith("m");
    }

    // Endpoint para buscar Pessoas cujo nome termina com uma substring específica
    @GetMapping("/api/terminaCom")
    public List<Pessoa> terminaCom() {
        return acao.findByNomeStartsWith("i");
    }

    // Endpoint para calcular a soma das idades das Pessoas
    @GetMapping("/api/somaIdades")
    public int somaIdades() {
        return acao.somaIdades();
    }

    // Endpoint para buscar Pessoas com idade maior ou igual a um valor específico
    @GetMapping("/api/idadeMaiorIgual")
    public List<Pessoa> idadeMaiorIgual() {
        return acao.idadeMaiorIgual(30);
    }

    // Endpoint para retornar um status HTTP 201
    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint para retornar uma mensagem simples
    @GetMapping("")
    public String mensagem() {
        return "Hello World!";
    }

    // Endpoint para retornar uma mensagem de boas-vindas genérica
    @GetMapping("/boasVindas")
    public String boasVindas() {
        return "Seja bem-vindo(a) ";
    }

    // Endpoint para retornar uma mensagem de boas-vindas personalizada
    @GetMapping("/boasVindas/{nome}")
    public String boasVindas(@PathVariable String nome) {
        return "Seja bem-vindo(a) " + nome;
    }

    // Endpoint para receber e retornar uma Pessoa
    @PostMapping("/pessoa")
    public Pessoa pessoa(@RequestBody Pessoa p) {
        return p;
    }
}
