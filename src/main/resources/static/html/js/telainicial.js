// telainicial.js

document.addEventListener("DOMContentLoaded", () => {
  carregarVagas();

  // botão de pesquisa
  document.getElementById("btn_pesquisa").addEventListener("click", () => {
    const termo = document.getElementById("pesquisa_txt").value.toLowerCase();
    carregarVagas(termo);
  });
});

// Função para buscar as vagas da API e preencher a tabela
function carregarVagas(filtro = "") {
  fetch("http://localhost:8080/api/vagas") // endpoint do Spring Boot
    .then(response => {
      if (!response.ok) {
        throw new Error("Erro ao buscar vagas");
      }
      return response.json();
    })
    .then(vagas => {
      const tabela = document.getElementById("vagasdisponiveis_table");
      tabela.innerHTML = "";

      // Aplica filtro se foi digitado algo na pesquisa
      const vagasFiltradas = vagas.filter(vaga =>
        vaga.empresa.toLowerCase().includes(filtro) ||
        vaga.descricao.toLowerCase().includes(filtro) ||
        vaga.tipo.toLowerCase().includes(filtro) ||
        vaga.curso.toLowerCase().includes(filtro) ||
        vaga.nivel.toLowerCase().includes(filtro)
      );

      if (vagasFiltradas.length === 0) {
        tabela.innerHTML = `
          <tr>
            <td colspan="7" style="text-align:center">Nenhuma vaga encontrada</td>
          </tr>
        `;
        return;
      }

      // Preenche tabela
      vagasFiltradas.forEach(vaga => {
        const row = `
          <tr>
            <td>${vaga.id}</td>
            <td>${vaga.empresa}</td>
            <td>${vaga.descricao}</td>
            <td>${vaga.contato}</td>
            <td>${vaga.tipo}</td>
            <td>${vaga.curso}</td>
            <td>${vaga.nivel}</td>
          </tr>
        `;
        tabela.innerHTML += row;
      });
    })
    .catch(error => {
      console.error("Erro ao carregar vagas:", error);
      document.getElementById("vagasdisponiveis_table").innerHTML = `
        <tr>
          <td colspan="7" style="text-align:center; color: red;">
            Erro ao carregar vagas. Verifique se a API está rodando.
          </td>
        </tr>
      `;
    });
}
