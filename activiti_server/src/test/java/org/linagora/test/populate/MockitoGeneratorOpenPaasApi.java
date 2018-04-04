package org.linagora.test.populate;

import org.mockito.Matchers;
import org.mockito.Mockito;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.client.apache.ApacheHttpClient;

public class MockitoGeneratorOpenPaasApi {

	private final static String type = "application/json";
	private final static String json = "{'mock' : 'mockJson'}";

	
	private static ClientResponse generateClientResponse(int status, String entityMsg) {
		ClientResponse mockitoResponse = Mockito.mock(ClientResponse.class);
		try {
			Mockito.when(mockitoResponse.getStatus()).thenReturn(status);
			Mockito.when(mockitoResponse.getEntity(String.class)).thenReturn(entityMsg);
		} catch (Exception e) {
			throw e;
		}
		return mockitoResponse;
	}

	private static Builder generateBuilder(boolean valideResponse) {
		Builder mockitoBuilder = Mockito.mock(Builder.class);
		int status = 400;
		String msgEntity = null;
		if (valideResponse) {
			status = 200;
			msgEntity = "Succes";
		}

		try {
			ClientResponse mockitoResponse = generateClientResponse(status, msgEntity);
			Mockito.when(mockitoBuilder.get(ClientResponse.class)).thenReturn(mockitoResponse);
			Mockito.when(mockitoBuilder.post(ClientResponse.class, json)).thenReturn(mockitoResponse);
			Mockito.when(mockitoBuilder.put(ClientResponse.class, json)).thenReturn(mockitoResponse);

		} catch (Exception e) {
			throw e;
		}
		return mockitoBuilder;

	}

	private static WebResource generateMockWebResource(boolean valideResponse) {
		WebResource mockitoWebResource = Mockito.mock(WebResource.class);
		try {
			Builder builder = generateBuilder(valideResponse);
			Mockito.when(mockitoWebResource.type(type)).thenReturn(builder);
		} catch (Exception e) {
			throw e;
		}

		return mockitoWebResource;
	}

	public static ApacheHttpClient generateMockApacheClient(boolean valideRequest) throws Exception {
		ApacheHttpClient mockitoClient = Mockito.mock(ApacheHttpClient.class);
		try {
			WebResource mockitoWebResource = generateMockWebResource(valideRequest);
			Mockito.when(mockitoClient.resource(Matchers.anyString())).thenReturn(mockitoWebResource);
		} catch (Exception e) {
			throw e;
		}

		return mockitoClient;
	}
}
