package org.linagora.dao.openpaas.form;

public enum FormlyClass {

	INPUT("form-control ng-pristine ng-untouched ng-invalid-required"),
	SELECT("form-control"),
	NUMBER("form-control")
	;
	
	private final String classStr;
	
	private FormlyClass(final String classStr){
		this.classStr = classStr;
	}

	public String getClassStr() {
		return classStr;
	}
 }
