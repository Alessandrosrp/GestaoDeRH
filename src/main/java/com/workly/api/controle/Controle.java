package com.workly.api.controle;

import java.util.List;


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


@RestController
public class Controle {

  
    @Autowired
    private Repositorio acao;

   
    @Autowired
    private Servico servico;

    @PostMapping("/api")
    public ResponseEntity<?> cadastrar(@RequestBody Pessoa obj) {
        return servico.cadastrar(obj);
    }

   
    @GetMapping("/api")
    public ResponseEntity<?> selecionar() {
        return servico.selecionar();
    }

    @GetMapping("/api/{codigo}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int codigo) {
        return servico.selecionarPeloCodigo(codigo);
    }

   
    @PutMapping("/api")
    public ResponseEntity<?> editar(@RequestBody Pessoa obj) {
        return servico.editar(obj);
    }

    
    @DeleteMapping("/api/{codigo}")
    public ResponseEntity<?> remover(@PathVariable int codigo) {

        return servico.remover(codigo);
    }

   
    @PostMapping("/cliente")
    public void cliente(@Valid @RequestBody Cliente obj) {

    }
    
   
    @GetMapping("/api/contador")
    public long contador() {
        return acao.count();
    }

  
    @GetMapping("/api/ordenarNomes")
    public List<Pessoa> ordenarNomes() {
        return acao.findByOrderByNome();
    }

    
    @GetMapping("/api/ordenarNomes2")
    public List<Pessoa> ordenarNomes2() {
        return acao.findByNomeOrderByIdade("Giani");
    }

    
    @GetMapping("/api/nomeContem")
    public List<Pessoa> nomeContem() {
        return acao.findByNomeContaining("g");
    }

    
    @GetMapping("/api/iniciaCom")
    public List<Pessoa> iniciaCom() {
        return acao.findByNomeStartsWith("m");
    }

 
    @GetMapping("/api/terminaCom")
    public List<Pessoa> terminaCom() {
        return acao.findByNomeStartsWith("i");
    }

    
    @GetMapping("/api/somaIdades")
    public int somaIdades() {
        return acao.somaIdades();
    }

    
    @GetMapping("/api/idadeMaiorIgual")
    public List<Pessoa> idadeMaiorIgual() {
        return acao.idadeMaiorIgual(30);
    }

   
    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @GetMapping("")
    public String mensagem() {
        return "Hello World!";
    }

   
    @GetMapping("/boasVindas")
    public String boasVindas() {
        return "Seja bem-vindo(a) ";
    }

   
    @GetMapping("/boasVindas/{nome}")
    public String boasVindas(@PathVariable String nome) {
        return "Seja bem-vindo(a) " + nome;
    }

   
    @PostMapping("/pessoa")
    public Pessoa pessoa(@RequestBody Pessoa p) {
        return p;
    }
}
