
package com.valework.yingul.logistic;

import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SucursalHandler extends DefaultHandler{

    ArrayList<ResultadoConsultarSucursales> resultadoSucursales=new ArrayList();
    private ResultadoConsultarSucursales consultarSucursales;
    //private ResultadoConsultarSucursales resultadoConsultarSucursales;
    private StringBuilder buffer=new StringBuilder();

    public ArrayList<ResultadoConsultarSucursales> getResultadoSucursales() {
        return resultadoSucursales;
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
               switch(qName){
            case "Descripcion":
                consultarSucursales.setDescripcion(buffer.toString());
                break;
            case "Direccion":
            	System.out.println("setDireccion"+buffer.toString());
              consultarSucursales.setDireccion((buffer.toString()));
                break;
            case "HoradeTrabajo":
                consultarSucursales.setHoradeTrabajo(buffer.toString());
                break;
            case "Latitud":
                consultarSucursales.setLatitud(buffer.toString());
                break;
            case "Longitud":
                consultarSucursales.setLongitud(buffer.toString());
                break;
            case "Mail":
                break;
            case "Numero":
                consultarSucursales.setNumero(buffer.toString());
                break;
            case "Responsable":
                consultarSucursales.setResponsable(buffer.toString());
                break;
            case "Resumen":
                consultarSucursales.setResumen(buffer.toString());
                break;
            case "Sucursal":
                consultarSucursales.setSucursal(Integer.parseInt(buffer.toString()));
                break;
            case "Telefono1":
                consultarSucursales.setTelefono1(buffer.toString());
                break;
            case "Telefono2":
                consultarSucursales.setTelefono2(buffer.toString());
                break;
            case "Telefono3":
                consultarSucursales.setTelefono3(buffer.toString());
                
                break;
            case "TipoSucursal":
                consultarSucursales.setTipoSucursal(buffer.toString());
                break;
            case "TipoTelefono1":
                consultarSucursales.setTipoTelefono1(buffer.toString());
                break;
            case "TipoTelefono2":
                consultarSucursales.setTipoTelefono2(buffer.toString());
                break;
            case "TipoTelefono3":
                consultarSucursales.setTipoTelefono3(buffer.toString());
                break;
            case "item":
                break;
            case "ResultadoConsultarSucursales":
                break;


                
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                switch(qName){
            case "ResultadoConsultarSucursales":
                break;
            case "item":
                consultarSucursales=new ResultadoConsultarSucursales();
                this.resultadoSucursales.add(consultarSucursales);
                
                break;
            case "Descripcion":
                buffer.delete(0, buffer.length());
                break;
            case "Direccion":
                buffer.delete(0, buffer.length());
                break;
            case "HoradeTrabajo":
                buffer.delete(0, buffer.length());
                break;
            case "Latitud":
                buffer.delete(0, buffer.length());
                break;
            case "Longitud":
                buffer.delete(0, buffer.length());
                break;
            case "Mail":
                buffer.delete(0, buffer.length());
                break;
            case "Numero":
                buffer.delete(0, buffer.length());
                break;
            case "Responsable":
                buffer.delete(0, buffer.length());
                break;
            case "Resumen":
                buffer.delete(0, buffer.length());
                break;
              case "Sucursal":
                buffer.delete(0, buffer.length());
                break;
            case "Telefono1":
                buffer.delete(0, buffer.length());
                break;
            case "Telefono2":
                buffer.delete(0, buffer.length());
                break;
            case "Telefono3":
                buffer.delete(0, buffer.length());
                break;
            case "TipoSucursal":
                buffer.delete(0, buffer.length());
                break;
            case "TipoTelefono1":
                buffer.delete(0, buffer.length());
                break;
            case "TipoTelefono2":
                buffer.delete(0, buffer.length());
                break;
            case "TipoTelefono3":
                buffer.delete(0, buffer.length());
                break;

                
        }
    }
    
     public static String convertiraISO(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
}
