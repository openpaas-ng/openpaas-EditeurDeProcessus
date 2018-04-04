package org.linagora.test.activiti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONArray;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.linagora.activiti.ActivitiProcess;
import org.linagora.dao.ActivitiDAO;
import org.linagora.exception.ExceptionGeneratorActiviti;
import org.linagora.service.ApplicationWorkflowReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@TestComponent
public class ActivitiProcessTaskTest {

	private static ConfigurableApplicationContext application;

	private final static String xmlPathInput = "src/test/resources/inputXml/";

	private final static String form_string_bpmn = "form_string";
	private final static String form_userMail_bpmn = "form_userMail";
	private final static String form_number_bpmn = "form_number";
	private final static String form_gateway_bpmn = "form_gateway";
	private final static String form_dkms_bpmn = "form_dkms";

	private final static boolean isExecuted = true;

	private final static ActivitiProcess aProcess = new ActivitiProcess();

	private final static String testUser = "test@test.com";
	private final static String fakeUser = "fake@test.com";

	private final static String taskIdMapKey = "taskId";

	private final static String processIdMapKey = "processId";
	private final static String DMKS_IdTask = "idTask";

	@BeforeClass
	public static void setUp() throws FileNotFoundException{
		application = SpringApplication.run(ApplicationWorkflowReader.class);
		changeSystemOutputTest();
	}

	@AfterClass
	public static void tearDown(){
		application.close();
	}

