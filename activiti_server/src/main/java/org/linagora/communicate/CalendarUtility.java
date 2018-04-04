package org.linagora.communicate;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.linagora.utility.DateManager;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

public class CalendarUtility {

	private static final String MAP_NAME = "name";
	private static final String MAP_LOCATION = "location";
	private static final String MAP_ATTENDEE = "attendee";
	private static final String MAP_DAYS = "days";

	private static final int DEFAULT_TIME_EVENT_HOURS = 2;
	private static final int DEFAULT_ADD_DAYS = 0;
	private static final int HOURS_DAY = 24;

	public static Calendar createCalendar(String name, List<String> attendeeList, String organizerName, String location,
			int daysAdded) throws IllegalArgumentException {

		checkerVariable(name, attendeeList, organizerName);
		// Default setting VCalendar
		Calendar calendar = new Calendar();
		calendar.getProperties().add(Version.VERSION_2_0);
		DateTime start;
		DateTime end;
		Date date = new Date();
		// Initialize date VEvent
		if (daysAdded > 0) {
			start = new DateTime(DateManager.addHours(date, HOURS_DAY * daysAdded));
			end = new DateTime(DateManager.addHours(date, HOURS_DAY * daysAdded + DEFAULT_TIME_EVENT_HOURS));
		} else {
			start = new DateTime(date);
			end = new DateTime(DateManager.addHours(date, DEFAULT_TIME_EVENT_HOURS));
		}

		CalendarBuilder builder = new CalendarBuilder();
		TimeZoneRegistry registry = builder.getRegistry();
		TimeZone tz = registry.getTimeZone("Europe/Berlin"); // TODO
		start.setTimeZone(tz);
		end.setTimeZone(tz);

		// Initialize VEvent
		VEvent event = new VEvent(start, end, name);
		Uid uid = new Uid(UUID.randomUUID().toString());
		event.getProperties().add(uid);
		event.getProperties().add(Transp.OPAQUE);
		if (location != null)
			event.getProperties().add(new Location(location));

		// Generate user to the event
		Organizer organizer = new Organizer(URI.create("mailto:" + organizerName));
		organizer.getParameters().add(new Cn(organizerName));

		event.getProperties().add(organizer);
		for (String attendeeMail : attendeeList) {
			Attendee att = new Attendee(URI.create("mailto:" + attendeeMail));
			att.getParameters().add(Role.REQ_PARTICIPANT);
			att.getParameters().add(new Cn(attendeeMail));
			att.getParameters().add(new Rsvp(true));
			att.getParameters().add(PartStat.NEEDS_ACTION);
			event.getProperties().add(att);
		}
		calendar.getComponents().add(event);
		return calendar;
	}

	private static void checkerVariable(String name, List<String> attendeeList, String organizerName)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Calendar Event Name is null");
		} else if (attendeeList == null) {
			throw new IllegalArgumentException("Calendar Attendee List is null");
		}else if(attendeeList.isEmpty()){
			throw new IllegalArgumentException("Attendee List can't be empty");
		} else if (organizerName == null) {
			throw new IllegalArgumentException("Calendar Organizer Name is null");
		}
	}

	public static Calendar createCalendarFromJson(Map<String, Object> map, String organizerName) throws Exception {
		Calendar cal;
		try {
			String name = (String) map.get(MAP_NAME);
			String location = (String) map.get(MAP_LOCATION);
			List<String> attendeeList = (List<String>) map.get(MAP_ATTENDEE);

			int hoursAdded;
			try {
				hoursAdded = (int) map.get(MAP_DAYS);
			} catch (Exception e) {
				hoursAdded = DEFAULT_ADD_DAYS;
			}

			if (name == null || attendeeList == null) {
				throw new Exception("An event name or attendee list is needed");
			}

			cal = createCalendar(name, attendeeList, organizerName, location, hoursAdded);
			return cal;
		} catch (Exception e) {
			throw e;
		}
	}

}
