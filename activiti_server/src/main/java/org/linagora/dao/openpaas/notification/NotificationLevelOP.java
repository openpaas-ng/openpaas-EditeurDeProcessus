package org.linagora.dao.openpaas.notification;

public enum NotificationLevelOP {

	TRANSIENT("transient"),
	PERSISTANT("persistant"),
	INFORMATION("information");
	
	private String level;

	public String getLevel() {
		return level;
	}

	private NotificationLevelOP(String level) {
		this.level = level;
	}
}
