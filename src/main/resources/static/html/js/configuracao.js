// ===== Variáveis globais =====
let tipoBackend; // será definido automaticamente conforme o tipo de usuário (admin ou candidato)
let usuarioInput; 
let labelUsuario;
let labelDescricao;

// ===== Inicialização da tela =====
function inicializarConfig(tipo) {
    console.log("Ação: Inicializar tela para:", tipo);

    /**
     * =====================================================
     *  DEFINIÇÃO AUTOMÁTICA DO BANCO DE DADOS A SER USADO
     * =====================================================
     * Aqui ocorre a separação entre os dois "bancos":
     * 
     * - Se o tipo recebido for "admin" → o backend usado será o de VAGAS.
     *   (ou seja, ele acessa as APIs: /api/vagas/... e altera empresas/vagas)
     * 
     * - Se o tipo recebido for "candidato" → o backend usado será o de CURRÍCULOS.
     *   (ou seja, ele acessa as APIs: /api/curriculos/... e altera dados de candidatos)
     * 
     * Essa simples linha é o que faz a distinção:
     */
    tipoBackend = tipo === "admin" ? "vagas" : "curriculos";
    console.log("Backend definido:", tipoBackend);

    // Captura elementos da tela
    labelUsuario = document.querySelector("label[for='usuario_txt']");
    labelDescricao = document.querySelector("label[for='descricao_txt']");
    usuarioInput = document.getElementById("usuario_txt");

    // Ajusta os rótulos e placeholders dependendo do tipo de usuário
    if (tipo === "admin") {
        if (labelUsuario) labelUsuario.innerText = "Empresa";
        if (labelDescricao) labelDescricao.innerText = "Sobre a vaga";
        if (usuarioInput) usuarioInput.placeholder = "Nome da empresa";
    } else {
        if (labelUsuario) labelUsuario.innerText = "Candidato";
        if (labelDescricao) labelDescricao.innerText = "Sobre você";
        if (usuarioInput) usuarioInput.placeholder = "Candidato";
    }

    // ===== Função de busca =====
    window.btn_buscar2 = async function () {
        console.log("Ação: Buscar registro");

        if (!usuarioInput) {
            console.error("Erro: usuarioInput não definido!");
            return;
        }

        const inputValor = usuarioInput.value.trim();
        if (!inputValor) {
            alert("Digite o valor para buscar!");
            return;
        }

        /**
         * Aqui o código usa o tipoBackend definido acima
         * para escolher qual API será chamada:
         * 
         * - Se for "curriculos" → busca em /api/curriculos
         * - Se for "vagas" → busca em /api/vagas
         */
        const url = tipoBackend === "curriculos"
            ? `http://localhost:8080/api/curriculos/buscar/${encodeURIComponent(inputValor)}`
            : `http://localhost:8080/api/vagas/buscar/${encodeURIComponent(inputValor)}`;

        try {
            const response = await fetch(url);
            const data = await response.json();
            console.log("DEBUG (RESPOSTA BACKEND BUSCAR):", data);

            if (!data || (Array.isArray(data) && data.length === 0)) {
                alert("Registro não encontrado!");
                return;
            }

            const registro = Array.isArray(data) ? data[0] : data;
            preencherCampos(registro);
        } catch (e) {
            console.error("Erro ao buscar:", e);
            alert("Erro de conexão com o servidor!");
        }
    };

    // ===== Função para salvar =====
    window.confirmar_configuracao = async function () {
        console.log("Ação: Confirmar configuração");

        const id = document.getElementById("id_txt")?.value;
        const usuario = usuarioInput?.value;
        const descricao = document.getElementById("descricao_txt")?.value;
        const contato = document.getElementById("contato_txt")?.value;
        const tipoSelecionado = document.getElementById("tipodevaga_combo")?.value;
        const curso = document.getElementById("curso_combo")?.value;
        const nivel = document.getElementById("nivel_comboBox")?.value;

        if (!usuario || !descricao || !contato || !nivel || tipoSelecionado === "Selecione" || curso === "Selecione") {
            alert("Preencha todos os campos obrigatórios!");
            return;
        }

        // Monta o corpo da requisição de acordo com o tipo de backend
        const dados = tipoBackend === "vagas"
            ? { id, empresa: usuario, descricao, contato, tipo: tipoSelecionado, curso, nivel }
            : { id, usuario, descricao, contato, tipo: tipoSelecionado, curso, nivel };

        /**
         * Aqui novamente a escolha do endpoint depende do tipoBackend:
         * 
         * - Admin → POST em /api/vagas/salvar
         * - Usuário → POST em /api/curriculos/salvar
         */
        const url = tipoBackend === "curriculos"
            ? "http://localhost:8080/api/curriculos/salvar"
            : "http://localhost:8080/api/vagas/salvar";

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados),
            });
            const data = await response.json();
            console.log("DEBUG (RESPOSTA BACKEND CONFIG):", data);
            alert(data.mensagem);
        } catch (e) {
            console.error("Erro ao salvar:", e);
            alert("Erro de conexão com o servidor!");
        }
    };

    // ===== Função para excluir =====
    window.excluir_configuracao = async function () {
        console.log("Ação: Excluir registro");

        const id = document.getElementById("id_txt")?.value;
        if (!id) {
            alert("Digite o ID para excluir!");
            return;
        }

        if (!confirm(`Tem certeza que deseja excluir o registro de ID "${id}"?`)) return;

        /**
         * Escolha dinâmica do endpoint de exclusão:
         * - Admin → /api/vagas/excluir/{id}
         * - Usuário → /api/curriculos/excluir/{id}
         */
        const url = tipoBackend === "curriculos"
            ? `http://localhost:8080/api/curriculos/excluir/${encodeURIComponent(id)}`
            : `http://localhost:8080/api/vagas/excluir/${encodeURIComponent(id)}`;

        try {
            const response = await fetch(url, { method: "DELETE" });
            const data = await response.json();
            console.log("DEBUG (RESPOSTA BACKEND EXCLUIR):", data);
            alert(data.mensagem);

            if (data.sucesso) limparCampos();
        } catch (e) {
            console.error("Erro ao excluir:", e);
            alert("Erro de conexão com o servidor!");
        }
    };

    // ===== Função para sair =====
    window.funcao_sair = function () {
        console.log("Ação: Sair da tela");
        window.location.href = "telainicial.html";
    };
}

