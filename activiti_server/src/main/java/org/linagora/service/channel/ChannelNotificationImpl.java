package org.linagora.service.channel;

import java.util.Map;

import org.linagora.exception.ExceptionGeneratorActiviti;
import org.linagora.service.api.ServiceNotification;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelNotificationImpl implements ServiceNotification{

	@Override
	public String sendNotification(String id) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createEventCalendar(Map<String, Object> map) throws ExceptionGeneratorActiviti {
		// TODO Auto-generated method stub
		return null;
	}

}