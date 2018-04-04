package org.linagora.service.api;

import java.util.Map;

import org.linagora.exception.ExceptionGeneratorActiviti;

public interface ServiceNotification {

	public String sendNotification(String id) throws ExceptionGeneratorActiviti;

	public String createEventCalendar(Map<String, Object> map) throws ExceptionGeneratorActiviti, Exception;
}
