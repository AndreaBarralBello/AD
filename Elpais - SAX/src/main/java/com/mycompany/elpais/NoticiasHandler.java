/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elpais;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Andrea Barral
 */
public class NoticiasHandler extends DefaultHandler {

    boolean bTitle = false;
    boolean bcterms = false;
    boolean bItem = false;
    boolean bFoto = false;
    String tituloNoticia;
    String contenidoNoticia;
    String urlFoto;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        //cuando Item es true
        if (qName.equalsIgnoreCase("item")) {
            bItem = true;
        }
        //pq hay dos title, por eso confirmaos que bItem sea true también
        if (qName.equalsIgnoreCase("title") && bItem) {
            bTitle = true;
        }
        
        if(qName.equalsIgnoreCase("dcterms:alternative")){
            bcterms = true;
        
        }
        if (qName.equalsIgnoreCase("media:content") && bItem){
            urlFoto = attributes.getValue("url");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            //cuando termina el elemento
            Variables.listaNoticias.add (new Noticia (tituloNoticia, urlFoto, contenidoNoticia));
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bTitle) {
            //lo guardamos en una variable, pq no lo vamos a imprimir por consola
            //directamente
            tituloNoticia = new String(ch, start, length);
            //como termino de leerlo, lo vuelvo a poner a false
            System.out.println(tituloNoticia);
           
            bTitle = false;

        } else if (bcterms) {
            //guardamos el contenido de la noticia en una variable
            contenidoNoticia = new String(ch, start, length);
            System.out.println(contenidoNoticia);
            
        }
    }

}