	private MultipartFile getMockCommonsMultipartFile(File file) throws IOException {
		FileInputStream inputFile = new FileInputStream(file.getAbsolutePath());
		MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "multipart/form-data",
				inputFile);
		return multipartFile;
	}

	public static void changeSystemOutputTest() throws FileNotFoundException {
		OutputStream output = new FileOutputStream("/dev/null");
		PrintStream nullOut = new PrintStream(output);
		System.setErr(nullOut);
	}

	public void completeTask(JSONArray jsonArray, boolean isString) throws ExceptionGeneratorActiviti {
		Map<String, Object> mapAttribute = new HashMap<String, Object>();
		mapAttribute.put(taskIdMapKey, jsonArray.getJSONObject(0).get(taskIdMapKey));
		if (isString)
			mapAttribute.put("string", "string");
		else
			mapAttribute.put("integer", 50);

		aProcess.completeUserTask(mapAttribute);
	}

	public ActivitiDAO checkActivitiDao(String jsonActiviti, String fileName, boolean isExecute) {
		ActivitiDAO activitiData = new Gson().fromJson(jsonActiviti, ActivitiDAO.class);

		Assert.assertEquals(fileName, activitiData.getProcessId());
		Assert.assertTrue(activitiData.getFile().getAbsolutePath().contains(fileName));
		if (isExecute)
			Assert.assertNotNull(activitiData.getIdNumber());
		else
			Assert.assertNull(activitiData.getIdNumber());

		return activitiData;
	}

	private void checkJsonArray(JSONArray jsonarray, int expectedSize, boolean isFake) {
		Assert.assertEquals(expectedSize, jsonarray.length());
		if (!isFake) {
			Assert.assertNotNull(jsonarray.getJSONObject(0));
			Assert.assertNotNull(jsonarray.getJSONObject(0).get(taskIdMapKey));
		}
	}

	@Test
	public void completeUserTask_executeFormString_TaskFindAndComplete()
			throws ExceptionGeneratorActiviti, IOException {
		try {
			MultipartFile multipartFile = getMockCommonsMultipartFile(new File(xmlPathInput + form_string_bpmn));
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);

			String jsonListTask = aProcess.listTask(testUser);
			Assert.assertNotNull(jsonListTask);

			JSONArray jsonarray = new JSONArray(jsonListTask);
			checkJsonArray(jsonarray, 1, false);
			completeTask(jsonarray, true);

			Assert.assertEquals(0, new JSONArray(aProcess.listTask(testUser)).length());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void completeUserTask_executeFormNumber_TaskFindAndComplete()
			throws ExceptionGeneratorActiviti, IOException {
		try {
			MultipartFile multipartFile = getMockCommonsMultipartFile(new File(xmlPathInput + form_number_bpmn));
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);

			String jsonListTask = aProcess.listTask(testUser);
			Assert.assertNotNull(jsonListTask);

			JSONArray jsonarray = new JSONArray(jsonListTask);
			checkJsonArray(jsonarray, 1, false);
			completeTask(jsonarray, false);

			Assert.assertEquals(0, new JSONArray(aProcess.listTask(testUser)).length());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void listTask_checkMailList_FilterOkAndExecute() throws ExceptionGeneratorActiviti, IOException {
		try {

			// TODO check user list
			MultipartFile multipartFile = getMockCommonsMultipartFile(new File(xmlPathInput + form_userMail_bpmn));
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);

			String jsonListTask = aProcess.listTask(testUser);
			Assert.assertNotNull(jsonListTask);
			JSONArray jsonarray = new JSONArray(jsonListTask);
			checkJsonArray(jsonarray, 1, false);

			String jsonListTaskFake = aProcess.listTask(fakeUser);
			Assert.assertNotNull(jsonListTaskFake);
			JSONArray jsonarrayFake = new JSONArray(jsonListTaskFake);
			checkJsonArray(jsonarrayFake, 0, true);

			completeTask(jsonarray, true);

			Assert.assertEquals(0, new JSONArray(aProcess.listTask(testUser)).length());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void completeReiceiveTask_executeGateway_wayOne() throws ExceptionGeneratorActiviti, IOException {
		try {
			MultipartFile multipartFile = getMockCommonsMultipartFile(new File(xmlPathInput + form_gateway_bpmn));
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			String jsonList = aProcess.dataReader();
			JSONArray jsonarray = new JSONArray(jsonList);
			Assert.assertEquals(1, jsonarray.length());

			boolean result = aProcess.completeReiceiveTask(jsonarray.getJSONObject(0).get(processIdMapKey).toString(),
					jsonarray.getJSONObject(0).get(DMKS_IdTask).toString(),
					"{\"name\" : \"Indice\" , \"value\" : 0.8}");
			Assert.assertTrue(result);

			String jsonListTask = aProcess.listTask(testUser);
			jsonarray = new JSONArray(jsonListTask);
			checkJsonArray(jsonarray, 1, false);
			completeTask(jsonarray, true);

			Assert.assertEquals(0, new JSONArray(aProcess.listTask(testUser)).length());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void completeReiceiveTask_executeGateway_wayTwo() throws ExceptionGeneratorActiviti, IOException {
		try {
			MultipartFile multipartFile = getMockCommonsMultipartFile(new File(xmlPathInput + form_gateway_bpmn));
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			String jsonList = aProcess.dataReader();
			JSONArray jsonarray = new JSONArray(jsonList);
			Assert.assertEquals(1, jsonarray.length());

			boolean result = aProcess.completeReiceiveTask(jsonarray.getJSONObject(0).get(processIdMapKey).toString(),
					jsonarray.getJSONObject(0).get(DMKS_IdTask).toString(),
					"{\"name\" : \"Indice\" , \"value\" : 1.8}");

			Assert.assertTrue(result);
			Assert.assertEquals(0, new JSONArray(aProcess.listTask(testUser)).length());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void completeReiceiveTask_dmksExecution_DKMSDone() throws ExceptionGeneratorActiviti, IOException {
		try {
			MultipartFile multipartFile = getMockCommonsMultipartFile(new File(xmlPathInput + form_dkms_bpmn));
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			String jsonList = aProcess.dataReader();
			JSONArray jsonarray = new JSONArray(jsonList);
			Assert.assertEquals(1, jsonarray.length());

			boolean result = aProcess.completeReiceiveTask(jsonarray.getJSONObject(0).get(processIdMapKey).toString(),
					jsonarray.getJSONObject(0).get(DMKS_IdTask).toString(), null);

			Assert.assertTrue(result);
			Assert.assertEquals(0, new JSONArray(aProcess.listTask(testUser)).length());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
