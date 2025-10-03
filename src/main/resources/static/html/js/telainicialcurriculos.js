document.addEventListener("DOMContentLoaded", () => {
  carregarCurriculos();

  // botão de pesquisa
  document.getElementById("btn_pesquisa").addEventListener("click", () => {
    const termo = document.getElementById("pesquisa_txt").value.toLowerCase();
    carregarCurriculos(termo);
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


function carregarCurriculos(filtro = "") {
  
  const url = filtro 
    ? `http://localhost:8080/api/curriculos?filtro=${encodeURIComponent(filtro)}`
    : "http://localhost:8080/api/curriculos";

  fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao buscar currículos");
      }
      return response.json();
    })
    .then((curriculos) => {
      const tabela = document.getElementById("vagasdisponiveis_table");
      tabela.innerHTML = "";

      if (!curriculos || curriculos.length === 0) {
        tabela.innerHTML = `
          <tr>
            <td colspan="7" style="text-align:center">Nenhum currículo encontrado</td>
          </tr>
        `;
        return;
      }

      // Preenche tabela
      curriculos.forEach((c) => {
        const row = document.createElement("tr");

        row.innerHTML = `
          <td>${c.id}</td>
          <td>${c.usuario}</td>
          <td>${c.descricao}</td>
          <td>${c.contato}</td>
          <td>${c.tipo}</td>
          <td>${c.curso}</td>
          <td>${c.nivel}</td>
        `;

        // Evento de duplo clique → card detalhado
        row.addEventListener("dblclick", () => {
          mostrarCard(c.id); // agora passa apenas o ID
        });

        tabela.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Erro ao carregar currículos:", error);
      document.getElementById("vagasdisponiveis_table").innerHTML = `
        <tr>
          <td colspan="7" style="text-align:center; color: red;">
            Erro ao carregar currículos. Verifique se a API está rodando.
          </td>
        </tr>
      `;
    });
}

// Função para mostrar card com detalhes do currículo
function mostrarCard(idCurriculo) {
  fetch(`http://localhost:8080/api/curriculos/${idCurriculo}`)
    .then((response) => {
      if (!response.ok) throw new Error("Erro ao buscar currículo");
      return response.json();
    })
    .then((curriculo) => {
      const overlay = document.getElementById("card-overlay");
      overlay.innerHTML = `
        <div class="vaga-card">
          <h5 class="vaga-card-title">${curriculo.usuario}</h5>
          <p><strong>Descrição:</strong> ${curriculo.descricao}</p>
          <p><strong>Contato:</strong> ${curriculo.contato}</p>
          <p><strong>Tipo:</strong> ${curriculo.tipo}</p>
          <p><strong>Curso:</strong> ${curriculo.curso}</p>
          <p><strong>Nível:</strong> ${curriculo.nivel}</p>
          <button class="btn-fechar" onclick="fecharCard()">Fechar</button>
        </div>
      `;
      overlay.style.display = "flex";
    })
    .catch((error) => {
      console.error("Erro ao abrir currículo:", error);
      alert("Não foi possível abrir o currículo.");
    });
}

// Função para fechar o card
function fecharCard() {
  const overlay = document.getElementById("card-overlay");
  overlay.style.display = "none";
  overlay.innerHTML = "";
}
