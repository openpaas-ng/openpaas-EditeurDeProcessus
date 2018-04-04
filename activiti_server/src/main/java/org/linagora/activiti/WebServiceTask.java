package org.linagora.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.util.json.JSONObject;
import org.linagora.dao.VariableData;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component("startProcessInstanceDelegate")
public class WebServiceTask implements JavaDelegate {

	private Expression url;
	private Expression json;

	public void execute(DelegateExecution execution) throws Exception {
		String jsonStr = null;
		String urlStr = null;
		boolean isGet = false;

		try {
			urlStr = (String) url.getValue(execution);
		} catch (Exception e) {
			throw e;
		}

		try {
			jsonStr = (String) json.getValue(execution);
		} catch (Exception e) {
			// No JSON -> GET
			isGet = true;
		}

		Client client = Client.create();
		WebResource webResource = client.resource(urlStr);

		if (isGet) {
			webResource.get(String.class);
		} else {
			webResource.accept("application/json").type("application/json").post(ClientResponse.class, jsonStr);
		}
	}

}