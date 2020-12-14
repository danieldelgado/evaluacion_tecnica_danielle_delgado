package com.bcp.reactive.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/", "index" })
public class FrontWwebController {

	@RequestMapping("/")
	public String greet() {
		return "index.html";
	}

}
