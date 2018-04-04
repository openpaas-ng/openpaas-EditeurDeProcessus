package org.linagora.service.api;

import java.util.Map;

import org.linagora.exception.ExceptionGeneratorActiviti;
import org.springframework.web.multipart.MultipartFile;

public interface ServiceAction {

	public String generateBpmn(MultipartFile file) throws ExceptionGeneratorActiviti;

	public String saveBpmn(MultipartFile file) throws ExceptionGeneratorActiviti;

	public String executeBpmn(String nameProcess) throws ExceptionGeneratorActiviti;

	public String listTask(String email) throws ExceptionGeneratorActiviti;

	public boolean completeTask(Map<String, Object> json) throws ExceptionGeneratorActiviti;

	public boolean executeReceiveTask(String processId, String receiveTaskId) throws ExceptionGeneratorActiviti;

}
