package com.workly.api.servico;

// Importações relacionadas ao Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// Importações de modelos e repositório
import com.workly.api.modelo.Mensagem;
import com.workly.api.modelo.Pessoa;
import com.workly.api.repositorio.Repositorio;

// Define que esta classe é um serviço Spring
@Service
public class Servico {

    // Injeção de dependência da Mensagem
    @Autowired
    private Mensagem mensagem;

    // Injeção de dependência do Repositório
    @Autowired
    private Repositorio acao;

    // Método para cadastrar pessoas
    public ResponseEntity<?> cadastrar(Pessoa obj) {

        // Verifica se o nome está vazio
        if (obj.getNome().equals("")) {
            mensagem.setMensagem("O nome precisa ser preenchido.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        // Verifica se a idade é negativa
        else if (obj.getIdade() < 0) {
            mensagem.setMensagem("Informe uma idade válida.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        // Caso contrário, salva a pessoa
        else {
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    // Método para selecionar pessoas
    public ResponseEntity<?> selecionar() {
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    // Método para selecionar pessoas pelo código
    public ResponseEntity<?> selecionarPeloCodigo(int codigo) {

        // Verifica se o código não existe
        if (acao.countByCodigo(codigo) == 0) {
            mensagem.setMensagem("Não foi encontrada nenhuma pessoa.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        // Caso contrário, retorna a pessoa encontrada
        else {
            return new ResponseEntity<>(acao.findByCodigo(codigo), HttpStatus.OK);
        }
    }

    // Método para editar dados
    public ResponseEntity<?> editar(Pessoa obj) {

        // Verifica se o código não existe
        if (acao.countByCodigo(obj.getCodigo()) == 0) {
            mensagem.setMensagem("O código informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        // Verifica se o nome está vazio
        else if (obj.getNome().equals("")) {
            mensagem.setMensagem("É necessário informar um nome");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        // Verifica se a idade é negativa
        else if (obj.getIdade() < 0) {
            mensagem.setMensagem("Informe uma idade válida");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        // Caso contrário, salva as alterações
        else {
            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
        }
    }

    // Método para remover registros
    public ResponseEntity<?> remover(int codigo) {

        // Verifica se o código não existe
        if (acao.countByCodigo(codigo) == 0) {
            mensagem.setMensagem("O código informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        // Caso contrário, remove a pessoa
        else {
            Pessoa obj = acao.findByCodigo(codigo);
            acao.delete(obj);

            mensagem.setMensagem("Pessoa removida com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
}
