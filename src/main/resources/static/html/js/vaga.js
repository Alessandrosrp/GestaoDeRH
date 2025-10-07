console.log("✅ Script vaga.js carregado com sucesso!");

function registrar() {
  const vaga = {
    empresa: document.getElementById('empresa_txt').value,
    contato: document.getElementById('contato_txt').value,
    tipo: document.getElementById('vaga_comboBox').value,
    curso: document.getElementById('curso_comboBox').value,
    nivel: document.getElementById('nivel_comboBox').value,
    descricao: document.getElementById('descricao_txt').value,
    foto: ""
  };

  if (!vaga.empresa || !vaga.contato || !vaga.tipo || !vaga.curso || !vaga.nivel || !vaga.descricao) {
    alert("⚠️ Preencha todos os campos antes de registrar!");
    return;
  }

  fetch("http://localhost:8080/api/vagas", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(vaga)
  })
  .then(response => {
    if (!response.ok) throw new Error("Erro ao registrar vaga");
    return response.json();
  })
  .then(data => {
    alert("✅ Vaga registrada com sucesso!");
    console.log("Vaga criada:", data);
    document.getElementById("formVaga").reset();
  })
  .catch(error => {
    console.error("❌ Erro:", error);
    alert("Falha ao registrar a vaga.");
  });
}

function sair() {
  window.location.href = "telainicial2.html";
}
