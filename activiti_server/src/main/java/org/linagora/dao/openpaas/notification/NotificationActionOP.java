package org.linagora.dao.openpaas.notification;

public enum NotificationActionOP {
	CREATED("created");
	
	private String action;

	public String getAction() {
		return action;
	}

	private NotificationActionOP(String action) {
		this.action = action;
	}
}
