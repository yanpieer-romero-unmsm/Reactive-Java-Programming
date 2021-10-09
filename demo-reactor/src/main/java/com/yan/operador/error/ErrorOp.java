package com.yan.operador.error;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;
import com.yan.operador.combinacion.Combinacion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorOp {

	private static final Logger Log = LoggerFactory.getLogger(Combinacion.class);
	
	public void retry() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.concatWith(Flux.error(new RuntimeException("UN ERROR")))
			.retry(1)
			.doOnNext(x -> Log.info(x.toString()))
			.subscribe();

	}
	
	public void errorReturn() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.concatWith(Flux.error(new RuntimeException("UN ERROR")))
			.onErrorReturn(new Persona(0, "XYZ", 99))
			.subscribe(x -> Log.info(x.toString()));

	}

	public void errorResume() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.concatWith(Flux.error(new RuntimeException("UN ERROR")))
			.onErrorResume(e -> Mono.just(new Persona(0, "XYZ", 99)))
			.subscribe(x -> Log.info(x.toString()));

	}
	
	public void errorMap() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.concatWith(Flux.error(new RuntimeException("UN ERROR")))
			.onErrorMap(e -> new InterruptedException(e.getMessage()))
			.subscribe(x -> Log.info(x.toString()));

	}
}
