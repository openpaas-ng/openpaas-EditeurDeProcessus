package org.linagora.service.rest;

import java.util.Map;

import org.linagora.communicate.CalendarUtility;
import org.linagora.communicate.OpenPaasConnector;
import org.linagora.dao.ActionActiviti;
import org.linagora.service.api.ServiceNotification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.fortuna.ical4j.model.Calendar;

@RestController
@RequestMapping("/notification")
public class ServiceNotificationsImpl implements ServiceNotification {

	public final OpenPaasConnector opc = new OpenPaasConnector();

	@RequestMapping("/send")
	public String sendNotification(@RequestParam(value = "id") String id) {
		return id;
	}

	/**
	 * 
	 * Json Request Format : { "name": "myEvent", "attendee":
	 * ["user01@open-paas.org", "user10@open-paas.org"], "location":
	 * "inToulouse" }
	 **/
	@RequestMapping(value = "/calendar/create", method = RequestMethod.POST)
	public String createEventCalendar(@RequestBody Map<String, Object> map) throws Exception {
		try {
			Calendar cal = CalendarUtility.createCalendarFromJson(map, opc.getOpenPaasConfig().getOpenPaasUser().getUsername());
			return opc.wsCallGenerator(ActionActiviti.CALENDAR, cal);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}