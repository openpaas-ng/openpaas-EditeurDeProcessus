package org.linagora.test.communicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.linagora.communicate.OpenPaasConnector;
import org.linagora.dao.openpaas.OpenPaasConfig;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class OpenPaasConnectorTest {

	OpenPaasConnector opConnect = new OpenPaasConnector();
	
	//Param from the file src/main/resources/config/config.properties
	String opWsPath = "http://localhost:8080";
	String opLoginPath = "/api/login";
	String opNotificationPath = "/api/notifications";
	String opCalendarPath = "/dav/api/calendars";
	String opCommunityPath= "/api/collaborations/community";

	@Before
	public void setUp() {
		opConnect = new OpenPaasConnector();
	}
	
	@Test
	public void openPaasConnector_VerifyInit_ParamOk() {
		OpenPaasConfig opc = opConnect.getOpenPaasConfig();

		Assert.assertEquals(opWsPath, opc.getWebServiceApi());
		Assert.assertEquals(opLoginPath, opc.getLoginApiPath());
		Assert.assertEquals(opNotificationPath, opc.getNotificationApiPath());
		Assert.assertEquals(opCalendarPath, opc.getCalendarsApiPath());
		Assert.assertEquals(opCommunityPath, opc.getComunityApiPath());
		
		Assert.assertEquals("admin@open-paas.org", opc.getOpenPaasUser().getUsername());
		Assert.assertEquals("secret", opc.getOpenPaasUser().getPassword());

		Assert.assertEquals(opWsPath+opCalendarPath, opc.getFullCalendarsApiPath());
		Assert.assertEquals(opWsPath+opCommunityPath, opc.getFullComunityApiPath());
		Assert.assertEquals(opWsPath+opLoginPath, opc.getFullLoginApiPath());
		Assert.assertEquals(opWsPath+opNotificationPath, opc.getFullNotificationApiPath());
	}
}
