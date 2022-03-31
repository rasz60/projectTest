package com.project.init.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

	@RequestMapping("")
	public String message() {
		return "message/message_box";
	}
}
