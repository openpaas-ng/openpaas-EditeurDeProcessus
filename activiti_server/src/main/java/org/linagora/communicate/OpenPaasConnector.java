package org.linagora.communicate;

import java.util.Properties;

import org.json.JSONObject;
import org.linagora.dao.ActionActiviti;
import org.linagora.dao.OpenPaasUser;
import org.linagora.dao.openpaas.OpenPaasConfig;
import org.linagora.dao.openpaas.TypeRequest;
import org.linagora.dao.openpaas.notification.NotificationOP;
import org.linagora.utility.PropertyFile;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import biweekly.Biweekly;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

public class OpenPaasConnector {
	private final String configFilePath = "config/config.properties";

	private OpenPaasConfig openPaasConfig;

	private String userId;
	private String uiId;

	private final String type = "application/json";

	private WebResource webResource;
	private ApacheHttpClient client;

	public OpenPaasConnector() {
		try {
			PropertyFile propertyFile = new PropertyFile();
			Properties prop = propertyFile.getProperties(configFilePath);

			openPaasConfig = new OpenPaasConfig(prop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getPath(ActionActiviti action) throws Exception {
		switch (action) {
		case MAIL:
			return openPaasConfig.getWebServiceApi(); // TODO
		case CALENDAR:
			return openPaasConfig.getFullCalendarsApiPath() + "/" + userId + "/events/" + uiId + ".ics?graceperiod=1";
		case NOTIFICATION:
			return openPaasConfig.getFullNotificationApiPath();
		case COMUNITY_MEMBER:
			return openPaasConfig.getFullComunityApiPath() + "/" + uiId + "/members";
		default:
			throw new Exception("WebService path should exist");

		}
	}

	private void prepareRequest(OpenPaasUser user, String ws) {
		HTTPBasicAuthFilter auth = new HTTPBasicAuthFilter(user.getUsername(), user.getPassword());
		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
		config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);

		client = ApacheHttpClient.create(config);
		client.addFilter(auth);
		webResource = client.resource(openPaasConfig.getWebServiceApi() + ws);
	}

	public ApacheHttpClient login(OpenPaasUser user) throws Exception {
		try {
			prepareRequest(user, openPaasConfig.getLoginApiPath());
			ClientResponse response = webResource.type(type).post(ClientResponse.class, user.generateJson());

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String resultJson = response.getEntity(String.class);
			userId = new JSONObject(resultJson).getString("_id");
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String wsCallGenerator(ActionActiviti action, Object request) throws Exception {
		ApacheHttpClient client = login(openPaasConfig.getOpenPaasUser());
		String json = null;
		TypeRequest type = TypeRequest.POST;
		switch (action) {
		case MAIL:
			// TODO Something..
			break;
		case CALENDAR:
			Calendar cal = (Calendar) request;
			json = Biweekly.parse(cal.toString()).first().writeJson();
			type = TypeRequest.PUT;
			uiId = ((VEvent) cal.getComponents().get(0)).getUid().getValue();
			break;
		case NOTIFICATION:
			NotificationOP notification = (NotificationOP) request;
			notification.setAuthor(userId);
			json = notification.generateJson();
			break;
		case COMUNITY_MEMBER:
			uiId = (String) request;
			type = TypeRequest.GET;
		default :
			 throw new Exception("WebService path should exist");
		}


		return OpenPaasAPI.wsOpCall(client, getPath(action), json, type);
	}

	public String getUserId() {
		return userId;
	}

	public OpenPaasConfig getOpenPaasConfig() {
		return openPaasConfig;
	}
}
