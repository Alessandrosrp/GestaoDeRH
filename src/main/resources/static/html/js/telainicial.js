// telainicial.js
document.addEventListener("DOMContentLoaded", () => {
  carregarVagas();

  // botão de pesquisa
  document.getElementById("btn_pesquisa").addEventListener("click", () => {
    const termo = document.getElementById("pesquisa_txt").value.toLowerCase();
    carregarVagas(termo);
  });

  // fechar ao clicar fora do card
  const overlay = document.getElementById("card-overlay");
  overlay.addEventListener("click", (e) => {
    if (e.target === overlay) fecharCard();
  });

  // fechar com ESC
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") fecharCard();
  });
});

// Função para buscar as vagas da API e preencher a tabela
function carregarVagas(filtro = "") {
  fetch("http://localhost:8080/api/vagas") // endpoint do Spring Boot
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao buscar vagas");
      }
      return response.json();
    })
    .then((vagas) => {
      const tabela = document.getElementById("vagasdisponiveis_table");
      tabela.innerHTML = "";

      // Aplica filtro se foi digitado algo na pesquisa
      const vagasFiltradas = vagas.filter(
        (vaga) =>
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

      // Preenche tabela com evento de duplo clique
      vagasFiltradas.forEach((vaga) => {
        const row = document.createElement("tr");

        row.innerHTML = `
          <td>${vaga.id}</td>
          <td>${vaga.empresa}</td>
          <td>${vaga.descricao}</td>
          <td>${vaga.contato}</td>
          <td>${vaga.tipo}</td>
          <td>${vaga.curso}</td>
          <td>${vaga.nivel}</td>
        `;

        // Evento de duplo clique para abrir card
        row.addEventListener("dblclick", () => {
          mostrarCard(vaga);
        });

        tabela.appendChild(row);
      });
    })
    .catch((error) => {
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

// Função para mostrar card com detalhes da vaga (overlay central)
function mostrarCard(vaga) {
  const overlay = document.getElementById("card-overlay");

  overlay.innerHTML = `
    <div class="vaga-card">
      <h5 class="vaga-card-title">${vaga.empresa}</h5>
      <p><strong>Descrição:</strong> ${vaga.descricao}</p>
      <p><strong>Contato:</strong> ${vaga.contato}</p>
      <p><strong>Tipo:</strong> ${vaga.tipo}</p>
      <p><strong>Curso:</strong> ${vaga.curso}</p>
      <p><strong>Nível:</strong> ${vaga.nivel}</p>
      <button class="btn-fechar" onclick="fecharCard()">Fechar</button>
    </div>
  `;

  overlay.style.display = "flex"; // mostra o overlay (CSS usa flex p/ centralizar)
}

// Função para fechar o card
function fecharCard() {
  const overlay = document.getElementById("card-overlay");
  overlay.style.display = "none";
  overlay.innerHTML = ""; // limpa conteúdo do overlay
}
