package com.workly.api.telainicial_vagas;

public class curriculo {
    private int id;
    private String usuario;
    private String descricao;
    private String contato;
    private String tipo;
    private String curso;
    private String nivel;
    private String foto; // Adicionei o campo foto

    public curriculo(int id, String usuario, String descricao, String contato, String tipo, String curso, String nivel, String foto) {
        this.id = id;
        this.usuario = usuario;
        this.descricao = descricao;
        this.contato = contato;
        this.tipo = tipo;
        this.curso = curso;
        this.nivel = nivel;
        this.foto = foto; // Adicionei o parâmetro foto
    }

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

    public String getFoto() { // Adicionei o método getFoto
        return foto;
    }

    public void setFoto(String foto) { // Adicionei o método setFoto
        this.foto = foto;
    }
}