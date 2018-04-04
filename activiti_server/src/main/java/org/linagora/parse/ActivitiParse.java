package org.linagora.parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.linagora.dao.ActivitiDAO;
import org.linagora.utility.LoggerManager;
import org.springframework.web.multipart.MultipartFile;

public class ActivitiParse {

	final static Logger logger = Logger.getLogger(ActivitiParse.class);

	private static final String DEFAULT_NAME_PARSING = "Parse_";
	private static final String EXTENSION_BPMN = ".bpmn20.xml";

	private static final String DEFAULT_XSL_PATH = "/parse/";
	private static final String DEFAULT_XSL_NAME = "ActivitiXLS.xml";

	private String xslPath;
	private String xslName;

	public ActivitiParse() {
		this.xslPath = DEFAULT_XSL_PATH;
		this.xslName = DEFAULT_XSL_NAME;
	}

	public ActivitiParse(String xslPath, String xslName) {
		if (xslPath == null)
			this.xslPath = DEFAULT_XSL_PATH;
		else
			this.xslPath = xslPath;

		if (xslName == null)
			this.xslName = DEFAULT_XSL_NAME;
		else
			this.xslName = xslPath;
	}

	public ActivitiDAO parseXMLToActivitiExecutable(MultipartFile multipart) throws Exception {
		try {
			if (multipart == null)
				throw new Exception("The parsed file can't be null");

			InputStream xslFile = getClass().getResourceAsStream(xslPath + xslName);
			File xmlFile = getFileFromeMultipartFile(multipart);

			// TODO NEED TO ADD MONGO TO SAVE BPMN
			// for the moment in tmp for the moment
			File xmlFileAfterDone = new File(System.getProperty("java.io.tmpdir"),
					DEFAULT_NAME_PARSING + multipart.getOriginalFilename() + EXTENSION_BPMN);

			xmlFileAfterDone = parseBpmnFile(xslFile, xmlFile, xmlFileAfterDone);
			xmlFile.delete();

			if (xmlFileAfterDone != null)
				return new ActivitiDAO(multipart.getOriginalFilename(), xmlFileAfterDone);
		} catch (IOException | TransformerException e) {
			throw e;
		}
		return null;
	}

	private File getFileFromeMultipartFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipart.getBytes());
		fos.close();
		convFile.deleteOnExit();
		return convFile;
	}

	private File parseBpmnFile(InputStream xslFile, File xmlFile, File xmlFileAfterDone)
			throws TransformerException, IOException {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(xslFile));
			transformer.transform(new StreamSource(xmlFile), new StreamResult(xmlFileAfterDone));
			return xmlFileAfterDone;
		} catch (Exception e) {
			throw e;
		}
	}

	public String generateParsedFileName(String fileName) {
		if (fileName == null)
			return null;
		return DEFAULT_NAME_PARSING + fileName + EXTENSION_BPMN;
	}
}
