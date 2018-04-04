package org.linagora.test.activiti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
public class ActivitiProcessDeployTest {

	private static ConfigurableApplicationContext application;

	private final static String xmlPathInput = "src/test/resources/inputXml/";
	private final static String xmlPathOutput = "src/test/resources/outputXml/";

	private final static String bpmn_valid = "test_bpmn_valid";
	private final static String parse_bpmn_valid = "Parse_test_bpmn_valid.bpmn20.xml";

	private final static String bpmn_wrong_xml = "test_bpmn_wrong_xml";
	private final static String bpmn_error_wrong_id = "test_bpmn_error_wrongId";
	private final static String no_xml_file = "test_no_xml_file";
	private final static String bpmn_no_executable = "test_bpmn_no_executable";

	private final static ActivitiProcess aProcess = new ActivitiProcess();

	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		application = SpringApplication.run(ApplicationWorkflowReader.class);
		changeSystemOutputTest();
	}

	@AfterClass
	public static void tearDown() {
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

	public void checkActivitiDao(String jsonActiviti, String fileName, boolean isExecute) {
		ActivitiDAO activitiData = new Gson().fromJson(jsonActiviti, ActivitiDAO.class);

		Assert.assertEquals(fileName, activitiData.getProcessId());
		Assert.assertTrue(activitiData.getFile().getAbsolutePath().contains(fileName));
		if (isExecute)
			Assert.assertNotNull(activitiData.getIdNumber());
		else
			Assert.assertNull(activitiData.getIdNumber());
	}

	@Test
	public void initBpmnIoToActiviti_BpmnExecution_IsOnlyParsed() throws ExceptionGeneratorActiviti, IOException {
		try {
			boolean isExecuted = false;

			File file = new File(xmlPathInput + bpmn_valid);
			MultipartFile multipartFile = getMockCommonsMultipartFile(file);
			String myActivitiJson = aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			checkActivitiDao(myActivitiJson, bpmn_valid, isExecuted);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void initBpmnIoToActiviti_BpmnExecutionStart_IsExecuted() throws ExceptionGeneratorActiviti, IOException {
		try {
			boolean isExecuted = true;

			File file = new File(xmlPathInput + bpmn_valid);
			MultipartFile multipartFile = getMockCommonsMultipartFile(file);
			String myActivitiJson = aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			checkActivitiDao(myActivitiJson, bpmn_valid, isExecuted);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void initBpmnIoToActiviti_BpmnExecutionErrorXml_UnableToParse()
			throws ExceptionGeneratorActiviti, IOException {
		try {
			boolean isExecuted = true;
			File file = new File(xmlPathInput + bpmn_wrong_xml);
			MultipartFile multipartFile = getMockCommonsMultipartFile(file);

			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e.toString().contains("javax.xml.transform.TransformerException"));
		}
	}

	@Test
	public void initBpmnIoToActiviti_BpmnExecutionNoXml_UnableToParse() throws ExceptionGeneratorActiviti, IOException {
		try {
			boolean isExecuted = true;

			File file = new File(xmlPathInput + no_xml_file);
			MultipartFile multipartFile = getMockCommonsMultipartFile(file);
			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e.toString().contains("javax.xml.transform.TransformerException"));
		}
	}

	@Test
	public void initBpmnIoToActiviti_ErrorInBpmnId_UnableToExecute() throws ExceptionGeneratorActiviti, IOException {
		try {
			boolean isExecuted = true;
			File file = new File(xmlPathInput + bpmn_error_wrong_id);
			MultipartFile multipartFile = getMockCommonsMultipartFile(file);

			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e.toString().contains("no processes deployed with key"));
		}
	}

	@Test
	public void initBpmnIoToActiviti_BpmnNoExecutable_UnableToExecute() throws ExceptionGeneratorActiviti, IOException {
		try {
			boolean isExecuted = true;
			File file = new File(xmlPathInput + bpmn_no_executable);
			MultipartFile multipartFile = getMockCommonsMultipartFile(file);

			aProcess.initBpmnIoToActiviti(multipartFile, isExecuted);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e.toString().contains("activiti-process-definition-not-executable"));
		}
	}

	@Test
	public void saveBpmn_SaveBpmnInRepositoryService_IsDeploy() throws ExceptionGeneratorActiviti, IOException {
		try {
			File file = new File(xmlPathOutput + parse_bpmn_valid);
			String processid = aProcess.saveBpmn(new ActivitiDAO("test_bpmn_valid", file));

			Assert.assertNotNull(processid);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void saveAndExecute_NoParsedValidXMml_ProcessIsDeploy() throws ExceptionGeneratorActiviti, IOException {
		try {
			File file = new File(xmlPathInput + bpmn_valid);
			String processId = aProcess.saveAndExecute(new ActivitiDAO("test_bpmn_valid", file));
			Assert.assertNotNull(processId);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
