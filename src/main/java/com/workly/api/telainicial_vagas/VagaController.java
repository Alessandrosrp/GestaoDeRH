package com.workly.api.telainicial_vagas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.workly.api.curriculo.vagas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vagas")
@CrossOrigin(origins = "*") // Permite que o frontend HTML/JS acesse a API
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    // ✅ Listar todas as vagas
    @GetMapping
    public List<vagas> listarVagas() {
        return vagaRepository.findAll();
    }

    // ✅ Buscar uma vaga pelo ID
    @GetMapping("/{id}")
    public Optional<vagas> buscarPorId(@PathVariable int id) {
        return vagaRepository.findById(id);
    }

    // ✅ Criar uma nova vaga
    @PostMapping
    public vagas criarVaga(@RequestBody vagas vaga) {
        return vagaRepository.save(vaga);
    }

    // ✅ Atualizar uma vaga existente
    @PutMapping("/{id}")
    public vagas atualizarVaga(@PathVariable int id, @RequestBody vagas vaga) {
        vaga.setId(id); // garante que o ID seja mantido
        return vagaRepository.save(vaga);
    }

    // ✅ Deletar uma vaga
    @DeleteMapping("/{id}")
    public void deletarVaga(@PathVariable int id) {
        vagaRepository.deleteById(id);
    }
}
