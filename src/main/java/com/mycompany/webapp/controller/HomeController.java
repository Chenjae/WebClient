package com.mycompany.webapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.PagerAndBoards;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@RequestMapping("/")
	public String content(Model model) {
		log.info("실행");
		return "content";
	}
	
	@RequestMapping("/test1")
	public String test1(Model model, HttpSession session) {
		log.info("실행");
		WebClient webClient = WebClient.create();		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("mid", "user");
		map.add("mpassword", "12345");
		Auth auth = webClient
			.post()
			.uri("http://localhost/member/login1")
			.body(BodyInserters.fromFormData(map))
			.retrieve()
			.bodyToMono(Auth.class)
			.block();
		session.setAttribute("auth", auth);
		model.addAttribute("auth", auth);
		return "test1";
	}
	
	@RequestMapping("/test2")
	public String test2(Model model, HttpSession session) {
		log.info("실행");
		Auth auth = (Auth) session.getAttribute("auth");
		WebClient webClient = WebClient.create();		
		PagerAndBoards pagerAndBoards = webClient
			.get()
			.uri("http://localhost/board/list?pageNo=1")
			.header("Authorization", "Bearer " + auth.getJwt())
			.retrieve()
			.bodyToMono(PagerAndBoards.class)
			.block();
		model.addAttribute("pager", pagerAndBoards.getPager());
		model.addAttribute("boards", pagerAndBoards.getBoards());
		return "test2";
	}
}










