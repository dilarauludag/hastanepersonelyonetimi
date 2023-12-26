package com.bilgeadam.hospital.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bilgeadam.hospital.entity.Personel;
import com.bilgeadam.hospital.service.FileStorageService;
import com.bilgeadam.hospital.service.PersonelService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PersonelController {

	private final PersonelService personelService;
	private final FileStorageService fileService;

	static final String PERSONELS = "personels";
	static final String REDIRECT_PERSONELS = "redirect:/personels?pageNo=1&recordCount=10&sortBy=id&direction=asc&keyword=";
	static final String PERSONEL_DETAIL = "personeldetail";
	static final String NEW_PERSONEL = "newpersonel";
	static final String UPDATE_PERSONEL = "updatepersonel";
	
	@GetMapping("/updatepersonel/{id}")
	public String updatepersonel(@PathVariable Long id, Model model) {

		Personel existPersonel = personelService.getById(id);

		//Map<Integer, String> kategoriler = categoryService.kategoriMap();
		//Map<Integer, String> kategoriler = categoryService.kategoriMap();
		
		//model.addAttribute("departments", departments);
		//model.addAttribute("titles", titles);
		model.addAttribute("personel", existPersonel);
		//model.addAttribute("selectedDepartment", existPersonel.getDepartment().getId());
		//model.addAttribute("selectedTitle", existPersonel.getTitle().getId());

		return UPDATE_PERSONEL;
	}

	@PostMapping("/updatepersonel/{id}")
	public String updatePersonel(@PathVariable Long id, @Valid @ModelAttribute Personel personel, BindingResult result,
			Model model, @RequestParam("img") MultipartFile img) {

		if (result.hasErrors()) {

			//Map<Integer, String> departments = categoryService.kategoriMap();

			//model.addAttribute("departments", departments);
			model.addAttribute("personel", personel);
			//model.addAttribute("selectedDepartment", personel.getDepartment());
			
			return UPDATE_PERSONEL;
		}
		
		if(img.getOriginalFilename() != null) {
			try {
				fileService.save(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			personel.setImage(img.getOriginalFilename());
		}
		
		personelService.update(id, personel);
		
		return REDIRECT_PERSONELS;
	}

	@GetMapping("/search")
	public String search(String keyword) {
		return REDIRECT_PERSONELS + keyword; 
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/personels")
	public String personels(Model model, @RequestParam Integer pageNo, @RequestParam Integer recordCount, 
			@RequestParam String sortBy,@RequestParam String direction, String keyword ) {
		pageNo = pageNo == null ? 1 : pageNo;
		recordCount = recordCount == null ? 10 : recordCount;
		sortBy = sortBy.isEmpty() ? "id" : sortBy;
		direction = direction.isEmpty()? "asc" : direction;
		
		//sortBy.equals("name") // NullPointerException
		model.addAttribute("nameLabel", "Adı");
		
		if("name".equals(sortBy)) {
			if("desc".equals(direction)) {
				model.addAttribute("nameLabel", "Adı ^");
				model.addAttribute("nameSortDirection", "asc");
			}else {
				model.addAttribute("nameLabel", "Adı v");
				model.addAttribute("nameSortDirection", "desc");
			}
		}
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("recordCount", recordCount);
		
		Page<Personel> page = personelService.personels(pageNo, recordCount, sortBy, direction, keyword);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		model.addAttribute("keyword", keyword);
		
		List<Personel> liste = page.getContent(); 
		model.addAttribute("personels", liste);
		return PERSONELS;
	}

	@GetMapping("/deletepersonel/{id}")
	public String deletePersonel(@PathVariable Long id) {
		personelService.deleteById(id);
		return REDIRECT_PERSONELS;
	}

	@GetMapping("/personeldetail/{id}")
	public String personelDetail(@PathVariable Long id, Model model) {
		Personel p = personelService.getById(id);

		model.addAttribute("personel", p);
		return PERSONEL_DETAIL;
	}

	@GetMapping("/newpersonel") // personel bilgilerinin girişi yapılacak sayfanın boş halde açılmasını sağlar
	public String newPersonel(Model model) {

		//Map<Integer, String> kategoriler = categoryService.kategoriMap();

		//model.addAttribute("kategoriler", kategoriler);
		model.addAttribute("personel", new Personel());

		return NEW_PERSONEL;
	}

	@PostMapping("/newpersonel")
	public String saveNewPersonel(@Valid @ModelAttribute("personel") Personel personel, BindingResult result,
			@RequestParam("img") MultipartFile img, Model model) {
		if (result.hasErrors()) {
			//Map<Integer, String> kategoriler = categoryService.kategoriMap();

			//model.addAttribute("kategoriler", kategoriler);
			personel.setImage(img.getOriginalFilename());
			model.addAttribute("img", img);
			model.addAttribute("personel", personel);
			//model.addAttribute("selectedDepartment", personel.getDepartment());
			return NEW_PERSONEL;
		}

		try {
			fileService.save(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String resim = personel.getImage();

		personel.setImage(resim.substring(resim.lastIndexOf("\\") + 1));

		personelService.save(personel);

		return REDIRECT_PERSONELS;
	}

	@GetMapping("img/{fileName:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String fileName) {

		Resource file = fileService.load(fileName);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
