package com.jacaranda.springProjecToWork.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jacaranda.springProjecToWork.model.Element;
import com.jacaranda.springProjecToWork.service.ElementService;

@Controller
public class ElementController {
	@Autowired
	private ElementService service;
	
	@GetMapping({"/","/home"})
	public String inicio() {
		return "home";
	}

	@GetMapping("listElement")
	public String getElements(Model model,@RequestParam("pageNumber") 
	Optional<Integer> pageNumber, 
	@RequestParam("sizeNumber") Optional<Integer> sizeNumber,
	@RequestParam("sortField") Optional<String> sortField,
	@RequestParam("stringFind") Optional<String> stringFind) { 
		
		Page<Element> page = service.findAll(pageNumber.orElse(1),sizeNumber.orElse(10), sortField.orElse("id"), stringFind.orElse(null)); 
		
		model.addAttribute("currentPage",pageNumber.orElse(1)); 
		model.addAttribute("totalPages",page.getTotalPages()); 
		model.addAttribute("totalItems",page.getTotalElements()); 
		model.addAttribute("sortField", sortField.orElse("id"));
		model.addAttribute("stringFind", stringFind.orElse(null));
		model.addAttribute("listElement", page.getContent());
		
		return"listElement";

	}

	@GetMapping("addElement")
	public String addElement(Model model) {

		Element element = new Element();

		model.addAttribute("element", element);
		return "addElement";
	}

	@PostMapping("/addElement/submit")
	public String addSumitElement(@ModelAttribute("element") Element element) {
		service.add(element);
		return "redirect:/listElement";
	}

	@GetMapping("/delElement")
	public String delElement(Model model, @RequestParam(name = "id") String id) {
		Element element = service.findById(Integer.parseInt(id));

		model.addAttribute("element", element);
		return "delElement";
	}

	@PostMapping("/delElement/submit")
	public String delSumitElement(@ModelAttribute("element") Element element) {
		service.delete(element);
		return "redirect:/listElement";
	}

	@GetMapping("/editElement")
	public String editElement(Model model, @RequestParam(name = "id", required = false, defaultValue = "") String id) {

		Element element = service.findById(Integer.parseInt(id));

		model.addAttribute("element", element);
		return "editElement";
	}

	@PostMapping("/editElement/submit")
	public String editSumitElement(@ModelAttribute("element") Element element) {
		service.save(element);
		return "redirect:/listElement";
	}
}