// ===== Funções auxiliares =====
function preencherCampos(data) {
    console.log("Ação: Preencher campos com dados:", data);
    const idInput = document.getElementById("id_txt");
    if (idInput && data.id !== undefined) idInput.value = data.id;

    if (usuarioInput) usuarioInput.value = data.usuario || data.empresa || "";
    const descInput = document.getElementById("descricao_txt");
    if (descInput) descInput.value = data.descricao || "";

    const contatoInput = document.getElementById("contato_txt");
    if (contatoInput) contatoInput.value = data.contato || "";

    const tipoCombo = document.getElementById("tipodevaga_combo");
    if (tipoCombo) {
        const tipoMap = { "Estagio": "Estágio", "Trabalho": "Trabalho" };
        tipoCombo.value = tipoMap[data.tipo] || "Selecione";
    }

    const cursoCombo = document.getElementById("curso_combo");
    if (cursoCombo) {
        const cursoExiste = Array.from(cursoCombo.options).some(opt => opt.value === data.curso);
        cursoCombo.value = cursoExiste ? data.curso : "Selecione";
    }

    const nivelCombo = document.getElementById("nivel_comboBox");
    if (nivelCombo) {
        const nivelMap = {
            "1": "1º Semestre", "2": "2º Semestre", "3": "3º Semestre",
            "4": "4º Semestre", "5": "5º Semestre", "6": "6º Semestre",
            "7": "7º Semestre", "8": "8º Semestre", "9": "9º Semestre",
            "10": "10º Semestre"
        };
        let valorNivel = data.nivel;
        if (nivelMap[valorNivel]) valorNivel = nivelMap[valorNivel];

        const existe = Array.from(nivelCombo.options).some(opt => opt.value === valorNivel);
        nivelCombo.value = existe ? valorNivel : "Selecione";
    }
}

function limparCampos() {
    console.log("Ação: Limpar campos");
    ["id_txt", "usuario_txt", "descricao_txt", "contato_txt", "tipodevaga_combo", "curso_combo", "nivel_comboBox"]
        .forEach(id => {
            const el = document.getElementById(id);
            if (el) el.tagName === "SELECT" ? el.value = "Selecione" : el.value = "";
        });
}
