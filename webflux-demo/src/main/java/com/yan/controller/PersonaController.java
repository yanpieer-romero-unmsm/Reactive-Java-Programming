package com.yan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yan.model.Persona;
import com.yan.repo.IPersonaRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	
	private static final Logger Log = LoggerFactory.getLogger(PersonaController.class);

		@Autowired
		private IPersonaRepo repo;
		
		@GetMapping
		public Flux<Persona> listar(){
			return repo.listar();
		}
		
		@GetMapping("/{id}")
		public Mono<Persona> listarPorId(@PathVariable("id") Integer id){
			return repo.listarPorId(id);
		}
		
		@PostMapping
		public Mono<Persona> registrar(@RequestBody Persona per){
			return repo.registrar(per);
		}
		
		@PutMapping
		public Mono<Persona> modificar(@RequestBody Persona per){
			return repo.modificar(per);
		}
		
		@DeleteMapping("/{id}")
		public Mono<Void> eliminar(@PathVariable("id") Integer id){
			return repo.listarPorId(id)
						.flatMap(p -> repo.eliminar(p.getIdPersona()));
		}
}
