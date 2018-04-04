package org.linagora.dao.openpaas.notification;

public class NotificationTargetOP {

	private NotificationObjectTypeOP objectType;
	private String id;
	
	public NotificationTargetOP(NotificationObjectTypeOP objectType, String id) {
		super();
		this.objectType = objectType;
		this.id = id;
	}
	
	public NotificationObjectTypeOP getObjectType() {
		return objectType;
	}
	public void setObjectType(NotificationObjectTypeOP objectType) {
		this.objectType = objectType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
