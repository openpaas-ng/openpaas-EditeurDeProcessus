package org.linagora.service.rest;

import java.util.Map;

import org.linagora.activiti.ActivitiProcess;
import org.linagora.exception.ExceptionGeneratorActiviti;
import org.linagora.service.api.ServiceAction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/action")
public class ServiceActionImpl implements ServiceAction {

	/**
	 * This web service parse an XML to be readable for Activiti
	 *
	 * @param String
	 *            xml The XML of BPMN to execute with Activiti
	 *
	 * @throws ExceptionGeneratorActiviti
	 */

	/* Save and Execution */
	private ActivitiProcess activiti = new ActivitiProcess();

	@RequestMapping(value = "/parse/save", method = RequestMethod.POST)
	public String saveBpmn(@RequestParam("file") MultipartFile file) throws ExceptionGeneratorActiviti {
		return activiti.initBpmnIoToActiviti(file, false);
	}

	@RequestMapping(value = "/parse/execute", method = RequestMethod.POST)
	public String generateBpmn(@RequestParam("file") MultipartFile file) throws ExceptionGeneratorActiviti {
		return activiti.initBpmnIoToActiviti(file, true);
	}

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public String executeBpmn(@RequestParam("nameProcess") String nameProcess) throws ExceptionGeneratorActiviti {
		return activiti.executeBpmn(nameProcess);
	}

	/* Manage task side*/
	@RequestMapping(value = "/task/list", method = RequestMethod.POST)
	public String listTask(@RequestParam("email") String email) throws ExceptionGeneratorActiviti {
		return activiti.listTask(email);
	}

	@RequestMapping(value = "/task/complet", method = RequestMethod.POST)
	public boolean completeTask(@RequestBody Map<String, Object> map) throws ExceptionGeneratorActiviti {
		return activiti.completeUserTask(map);
	}

	@RequestMapping(value = "/task/receive", method = RequestMethod.POST)
	public boolean executeReceiveTask(@RequestParam("processId") String processId,
			@RequestParam("taskId") String receiveTaskId) throws ExceptionGeneratorActiviti {
		return activiti.completeReiceiveTask(processId, receiveTaskId, null);
	}

	@RequestMapping(value = "/task/receive/json", method = RequestMethod.POST)
	public boolean executeReceiveTask(@RequestParam("processId") String processId,
			@RequestParam("taskId") String receiveTaskId, @RequestParam("json") String json) throws ExceptionGeneratorActiviti {
		return activiti.completeReiceiveTask(processId, receiveTaskId, json);
	}
	
	/*All data*/
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String allData() throws ExceptionGeneratorActiviti {
		return activiti.dataReader();
	}
}