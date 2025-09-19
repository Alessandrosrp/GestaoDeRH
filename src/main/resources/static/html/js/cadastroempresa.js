
function onSalvar(event) {

    if (event) {
        event.preventDefault();
    }


    const nome = document.getElementById("nomeEmpresa").value;
    const cnpj = document.getElementById("cnpj").value;
    const cep = document.getElementById("cep").value;
    const endereco = document.getElementById("endereco").value;
    const cidade = document.getElementById("cidade").value;
    const bairro = document.getElementById("bairro").value;
    const rh = document.getElementById("possuiRh").value;
    const ramo = document.getElementById("ramoAtividade").value;
    const email = document.getElementById("email").value;
    const filial = document.getElementById("filial").value;
    const estagiario = document.getElementById("estagiarios").value;
    const funcionarios = document.getElementById("funcionarios").value;
    const inscricao = document.getElementById("inscricao").value;
    const responsavel = document.getElementById("responsavel").value;
    const telefones = document.getElementById("telefones").value;
    const como = document.getElementById("comoconheceu").value;

    console.log("DEBUG (FRONTEND): Coletando dados para cadastro de empresa...");

    
    const dadosEmpresa = {
        nome,
        cnpj,
        cep,
        endereco,
        cidade,
        bairro,
        rh,
        ramo,
        email,
        filial,
        estagiario,
        funcionarios,
        inscricao,
        responsavel,
        telefones,
        como
    };

    console.log("DEBUG (FRONTEND): Dados a serem enviados:", dadosEmpresa);

    
    fetch("http://localhost:8080/cadastrar-empresa", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dadosEmpresa)
    })
    .then(response => {
        
        if (!response.ok) {
            
            return response.json().then(errorData => {
                throw new Error(errorData.mensagem || "Erro desconhecido");
            });
        }
        return response.json();
    })
    .then(data => {
        console.log("DEBUG (BACKEND RESPONSE):", data);
        
       
        if (data.sucesso) {
            alert(data.mensagem);
            
        
            
        } else {
           
            alert("Erro ao cadastrar empresa: " + data.mensagem);
        }
    })
    .catch(error => {
        console.error("Erro na requisição:", error);
        alert("Ocorreu um erro ao tentar salvar a empresa. Por favor, tente novamente.");
    });
}