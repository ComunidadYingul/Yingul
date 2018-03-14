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
public class EnvioHandler  extends DefaultHandler{
    
    ArrayList<EnvioResponce> envioResponse=new ArrayList();
    EnvioResponce enviar;
    private StringBuilder buffer=new StringBuilder();


    public ArrayList<EnvioResponce> getEnvioResponse() {
        return envioResponse;
    }    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);       
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            case "a:NumeroAndreani":
                enviar.setNumeroAndreani(buffer.toString());
                break;
        }        
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                switch(qName){
             case "GenerarEnvioConDatosDeImpresionYRemitenteResult":
                   enviar=new EnvioResponce();
                   envioResponse.add(enviar);
                     break;
            case "a:NumeroAndreani":
                buffer.delete(0, buffer.length());
                break;

                
        }
    }
    
    
    
}
