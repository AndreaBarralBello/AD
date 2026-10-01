/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elpais;

/**
 *
 * @author Andrea Barral
 */
public class Noticia {
    String titulo;
    String urlFoto;
    String contenido;

    public Noticia() {
    }

    public Noticia(String titulo, String urlFoto, String contenido) {
        this.titulo = titulo;
        this.urlFoto = urlFoto;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Noticia{" + "titulo=" + titulo + ", urlFoto=" + urlFoto + ", contenido=" + contenido + '}';
    }
    
    
    
    
}
