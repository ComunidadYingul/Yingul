/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valework.yingul.logistic;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Valework
 */
public class ImprimirConstanciaHandler extends DefaultHandler{
    private StringBuilder buffer=new StringBuilder();
    ArrayList<ImprimirConstanciaResponse>imprimirResponce=new ArrayList(); 
    ImprimirConstanciaResponse imprimir;

    public ArrayList<ImprimirConstanciaResponse> getImprimirResponce() {
        return imprimirResponce;
    }

    

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
         buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            case "PdfLinkFile":
                imprimir.setPdfLinkFile(buffer.toString());
                break;

       }
        
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                        switch(qName){
             case "ResultadoImprimirConstancia":
                   imprimir=new ImprimirConstanciaResponse();
                   imprimirResponce.add(imprimir);
                     break;
            case "PdfLinkFile":
                buffer.delete(0, buffer.length());
                break;

                
        }
        
    }
    
    
}
