package org.linagora.dao.openpaas.form;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.form.FormProperty;

import com.google.gson.Gson;

public class TemplateOptions {

	private String type;
	private String label;
	private String placeholder;
	private String description;
	private String className;
	private boolean required = false;
	private List<Options> options;

	/*For the hidden form*/
	public TemplateOptions(String label, String type){
		this.label = label;
		this.type = type;
	}

	public TemplateOptions(FormProperty propertyForm, FormlyType type) {
		this.className = "label-formly";

		if(propertyForm.getName() != null)
			this.label = propertyForm.getName();
		else
			this.label = propertyForm.getId();
		this.placeholder = this.label;

		this.required = propertyForm.isRequired();
		if(type.equals(FormlyType.LONG) || type.equals(FormlyType.INTEGER))
			this.setType(type);
		else if(type.equals(FormlyType.HIDDEN))
			this.type = FormlyType.HIDDEN.getTypeFormly();
	}

	public TemplateOptions(FormProperty property, FormlyType type, List<Options> options) {
		this(property, type);
		this.options = options;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getType() {
		return type;
	}
	public void setType(FormlyType type) {
		this.type = type.getTypeFormly();
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean addOption(String name, String value) {
		if(this.options == null)
			this.options = new ArrayList<Options>();
		return this.options.add(new Options(name, value));
	}
	public List<Options> getOptions() {
		return options;
	}
	public void setOptions(List<Options> options) {
		this.options = options;
	}

	public String generateJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}


/*Needed for enumeration*/
class Options {
	private String name;
	private String value;

	public Options(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String generateJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}