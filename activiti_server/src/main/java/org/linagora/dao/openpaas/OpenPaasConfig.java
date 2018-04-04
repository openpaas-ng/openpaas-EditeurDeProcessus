package org.linagora.dao.openpaas;

import java.util.Properties;

import org.linagora.dao.OpenPaasUser;

public class OpenPaasConfig {

	private String webServiceApi;
	private String loginApiPath;
	private String notificationApiPath;
	private String calendarsApiPath;
	private String comunityApiPath;

	private OpenPaasUser opu;

	public OpenPaasConfig(String webServiceApi, String loginApiPath, String notificationApiPath,
			String calendarsApiPath, String comunityApiPath, OpenPaasUser opu) {
		super();
		this.webServiceApi = webServiceApi;
		this.loginApiPath = loginApiPath;
		this.notificationApiPath = notificationApiPath;
		this.calendarsApiPath = calendarsApiPath;
		this.comunityApiPath = comunityApiPath;
		this.opu = opu;
	}

	public OpenPaasConfig(Properties prop) {
		super();
		webServiceApi = prop.getProperty("service.webservice");
		loginApiPath = prop.getProperty("service.login");
		notificationApiPath = prop.getProperty("service.notification");
		calendarsApiPath = prop.getProperty("service.calendars");
		comunityApiPath = prop.getProperty("service.community");

		opu = new OpenPaasUser(prop.getProperty("user.username"), prop.getProperty("user.password"));
	}

	public String getWebServiceApi() {
		return webServiceApi;
	}

	public void setWebServiceApi(String webServiceApi) {
		this.webServiceApi = webServiceApi;
	}

	public String getLoginApiPath() {
		return loginApiPath;
	}

	public void setLoginApiPath(String loginApiPath) {
		this.loginApiPath = loginApiPath;
	}

	public String getNotificationApiPath() {
		return notificationApiPath;
	}

	public void setNotificationApiPath(String notificationApiPath) {
		this.notificationApiPath = notificationApiPath;
	}

	public String getCalendarsApiPath() {
		return calendarsApiPath;
	}

	public void setCalendarsApiPath(String calendarsApiPath) {
		this.calendarsApiPath = calendarsApiPath;
	}

	public String getComunityApiPath() {
		return comunityApiPath;
	}

	public void setComunityApiPath(String comunityApiPath) {
		this.comunityApiPath = comunityApiPath;
	}

	public OpenPaasUser getOpenPaasUser() {
		return opu;
	}

	public void setOpenPaasUser(OpenPaasUser opu) {
		this.opu = opu;
	}

	public String getFullLoginApiPath() {
		return webServiceApi + loginApiPath;
	}

	public String getFullNotificationApiPath() {
		return webServiceApi + notificationApiPath;
	}

	public String getFullCalendarsApiPath() {
		return webServiceApi + calendarsApiPath;
	}

	public String getFullComunityApiPath() {
		return webServiceApi + comunityApiPath;
	}
}
