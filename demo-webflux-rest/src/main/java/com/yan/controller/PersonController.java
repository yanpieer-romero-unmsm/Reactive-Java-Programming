package com.yan.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yan.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	/*
	@Autowired
	private PersonService service;
	*/
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Person>>> findAll(){
		//Flux<Person> list = service.findAll();
		List<Person> list = Arrays.asList(new Person(1, "Yanpieer"), new Person(2, "Josue"));
		
		Flux<Person> fxPerson = Flux.fromIterable(list);

		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fxPerson));
	}
}
