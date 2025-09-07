async function fazerCadastro(event) {
  event.preventDefault();

  const tipo = document.getElementById("tipo").value;
  const email = document.getElementById("email").value;
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirm-password").value;

  // Validação de senha
  if (password !== confirmPassword) {
    alert("As senhas não coincidem!");
    return;
  }

  const dados = {
    tipo,
    email,
    usuario: username,
    senha: password,
  };

  console.log("DEBUG (FRONT CADASTRO):", dados);

  try {
    const response = await fetch("http://localhost:8080/cadastro", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dados),
    });

    const data = await response.json();
    console.log("DEBUG (RESPOSTA BACKEND CADASTRO):", data);

    if (!data.sucesso && data.mensagem) {
      alert(data.mensagem);
    }

    if (data.sucesso && data.rota) {
      alert("Cadastro realizado com sucesso!");
      window.location.href = data.rota; // ex: login.html
    }
  } catch (e) {
    console.error("Erro de conexão:", e);
    alert("Erro de conexão com o servidor!");
  }
}
