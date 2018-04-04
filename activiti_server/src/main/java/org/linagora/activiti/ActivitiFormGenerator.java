package org.linagora.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.task.Task;
import org.linagora.dao.openpaas.form.Formly;
import org.linagora.dao.openpaas.form.FormlyType;
import org.linagora.dao.openpaas.form.TemplateOptions;
import org.linagora.exception.ExceptionGeneratorActiviti;

public class ActivitiFormGenerator {

	private final static String ENUM_INFORMATION_VALUES = "values";
	private final static String TASK_ATTRIBUTE = "taskId";
	// Empty for not appear in form
	private final static String TASK_VALUE_HIDDEN_FORM = "";

	public static List<Formly> generateForm(Task task, FormData formData) throws ExceptionGeneratorActiviti {
		List<Formly> formlyList = new ArrayList<Formly>();

		for (FormProperty propertyForm : formData.getFormProperties()) {
			FormlyType propertyType = getFormType(propertyForm.getType());
			TemplateOptions templateOptions = new TemplateOptions(propertyForm, propertyType);

			if (propertyForm.getName() != null)
				manageGvariableLabel(task.getExecutionId(), templateOptions);

			if (propertyType.equals(FormlyType.DATE) || propertyType.equals(FormlyType.CUSTOM_TYPE)) {
				// TODO NOT YET SUPPORTED
				throw new ExceptionGeneratorActiviti("Unable to parse the Form for Property");
			} else if (propertyType.equals(FormlyType.ENUM)) {
				templateOptions = makeTemplateOptions(templateOptions, propertyForm);
			}
			formlyList.add(new Formly(propertyForm.getId(), propertyType.getTypeFormly(), templateOptions,
					propertyForm.getValue()));
		}

		TemplateOptions templateOptionsHidden = new TemplateOptions(TASK_VALUE_HIDDEN_FORM,
				FormlyType.HIDDEN.getTypeFormly());
		// Used for have some information
		templateOptionsHidden.setPlaceholder(task.getName());
		formlyList.add(
				new Formly(TASK_ATTRIBUTE, FormlyType.STRING.getTypeFormly(), templateOptionsHidden, task.getId()));

		return formlyList;
	}

	private static TemplateOptions manageGvariableLabel(String taskExecutionId, final TemplateOptions templateOptions) {
		Map<String, Object> globalVariableMap = ProcessEngines.getDefaultProcessEngine().getRuntimeService()
				.getVariables(taskExecutionId);
		for (String key : globalVariableMap.keySet()) {
			String keyVarName = "${" + key + "}";
			if (templateOptions.getLabel().contains(keyVarName)) {
				String keyVarValue = globalVariableMap.get(key).toString();

				templateOptions.setLabel(templateOptions.getLabel().replace(keyVarName, keyVarValue));
				templateOptions.setPlaceholder(templateOptions.getPlaceholder().replace(keyVarName, keyVarValue));
			}
		}
		return templateOptions;
	}

	private static FormlyType getFormType(FormType type) {
		if (type != null)
			return FormlyType.valueOf(type.getName().toUpperCase());
		return FormlyType.CUSTOM_TYPE;
	}

	private static TemplateOptions makeTemplateOptions(final TemplateOptions templateOptions, FormProperty propertyForm)
			throws ExceptionGeneratorActiviti {
		try {
			Map<String, String> map = (Map<String, String>) propertyForm.getType()
					.getInformation(ENUM_INFORMATION_VALUES);
			for (Map.Entry<String, String> entry : map.entrySet()) {

				templateOptions.addOption(entry.getValue(), entry.getKey());
			}
			return templateOptions;
		} catch (Exception e) {
			throw new ExceptionGeneratorActiviti("Unable to cast enum value");
		}
	}
}
