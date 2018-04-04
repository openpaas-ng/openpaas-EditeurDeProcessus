package org.linagora.service.rest;

import org.linagora.dao.VariableData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ServiceTestFakeDemo {

	@RequestMapping("/hello")
	public String test() {
		System.out.println("Hello World GET");
		return "Hello World GET";
	}

	@RequestMapping("/variable")
	public String variable() {
		VariableData res = new VariableData("Indice", "11");
		return res.generateJson();
	}

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public String testPost(@RequestBody String postPayload) {
		System.out.println("Hello World POST");
		System.out.println(postPayload);
		return "Hello World POST";
	}

	@RequestMapping(value = "/optimisation", method = RequestMethod.POST)
	public String optimisation(@RequestBody String postPayload) {
		System.out.println("Optimisation Web Service - POST");
		System.out.println("Json : " + postPayload);
		return postPayload;
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public String email(@RequestBody String postPayload) {
		System.out.println("Email Web Service - POST");
		System.out.println("Json : " + postPayload);
		return postPayload;
	}

	@RequestMapping(value = "/supervision", method = RequestMethod.POST)
	public String supervision(@RequestBody String postPayload) {
		System.out.println("Supervision Web Service - POST");
		System.out.println("Json : " + postPayload);
		return postPayload;
	}
}