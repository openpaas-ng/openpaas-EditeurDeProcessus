package org.linagora.dao.openpaas.notification;

public enum NotificationObjectOP {

	FORM("form");
	
	private String object;

	public String getObject() {
		return object;
	}

	private NotificationObjectOP(String object) {
		this.object = object;
	}
	
}
