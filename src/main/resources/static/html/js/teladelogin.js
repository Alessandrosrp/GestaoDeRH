function fazerLogin(event) {
  // Sem isso n funciona por algum motivo q eu n entendi, mas faz com q o BootStrap n consiga dar os avisos dos campos tipo o do email ser obrigatório o @
  event.preventDefault(); 
  // A se trocar o submit do botao para button dai da pra remover isso dnv ns por que isso acontece

  const tipo = document.getElementById("tipo").value;
  const usuario = document.getElementById("usuario").value;
  const senha = document.getElementById("senha").value;
  
  console.log("DEBUG (FRONT):", { tipo, usuario, senha }); // 👈 MOSTRA NO CONSOLE DO NAVEGADOR
  
  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ tipo, usuario, senha })
  })
  .then(res => res.json())
  .then(data => {
    console.log("DEBUG (RESPOSTA BACKEND):", data);

    // Só mostra o alert se o login falhar
    if ((tipo && usuario && senha) && !data.sucesso && data.mensagem) {
        alert(data.mensagem);
    }

    // Se login deu certo, redireciona para a rota informada pelo backend
    if (data.sucesso && data.rota) {
        window.location.href = data.rota;
    }
})
  .catch(err => {
    console.error("Erro na requisição:", err);
  });
  
}
