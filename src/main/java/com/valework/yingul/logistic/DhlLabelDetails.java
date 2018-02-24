package com.valework.yingul.logistic;

public class DhlLabelDetails {
	
	String  packageId="";
	String  format="";
	String  templateId="";
	java.lang.String labelData;
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public java.lang.String getLabelData() {
		return labelData;
	}
	public void setLabelData(java.lang.String labelData) {
		this.labelData = labelData;
	}
	@Override
	public String toString() {
		return "DhlLabelDetails [packageId=" + packageId + ", format=" + format + ", templateId=" + templateId
				+ ", labelData=" + labelData + "]";
	}
	public DhlLabelDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
