package com.valework.yingul.logistic;

import java.util.ArrayList;
import java.util.List;

public class ResultadoConsultarSucursalesArray {
	List<ResultadoConsultarSucursales> su=new ArrayList<ResultadoConsultarSucursales>();

	@Override
	public String toString() {
		return "ResultadoConsultarSucursalesArray [su=" + su + "]";
	}

	public ResultadoConsultarSucursalesArray() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ResultadoConsultarSucursales> getSu() {
		return su;
	}

	public void setSu(List<ResultadoConsultarSucursales> su) {
		this.su = su;
	} 
	
}
