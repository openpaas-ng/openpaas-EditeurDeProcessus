package org.linagora.dao;

import com.google.gson.Gson;

public class ProcessData {

	private final String processId; /* Id of the bpmn -> it's a name */
	private final String processName; /* Name of the bpmn */

	private final TaskActiviti type; /* Difference between task */
	private final String idTask; /* Id of the task to complete */
	private String nameTask; /* Name of the task */
	private String assignee;

	public ProcessData(String processId, String processName, TaskActiviti type, String idTask, String assignee) {
		super();
		this.processId = processId;
		this.processName = processName;
		this.type = type;
		this.idTask = idTask;
		this.assignee = assignee;
	}

	public String getProcessId() {
		return processId;
	}

	public String getProcessName() {
		return processName;
	}

	public TaskActiviti getType() {
		return type;
	}

	public String getIdTask() {
		return idTask;
	}

	public String getNameTask() {
		return nameTask;
	}

	public String getAssignee() {
		return assignee;
	}

	public String generateJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
