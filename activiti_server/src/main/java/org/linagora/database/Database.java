package org.linagora.database;

import org.linagora.dao.ActivitiDAO;

public class Database {
	
	/*Use OpenPaas API*/

	public static String create(ActivitiDAO activitiBpmn){
		return activitiBpmn.getName();
	}

	public static String edit(ActivitiDAO bpmn){
		String idEdit = bpmn.getName();
		return idEdit;
	}

	public static String delete(String id){
		String idDelete = id;
		return idDelete;
	}

}
