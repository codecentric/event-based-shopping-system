package de.codecentric.ebss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/error")
public class ExceptionController {

	@ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletRequest request) {
        return "redirect:movies";
    }
}