package org.linagora.dao;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class ActivitiDAO {

	public String idNumber;
	public String processId;

	@Expose(serialize = false, deserialize = false)
	public File file;

	public ActivitiDAO(String process, File file) {
		super();
		this.processId = process;
		this.file = file;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String process) {
		this.processId = process;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return file.getName();
	}

	public String generateJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public String toString() {
		return "ActivitiDAO [idNumber=" + idNumber + ", processId=" + processId + ", file=" + file + "]";
	}

}
