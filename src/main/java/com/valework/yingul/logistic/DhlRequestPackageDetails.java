package com.valework.yingul.logistic;

public class DhlRequestPackageDetails {
	String packageRefName="";
	//--valorAsegurado
	double insuredValue   =0;
	//peso¿que valores?
	double weight   =0;
	//valorDeclarado
	double declaredValue   =0;
	//tipo de correo
	int mailType   =0;
	double height   =0;
	//descripción del paquete
	String packageDesc="";
	//moneda AUD
	String currency="";
	//longitud
	double length   =0;
	//weight Uom G
	String weightUom="";
	//facturación Ref2
	String billingRef2="";
	//	ID del paquete de donde se obtien
	String packageId="";
	//impuestos pagados¿?
	String dutiesPaid="";
	//Producto ordenado¿?
	String orderedProduct="";
	//dimensiones CM
	String dimensionUom="";
	//facturación Ref1 ¿a que se refiere?
	String billingRef1="";
	public DhlRequestPackageDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPackageRefName() {
		return packageRefName;
	}
	public void setPackageRefName(String packageRefName) {
		this.packageRefName = packageRefName;
	}
	public double getInsuredValue() {
		return insuredValue;
	}
	public void setInsuredValue(double insuredValue) {
		this.insuredValue = insuredValue;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public int getMailType() {
		return mailType;
	}
	public void setMailType(int mailType) {
		this.mailType = mailType;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public String getPackageDesc() {
		return packageDesc;
	}
	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public String getWeightUom() {
		return weightUom;
	}
	public void setWeightUom(String weightUom) {
		this.weightUom = weightUom;
	}
	public String getBillingRef2() {
		return billingRef2;
	}
	public void setBillingRef2(String billingRef2) {
		this.billingRef2 = billingRef2;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getDutiesPaid() {
		return dutiesPaid;
	}
	public void setDutiesPaid(String dutiesPaid) {
		this.dutiesPaid = dutiesPaid;
	}
	public String getOrderedProduct() {
		return orderedProduct;
	}
	public void setOrderedProduct(String orderedProduct) {
		this.orderedProduct = orderedProduct;
	}
	public String getDimensionUom() {
		return dimensionUom;
	}
	public void setDimensionUom(String dimensionUom) {
		this.dimensionUom = dimensionUom;
	}
	public String getBillingRef1() {
		return billingRef1;
	}
	public void setBillingRef1(String billingRef1) {
		this.billingRef1 = billingRef1;
	}
	@Override
	public String toString() {
		return "DhlRequestPackageDetails [packageRefName=" + packageRefName + ", insuredValue=" + insuredValue
				+ ", weight=" + weight + ", declaredValue=" + declaredValue + ", mailType=" + mailType + ", height="
				+ height + ", packageDesc=" + packageDesc + ", currency=" + currency + ", length=" + length
				+ ", weightUom=" + weightUom + ", billingRef2=" + billingRef2 + ", packageId=" + packageId
				+ ", dutiesPaid=" + dutiesPaid + ", orderedProduct=" + orderedProduct + ", dimensionUom=" + dimensionUom
				+ ", billingRef1=" + billingRef1 + "]";
	}
	
	
}
