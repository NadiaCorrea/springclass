package com.jacaranda.springProjecToWork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.springProjecToWork.model.Element;

public interface ElementRepository extends JpaRepository<Element, Integer> {
	
	public Page<Element> findByNameLike(String keyword, Pageable pageable); 

}
