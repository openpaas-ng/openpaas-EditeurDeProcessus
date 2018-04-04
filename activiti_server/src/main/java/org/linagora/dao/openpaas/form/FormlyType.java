package org.linagora.dao.openpaas.form;

public enum FormlyType {

	ENUM("select", "select"),
	STRING("string", "input"),
	INTEGER("number", "number"),
	LONG("number", "number"),
	BOOLEAN("conditional", "checkbox"),
	DATE("date", "date"),
	HIDDEN("hidden", "hidden"),
	CUSTOM_TYPE("custom type", "custom")
	;
	
	private final String typeBpmn;		/*Name generate by activiti*/
	private final String typeFormly;	/*Name needed for generate an form by angular-formly*/
	
	private FormlyType(final String typeBpmn, final String typeFormly){
		this.typeBpmn = typeBpmn;
		this.typeFormly = typeFormly;
	}
	
    public String getTypeBpmn() {
		return typeBpmn;
	}

	public String getTypeFormly() {
		return typeFormly;
	}

	public boolean equalsName(String otherType) {
        return (otherType == null) ? false : typeBpmn.equals(otherType);
    }
 }
