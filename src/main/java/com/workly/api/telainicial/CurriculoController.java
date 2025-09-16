package com.workly.api.telainicial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.workly.api.telainicial_vagas.curriculo;
import java.util.List;

@RestController
@RequestMapping("/api/curriculos") // plural para evitar conflito
@CrossOrigin(origins = "*") // permite acesso do frontend
public class CurriculoController {

    @Autowired
    private CurriculoRepository curriculoRepository;

    // 🔹 Listar todos os currículos
    @GetMapping
    public List<curriculo> listarCurriculos(@RequestParam(value = "filtro", required = false) String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return curriculoRepository.findAll();
        } else {
            // você pode criar um método no repository para filtrar por usuario, descricao, tipo, curso ou nivel
            return curriculoRepository.findByFiltro(filtro.toLowerCase());
        }
    }

    // 🔹 Buscar currículo pelo ID
    @GetMapping("/{id}")
    public curriculo buscarPorId(@PathVariable int id) {
        return curriculoRepository.findById(id).orElse(null);
    }

    // 🔹 Criar novo currículo
    @PostMapping
    public curriculo criarCurriculo(@RequestBody curriculo curriculo) {
        return curriculoRepository.save(curriculo);
    }

    // 🔹 Atualizar currículo existente
    @PutMapping("/{id}")
    public curriculo atualizarCurriculo(@PathVariable int id, @RequestBody curriculo curriculo) {
        curriculo.setId(id);
        return curriculoRepository.save(curriculo);
    }

    // 🔹 Deletar currículo
    @DeleteMapping("/{id}")
    public void deletarCurriculo(@PathVariable int id) {
        curriculoRepository.deleteById(id);
    }
}
