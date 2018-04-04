package org.linagora.test.communicate;

import org.junit.Assert;
import org.junit.Test;
import org.linagora.communicate.OpenPaasAPI;
import org.linagora.dao.openpaas.TypeRequest;
import org.linagora.test.populate.MockitoGeneratorOpenPaasApi;
import org.springframework.boot.test.context.TestComponent;

import com.sun.jersey.client.apache.ApacheHttpClient;

@TestComponent
public class OpenPaasAPITest {

	private final String wsPath = "http://localhost/path";
	private final String json = "{'mock' : 'mockJson'}";

	private ApacheHttpClient clientMockup;

	public void initTestData(boolean validData) {
		try {
			clientMockup = MockitoGeneratorOpenPaasApi.generateMockApacheClient(validData);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void wsOpCall_ClientNull_ThrowException() {
		try {
			OpenPaasAPI.wsOpCall(null, wsPath, json, TypeRequest.GET);
			Assert.fail("Should throw an exception if no client");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Failed : HTTP Login"));
		}
	}

	@Test
	public void wsOpCall_TypeRequestNull_ThrowException() {
		initTestData(true);
		try {
			OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, null);
			Assert.fail("Should throw an exception if no client");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("TypeRequest can't be null"));
		}
	}

	@Test
	public void wsOpCall_ClientFailGet_ThrowException() {
		initTestData(false);
		try {
			OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, TypeRequest.GET);
			Assert.fail("Should throw an exception if client error");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Failed : HTTP error code : "));
		}
	}

	@Test
	public void wsOpCall_ClientFailPost_ThrowException() {
		initTestData(false);
		try {
			OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, TypeRequest.POST);
			Assert.fail("Should throw an exception if client error");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Failed : HTTP error code : "));
		}
	}

	@Test
	public void wsOpCall_ClientFailPut_ThrowException() {
		initTestData(false);
		try {
			OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, TypeRequest.PUT);
			Assert.fail("Should throw an exception if client error");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Failed : HTTP error code : "));
		}
	}

	@Test
	public void wsOpCall_SuccesClientPut_isOk() {
		initTestData(true);
		try {
			String result = OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, TypeRequest.PUT);
			Assert.assertEquals("Succes", result);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should'nt throw an exception if client ok");
		}
	}

	@Test
	public void wsOpCall_SuccesClientGet_isOk() {
		initTestData(true);
		try {
			String result = OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, TypeRequest.GET);
			Assert.assertEquals("Succes", result);
		} catch (Exception e) {
			Assert.fail("Should'nt throw an exception if client ok");
		}
	}

	@Test
	public void wsOpCall_SuccesClientPost_isOk() {
		initTestData(true);
		try {
			String result = OpenPaasAPI.wsOpCall(clientMockup, wsPath, json, TypeRequest.POST);
			Assert.assertEquals("Succes", result);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should'nt throw an exception if client ok");
		}
	}

}
