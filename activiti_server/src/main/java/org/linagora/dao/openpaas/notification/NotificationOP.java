package org.linagora.dao.openpaas.notification;

import java.util.List;

import com.google.gson.Gson;

public class NotificationOP {

	private String title;
	private NotificationActionOP action;
	private NotificationObjectOP object;
	private String link;
	private NotificationLevelOP level;
	private String author;
	
	private List<NotificationTargetOP> target;
	
	public NotificationOP(String title, NotificationActionOP action, NotificationObjectOP object, String link,
			NotificationLevelOP level, String author, List<NotificationTargetOP> target) {
		super();
		this.title = title;
		this.action = action;
		this.object = object;
		this.link = link;
		this.level = level;
		this.author = author;
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public NotificationActionOP getAction() {
		return action;
	}

	public void setAction(NotificationActionOP action) {
		this.action = action;
	}

	public NotificationObjectOP getObject() {
		return object;
	}

	public void setObject(NotificationObjectOP object) {
		this.object = object;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public NotificationLevelOP getLevel() {
		return level;
	}

	public void setLevel(NotificationLevelOP level) {
		this.level = level;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<NotificationTargetOP> getTarget() {
		return target;
	}

	public void setTarget(List<NotificationTargetOP> target) {
		this.target = target;
	}
	
	public String generateJson(){
		Gson gson = new Gson();
		String json = gson.toJson(this);
		json = json.toLowerCase();
		json = json.replace("\"objecttype\"", "\"objectType\"");
		return json;
	}
	
	
}
