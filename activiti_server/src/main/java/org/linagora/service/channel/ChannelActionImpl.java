package org.linagora.service.channel;

import java.util.Map;

import org.linagora.exception.ExceptionGeneratorActiviti;
import org.linagora.service.api.ServiceAction;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ChannelActionImpl implements ServiceAction {

	@Override
	public String generateBpmn(MultipartFile file) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveBpmn(MultipartFile file) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeBpmn(String nameProcess) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listTask(String email) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean completeTask(Map<String, Object> json) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeReceiveTask(String processId, String receiveTaskId) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return false;
	}

}