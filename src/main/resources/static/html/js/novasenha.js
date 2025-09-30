function alterarSenha(event) {
  event.preventDefault();

  const usuario = document.getElementById("usuario").value;
  const matricula = document.getElementById("matricula").value;
  const senha = document.getElementById("confirmarSenha").value;
  const novasenha = document.getElementById("novaSenha").value;

  console.log("Dados para alteração de senha:", { usuario, matricula, senha, novasenha });

  fetch("http://localhost:8080/alterar-senha", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ usuario, matricula, senha, novaSenha: novasenha })
  })
  .then(res => res.json())
  .then(data => {
    if (data.atualizou) {
        alert("Senha alterada com sucesso!");
        console.log("Sucesso! \n Resposta do servidor:", data);
    } else {
        alert("Erro: usuário, matrícula ou senha incorretos.");
        console.error("Falha na alteração de senha. \n Resposta do servidor:", data);
    }
  })
  .catch(err => console.error("Erro na requisição:", err));

  if (data.atualizou) {
  }
}
