package org.linagora.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class NotificationListener implements TaskListener {

	private static final long serialVersionUID = 1553996028710887418L;

	@Override
	public void notify(DelegateTask delegateTask) {

		// TODO Notification user before action
		return;
	}
}