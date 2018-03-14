package com.valework.yingul.logistic;

public class DhlResponsePackageDetails {
	String billingRef1="";
	String billingRef2="";
	String currency="";
	int declaredValue=0;
	String dimensionUom="";
	String dutiesPaid="";
	int height=30;
	int insuredValue=0;
	int length=0;
	int mailType=0;
	String orderedProduct="";
	String packageDesc="";
	String packageId="";
	String packageRefName="";
	double weight=0;
	String weightUom="";
	public DhlResponsePackageDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBillingRef1() {
		return billingRef1;
	}
	public void setBillingRef1(String billingRef1) {
		this.billingRef1 = billingRef1;
	}
	public String getBillingRef2() {
		return billingRef2;
	}
	public void setBillingRef2(String billingRef2) {
		this.billingRef2 = billingRef2;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(int declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getDimensionUom() {
		return dimensionUom;
	}
	public void setDimensionUom(String dimensionUom) {
		this.dimensionUom = dimensionUom;
	}
	public String getDutiesPaid() {
		return dutiesPaid;
	}
	public void setDutiesPaid(String dutiesPaid) {
		this.dutiesPaid = dutiesPaid;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getInsuredValue() {
		return insuredValue;
	}
	public void setInsuredValue(int insuredValue) {
		this.insuredValue = insuredValue;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getMailType() {
		return mailType;
	}
	public void setMailType(int mailType) {
		this.mailType = mailType;
	}
	public String getOrderedProduct() {
		return orderedProduct;
	}
	public void setOrderedProduct(String orderedProduct) {
		this.orderedProduct = orderedProduct;
	}
	public String getPackageDesc() {
		return packageDesc;
	}
	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageRefName() {
		return packageRefName;
	}
	public void setPackageRefName(String packageRefName) {
		this.packageRefName = packageRefName;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getWeightUom() {
		return weightUom;
	}
	public void setWeightUom(String weightUom) {
		this.weightUom = weightUom;
	}
	@Override
	public String toString() {
		return "DhlpackageDetails [billingRef1=" + billingRef1 + ", billingRef2=" + billingRef2 + ", currency="
				+ currency + ", declaredValue=" + declaredValue + ", dimensionUom=" + dimensionUom + ", dutiesPaid="
				+ dutiesPaid + ", height=" + height + ", insuredValue=" + insuredValue + ", length=" + length
				+ ", mailType=" + mailType + ", orderedProduct=" + orderedProduct + ", packageDesc=" + packageDesc
				+ ", packageId=" + packageId + ", packageRefName=" + packageRefName + ", weight=" + weight
				+ ", weightUom=" + weightUom + "]";
	}
	
}
