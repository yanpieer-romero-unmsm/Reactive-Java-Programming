package com.yan.operador.creacion;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;

import io.reactivex.Observable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Creacion {
	
	private static final Logger Log = LoggerFactory.getLogger(Creacion.class);
	
	public void justFrom() {
		Mono.just(new Persona(1, "Yanpieer", 23));
		//Flux.fromIterable(coleccion);
		//Observable.just(item);
	}
	
	public void empty() {
		Mono.empty();
		Flux.empty();
		Observable.empty();
	}
	
	public void range() {
		Flux.range(0, 3)
			.doOnNext(i -> Log.info("i : " + i))
			.subscribe();
	}
	
	public void repeat() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		
		Flux.fromIterable(personas)
			.repeat(3)
			.subscribe(p -> Log.info(p.toString()));
		
		/*
		Mono.just(personas)
			.repeat(3)
			.subscribe(p -> Log.info(p.toString()));
		*/
	}
}
