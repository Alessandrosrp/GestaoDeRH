package com.workly.api.telainicial_vagas;

import jakarta.persistence.*;

@Entity
@Table(name = "curriculo") // nome da tabela no banco
public class curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento no banco
    private int id;

    private String usuario;
    private String descricao;
    private String contato;
    private String tipo;
    private String curso;
    private String nivel;
    private String foto;

    // 🔹 Construtor vazio (obrigatório para o JPA)
    public curriculo() {
    }

    // 🔹 Construtor completo (opcional, útil para testes ou inicialização)
    public curriculo(int id, String usuario, String descricao, String contato, String tipo, String curso, String nivel, String foto) {
        this.id = id;
        this.usuario = usuario;
        this.descricao = descricao;
        this.contato = contato;
        this.tipo = tipo;
        this.curso = curso;
        this.nivel = nivel;
        this.foto = foto;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
