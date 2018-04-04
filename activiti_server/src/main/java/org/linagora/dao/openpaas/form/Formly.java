package org.linagora.dao.openpaas.form;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Formly {

	private String key;
	private String type;
	private String defaultValue;
	public TemplateOptions templateOptions;

	@SuppressWarnings("unused")
	private NgModelElAttrs ngModelElAttrs; /* Value depend of type */

	public Formly(String key, String type) {
		super();
		this.key = key;

		if (type.equals("number"))
			this.type = "input";
		else
			this.type = type;

		/* Class assignement */
		if (type.equals("input"))
			ngModelElAttrs = new NgModelElAttrs(FormlyClass.INPUT.getClassStr());
		else if (type.equals("select"))
			ngModelElAttrs = new NgModelElAttrs(FormlyClass.SELECT.getClassStr());
		else if (type.equals("number"))
			ngModelElAttrs = new NgModelElAttrs(FormlyClass.NUMBER.getClassStr());
	}

	public Formly(String key, String type, TemplateOptions templateOptions) {
		this(key, type);
		this.templateOptions = templateOptions;
	}

	public Formly(String key, String type, TemplateOptions templateOptions, String defaultValue) {
		this(key, type, templateOptions);
		if (defaultValue != null)
			this.defaultValue = defaultValue;
	}

	public Formly(String key, String type, String defaultValue) {
		this(key, type);
		if (defaultValue != null)
			this.defaultValue = defaultValue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public TemplateOptions getTemplateOptions() {
		return templateOptions;
	}

	public void setTemplateOptions(TemplateOptions templateOptions) {
		this.templateOptions = templateOptions;
	}

	public String generateJson() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		return gson.toJson(this);
	}

}

class NgModelElAttrs {

	@SerializedName("class")
	public String classForTemplate;

	public NgModelElAttrs(String value) {
		classForTemplate = value;
	}

}
