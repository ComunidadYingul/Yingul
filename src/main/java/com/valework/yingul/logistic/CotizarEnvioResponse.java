/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valework.yingul.logistic;


public class CotizarEnvioResponse {
    private String CategoriaDistancia;
    private String CategoriaDistanciaId;
    private String CategoriaPeso;
    private String CategoriaPesoId;
    private String PesoAforado;
    private String Tarifa;

    public String getCategoriaDistancia() {
        return CategoriaDistancia;
    }

    public void setCategoriaDistancia(String CategoriaDistancia) {
        this.CategoriaDistancia = CategoriaDistancia;
    }

    public String getCategoriaDistanciaId() {
        return CategoriaDistanciaId;
    }

    public void setCategoriaDistanciaId(String CategoriaDistanciaId) {
        this.CategoriaDistanciaId = CategoriaDistanciaId;
    }

    public String getCategoriaPeso() {
        return CategoriaPeso;
    }

    public void setCategoriaPeso(String CategoriaPeso) {
        this.CategoriaPeso = CategoriaPeso;
    }

    public String getCategoriaPesoId() {
        return CategoriaPesoId;
    }

    public void setCategoriaPesoId(String CategoriaPesoId) {
        this.CategoriaPesoId = CategoriaPesoId;
    }

    public String getPesoAforado() {
        return PesoAforado;
    }

    public void setPesoAforado(String PesoAforado) {
        this.PesoAforado = PesoAforado;
    }

    public String getTarifa() {
        return Tarifa;
    }

    public void setTarifa(String Tarifa) {
        this.Tarifa = Tarifa;
    }

    @Override
    public String toString() {
        return "CotizarEnvioResponse{" + "CategoriaDistancia=" + CategoriaDistancia + ", CategoriaDistanciaId=" + CategoriaDistanciaId + ", CategoriaPeso=" + CategoriaPeso + ", CategoriaPesoId=" + CategoriaPesoId + ", PesoAforado=" + PesoAforado + ", Tarifa=" + Tarifa + '}';
    }
    
    
        
}
