package com.yan.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yan.model.Persona;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonaRepoImpl implements IPersonaRepo{

	private static final Logger Log = LoggerFactory.getLogger(PersonaRepoImpl.class);
	
	@Override
	public Mono<Persona> registrar(Persona per) {
		Log.info(per.toString());
		return Mono.just(per);
	}

	@Override
	public Mono<Persona> modificar(Persona per) {
		Log.info(per.toString());
		return Mono.just(per);
	}

	@Override
	public Flux<Persona> listar() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer"));
		personas.add(new Persona(2, "Josue"));
		personas.add(new Persona(3, "Cristofer"));

		return Flux.fromIterable(personas);
	}

	@Override
	public Mono<Persona> listarPorId(Integer id) {
		return Mono.just(new Persona(1, "Yanpieer"));
	}

	@Override
	public Mono<Void> eliminar(Integer id) {
		return Mono.empty();
	}

}
