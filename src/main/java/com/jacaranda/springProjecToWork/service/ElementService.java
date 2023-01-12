package com.jacaranda.springProjecToWork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jacaranda.springProjecToWork.model.Element;
import com.jacaranda.springProjecToWork.repository.ElementRepository;

@Service
public class ElementService {
	@Autowired
	private ElementRepository repository;

	public Element add(Element s) {
		return repository.save(s);
	}

	public Page<Element> findAll(int pageNum, int pageSize, String sortField, String stringFind) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortField).ascending());

		if(stringFind == null) {
			return repository.findAll(pageable);//busca 
		}else {
			return repository.findByNameLike("%" + stringFind + "%", pageable); //se neceista añadir % antes y despues del keyword
		}
	
	}

	public Element findById(int id) {
		return repository.findById(id).orElse(null);
	}

	public Element save(Element e) {
		return repository.save(e);
	}

	public void delete(Element e) {
		repository.delete(e);
	}
}
