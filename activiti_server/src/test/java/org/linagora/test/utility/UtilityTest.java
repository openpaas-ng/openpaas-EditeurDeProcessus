package org.linagora.test.utility;

import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.linagora.utility.DateManager;
import org.linagora.utility.PropertyFile;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class UtilityTest {

	@Test
	public void addHours_AddOneHours_isOk() {
		Date now = new Date();
		Date futur = DateManager.addHours(now, 1);
		Assert.assertTrue(now.before(futur));
		Assert.assertTrue(futur.after(now));
		Assert.assertTrue(now.getTime() < futur.getTime());
	}

	@Test
	public void getProperties_ConfigFileIsNull_ExceptionThrown() {
		try {
			PropertyFile propManager = new PropertyFile();
			propManager.getProperties(null);
			Assert.fail("File name can't be null");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("The property file name can't be null"));
		}
	}

	@Test
	public void getProperties_ParamIsValid_DataPropertiesIsOk() throws Exception {
		PropertyFile propManager = new PropertyFile();
		Properties prop = propManager.getProperties("config/config.properties");
		String pathOpLogin = "/api/login";
		String pathOpNotification = "/api/notifications";
		String pathOpCalendars = "/dav/api/calendars";
		String pathOpCommunity = "/api/collaborations/community";
		
		Assert.assertNotNull(prop);
		Assert.assertEquals(pathOpLogin, prop.get("service.login"));
		Assert.assertEquals(pathOpNotification, prop.get("service.notification"));
		Assert.assertEquals(pathOpCalendars, prop.get("service.calendars"));
		Assert.assertEquals(pathOpCommunity, prop.get("service.community"));
	}
}
