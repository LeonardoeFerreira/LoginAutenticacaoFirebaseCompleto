package com.playxcodes.organizze.model;

public class Usuario {

    private String nome;
    private String email;
    private String senha;


    //criando um construtor vazio
    public Usuario(){

    }


    //getter and setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



}
