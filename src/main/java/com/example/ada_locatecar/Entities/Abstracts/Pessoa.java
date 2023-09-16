package com.example.ada_locatecar.Entities.Abstracts;

public abstract class Pessoa {
    private Long id;
    private String nome;

    private String tipoDocumento;
    private String documento;

    private String habilitacao;
    private String endereco;

    public Pessoa(String nome, String tipoDocumento, String documento, String habilitacao, String endereco){
        this.nome = nome;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.habilitacao = habilitacao;
        this.endereco = endereco;
    }

    public Pessoa() {
        
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return this.documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    public void setHabilitacao(String habilitacao) {
        this.habilitacao = habilitacao;
    }

    public Long getId() {
        return id;
    }
}
