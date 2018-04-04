package org.linagora.dao.openpaas.notification;

public enum NotificationObjectTypeOP {
	USER("user"), COMMUNITY("community");

	private String objectType;

	public String getObjectType() {
		return objectType;
	}

	private NotificationObjectTypeOP(String objectType) {
		this.objectType = objectType;
	}
}

