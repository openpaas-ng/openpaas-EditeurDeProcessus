package org.linagora.test.communicate;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.linagora.communicate.NotificationUtility;
import org.linagora.dao.openpaas.notification.NotificationActionOP;
import org.linagora.dao.openpaas.notification.NotificationLevelOP;
import org.linagora.dao.openpaas.notification.NotificationOP;
import org.linagora.dao.openpaas.notification.NotificationObjectOP;
import org.linagora.dao.openpaas.notification.NotificationObjectTypeOP;
import org.linagora.dao.openpaas.notification.NotificationTargetOP;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class NotificationTest {

	private final static String errorNullMsg = " can't be null";

	private final static String link = "http://localhost:3000/form/myNewNotification";
	private final static String author = "The author";
	private final static String target = "A random target";

	private final static String defaultTitle = "MyActivitiNotification";

	@Test
	public void createNotification_TargetNull_ExceptionThrown() throws Exception {
		try {
			NotificationUtility.createNotification(link, author, null);
			Assert.fail("Should throw exception when Target is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains(errorNullMsg));
		}
	}

	@Test
	public void createNotification_AuthorNull_ExceptionThrown() throws Exception {
		try {
			NotificationUtility.createNotification(link, null, target);
			Assert.fail("Should throw exception when Author is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains(errorNullMsg));
		}
	}

	@Test
	public void createNotification_LinkIsFake_ExceptionThrown() throws Exception {
		try {
			String fakeLink = "fake link";
			NotificationUtility.createNotification(fakeLink, author, target);
			Assert.fail("Should throw exception when Link is wrong");
		} catch (MalformedURLException e) {
			Assert.assertTrue(e.toString().contains("java.net.MalformedURLException"));
		}
	}

	@Test
	public void createNotification_LinkNull_ExceptionThrown() throws Exception {
		try {
			NotificationUtility.createNotification(null, author, target);
			Assert.fail("Should throw exception when Link is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains(errorNullMsg));
		}
	}

	@Test
	public void createNotification_ParamValid_isCreate() throws IllegalArgumentException, MalformedURLException {
		List<NotificationTargetOP> targetNotification = new ArrayList<NotificationTargetOP>();
		targetNotification.add(new NotificationTargetOP(NotificationObjectTypeOP.USER, target));

		NotificationOP notification = NotificationUtility.createNotification(link, author, target);

		Assert.assertNotNull(notification);
		Assert.assertEquals(link, notification.getLink());
		Assert.assertEquals(author, notification.getAuthor());
		Assert.assertTrue(checkNotificationTarget(targetNotification, notification.getTarget()));
		Assert.assertEquals(defaultTitle, notification.getTitle());
		Assert.assertEquals(NotificationActionOP.CREATED, notification.getAction());
		Assert.assertEquals(NotificationObjectOP.FORM, notification.getObject());
		Assert.assertEquals(NotificationLevelOP.TRANSIENT, notification.getLevel());
	}

	private boolean checkNotificationTarget(List<NotificationTargetOP> targetNormalyCreate,
			List<NotificationTargetOP> targetNotification) {
		for (NotificationTargetOP targetCreate : targetNormalyCreate) {
			boolean find = false;
			for (NotificationTargetOP targetNotificationCreate : targetNotification) {
				if (targetCreate.getId().equals(targetNotificationCreate.getId())
						&& targetCreate.getObjectType().equals(targetNotificationCreate.getObjectType())) {
					find = true;
				}
			}

			if (!find)
				return false;
			find = false;
		}
		return true;
	}

}
