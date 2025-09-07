async function fazerCad(event) {
  event.preventDefault();

  const form = document.getElementById("form-curriculo");
  const formData = new FormData(form);

  try {
    const response = await fetch("http://localhost:3000/curriculo", {
      method: "POST",
      body: formData,
    });

    const result = await response.json();
    alert(result.mensagem);

    if (result.sucesso) {
      window.location.href = "/sucesso.html";
    }
  } catch (err) {
    console.error("Erro ao enviar formulário:", err);
    alert("Erro ao enviar currículo!");
  }
}
