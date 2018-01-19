package com.valework.yingul.logistic;


import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CotizarHandler extends DefaultHandler{
    ArrayList<CotizarEnvioResponse> cotizarEnvioResponse=new ArrayList();
    CotizarEnvioResponse cotizar;
    private StringBuilder buffer=new StringBuilder();

    public ArrayList<CotizarEnvioResponse> getCotizarEnvioResponse() {
        return cotizarEnvioResponse;
    }
    
    

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
       buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       switch(qName){
            case "CategoriaDistancia":
                cotizar.setCategoriaDistancia(buffer.toString());
                break;
            case "CategoriaDistanciaId":
              cotizar.setCategoriaDistanciaId(buffer.toString());
                break;
            case "CategoriaPeso":
                cotizar.setCategoriaPeso(buffer.toString());
                break;
            case "CategoriaPesoId":
                cotizar.setCategoriaPesoId(buffer.toString());
                break;
            case "PesoAforado":
                cotizar.setPesoAforado(buffer.toString());
                break;
            case "Tarifa":
                cotizar.setTarifa(buffer.toString());

       }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         switch(qName){
             case "CotizarEnvioResult":
                 cotizar=new CotizarEnvioResponse();
                 cotizarEnvioResponse.add(cotizar);
                     break;
            case "CategoriaDistancia":
                buffer.delete(0, buffer.length());
                break;
            case "CategoriaDistanciaId":
                buffer.delete(0, buffer.length());
                break;
            case "CategoriaPeso":
                buffer.delete(0, buffer.length());
                break;
            case "CategoriaPesoId":
                buffer.delete(0, buffer.length());
                break;
            case "PesoAforado":
                buffer.delete(0, buffer.length());
                break;
            case "Tarifa":
                buffer.delete(0, buffer.length());
                break;

                
        }
    }
    
    
}
