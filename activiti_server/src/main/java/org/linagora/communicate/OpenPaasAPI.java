package org.linagora.communicate;

import org.linagora.dao.openpaas.TypeRequest;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;

public class OpenPaasAPI {

	private final static String type = "application/json";

	public static String wsOpCall(ApacheHttpClient client, String webServicePath, String json, TypeRequest request) throws Exception {
		try {
			if(request == null)
				throw new Exception("TypeRequest can't be null");

			if (client != null) {
				WebResource webResource = client.resource(webServicePath);
				ClientResponse response = null;

				if (request.equals(TypeRequest.POST))
					response = webResource.type(type).post(ClientResponse.class, json);
				else if (request.equals(TypeRequest.PUT))
					response = webResource.type(type).put(ClientResponse.class, json);
				else if (request.equals(TypeRequest.GET))
					response = webResource.type(type).get(ClientResponse.class);

				if (response == null || response.getStatus() >= 210) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}
				return response.getEntity(String.class);
			}
			throw new RuntimeException("Failed : HTTP Login");
		} catch (Exception e) {
			throw e;
		}
	}

}
