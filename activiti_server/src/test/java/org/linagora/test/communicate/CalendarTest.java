package org.linagora.test.communicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.linagora.communicate.CalendarUtility;
import org.springframework.boot.test.context.TestComponent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.Version;

@TestComponent
public class CalendarTest {

	private final static String errorMsgNull = " is null";

	private final static String summaryEvent = "Test event";
	private final static String organizerName = "Organizer";
	private final static String location = "Somewhere";
	private final static int daysAdded = 3;
	private final static List<String> attendeeList = Arrays.asList("attendee1@test.com", "attendee2@test.com",
			"attendee3@test.com");

	@Test
	public void createCalendar_LocationNull_LocationIsEmpty() throws Exception {
		Calendar calendarValidity = CalendarUtility.createCalendar(summaryEvent, attendeeList, organizerName, null,
				daysAdded);
		checkCalendar(calendarValidity, true);
	}

	@Test
	public void createCalendar_OrganizerNameNull_ExceptionThrown() throws Exception {
		try {
			CalendarUtility.createCalendar(summaryEvent, attendeeList, null, location, daysAdded);
			Assert.fail("Should throw exception when organizer event is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains(errorMsgNull));
		}
	}

	@Test
	public void createCalendar_AttendeeListEmpty_ExceptionThrown() throws Exception {
		try {
			List<String> attendeeListEmpty = new ArrayList<String>();
			CalendarUtility.createCalendar(summaryEvent, attendeeListEmpty, organizerName, location, daysAdded);
			Assert.fail("Should throw exception when name event is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("Attendee List can't be empty"));
		}
	}

	@Test
	public void createCalendar_AttendeeListNull_ExceptionThrown() throws Exception {
		try {
			CalendarUtility.createCalendar(summaryEvent, null, organizerName, location, daysAdded);
			Assert.fail("Should throw exception when name event is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains(errorMsgNull));
		}
	}

	@Test
	public void createCalendar_EventNameNull_ExceptionThrown() throws Exception {
		try {
			CalendarUtility.createCalendar(null, attendeeList, organizerName, location, daysAdded);
			Assert.fail("Should throw exception when name event is null");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains(errorMsgNull));
		}
	}

	@Test
	public void createCalendar_ParamValid_isCreate() throws Exception {
		Calendar calendarValidity = CalendarUtility.createCalendar(summaryEvent, attendeeList, organizerName, location,
				daysAdded);

		checkCalendar(calendarValidity, false);
	}

	private boolean checkMailTo(List<String> attendeeList, String vcalAttendee) {
		for (String attendeeMail : attendeeList)
			if (vcalAttendee.contains("mailto:" + attendeeMail))
				return true;
		return false;
	}

	private void checkCalendar(Calendar calendarValidity, boolean isLocationEmpty) {
		Assert.assertNotNull(calendarValidity);
		Assert.assertEquals(Version.VERSION_2_0, calendarValidity.getVersion());
		for (Iterator<Component> comps = calendarValidity.getComponents().iterator(); comps.hasNext();) {
			Component component = comps.next();
			Assert.assertEquals("VEVENT", component.getName());

			Assert.assertTrue(component.getProperties("SUMMARY").toString().contains(summaryEvent));
			Assert.assertTrue(component.getProperties("ORGANIZER").toString().contains(organizerName));
			if (isLocationEmpty)
				Assert.assertTrue(component.getProperties("LOCATION").isEmpty());
			else
				Assert.assertTrue(component.getProperties("LOCATION").toString().contains(location));

			// Auto information
			Assert.assertNotNull(component.getProperties("TRANSP"));
			Assert.assertNotNull(component.getProperties("UID"));
			Assert.assertNotNull(component.getProperties("DTEND"));
			Assert.assertNotNull(component.getProperties("DTSTART"));

			for (Iterator<Property> props = component.getProperties().iterator(); props.hasNext();) {
				Property property = props.next();
				if (property.getName().equals("ATTENDEE"))
					Assert.assertEquals(true, checkMailTo(attendeeList, property.getValue().toString()));
			}
		}
	}
}
