package com.bilgeadam.hospital.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilgeadam.hospital.service.UserService;

@Controller
public class IndexController {
	
	static final String INDEX = "index";
	static final String REDIRECT_INDEX = "redirect:/index";
	static final String ALT_SAYFA = "altsayfa";
	static final String REDIRECT = "redirect:/";
	
	@GetMapping("/") // localhost:8080
	public String index(Model model) {
		model.addAttribute("adsoyad", "Dilara Uludağ");
		return INDEX;
	}
	
	@GetMapping("altsayfa") // localhost:8080/altsayfa
	public String altsayfa() {
		return ALT_SAYFA;
	}
	
	@GetMapping("locale") // localhost:8080/locale?language=en // soru işaretinden sonraki değişkenleri @RequestParam ile alırız
	public String locale(@RequestParam String language, HttpServletRequest request) {
		// dil değiştirmek için locale metodu çağrıldıktan sonra çağıran sayfaya geri dönmek amacıyla
		// aşağıdaki kodlar yazıldı
		String referer = request.getHeader("referer"); // referer : locale metodunun çağrıldığı sayfa
		String refPage = referer.substring(referer.lastIndexOf("/") + 1);
		
		return REDIRECT + refPage;
	}

}
