package org.linagora.communicate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.linagora.dao.openpaas.notification.NotificationActionOP;
import org.linagora.dao.openpaas.notification.NotificationLevelOP;
import org.linagora.dao.openpaas.notification.NotificationOP;
import org.linagora.dao.openpaas.notification.NotificationObjectOP;
import org.linagora.dao.openpaas.notification.NotificationObjectTypeOP;
import org.linagora.dao.openpaas.notification.NotificationTargetOP;

public class NotificationUtility {

	public static NotificationOP createNotification(String link, String author, String target) throws IllegalArgumentException, MalformedURLException {

		checkVariable(link, author, target);

		List<NotificationTargetOP> targetNotification = new ArrayList<NotificationTargetOP>();
		targetNotification.add(new NotificationTargetOP(NotificationObjectTypeOP.USER, target));

		NotificationOP opNot = new NotificationOP("MyActivitiNotification", NotificationActionOP.CREATED,
				NotificationObjectOP.FORM, link, NotificationLevelOP.TRANSIENT, author, targetNotification);
		return opNot;
	}

	private static void checkVariable(String link, String author, String target) throws IllegalArgumentException, MalformedURLException {
		if (link == null) {
			throw new IllegalArgumentException("Notification link can't be null");
		} else if(author == null) {
			throw new IllegalArgumentException("Notification author can't be null");
		} else if(target == null){
			throw new IllegalArgumentException("Notification target can't be null");
		}

		try {
			new URL(link);
		} catch (MalformedURLException malformedURLException) {
			throw malformedURLException;
		}
	}
}