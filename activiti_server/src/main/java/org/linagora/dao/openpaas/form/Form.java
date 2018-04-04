package org.linagora.dao.openpaas.form;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Form {

	public String taskId;
	public String processName;
	public List<Formly> form = new ArrayList<Formly>();
	
	public Form(String processName, String taskId, List<Formly> form) {
		super();
		this.processName = processName;
		this.form = form;
		this.taskId = taskId;
	}
	
	public String generateJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
