package com.yan.operador.transformacion;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Transformacion {
	
	private static final Logger Log = LoggerFactory.getLogger(Transformacion.class);
	
	public void map() {
		/*
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.map(p -> {
				p.setEdad(p.getEdad() + 10);
				return p;
			})
			.subscribe(p -> Log.info(p.toString()));
		*/
		Flux<Integer> fx = Flux.range(0, 10);
		Flux<Integer> fx2 = fx.map(x -> x + 10);
		fx2.subscribe(x -> Log.info("X: " + x));
	}
	
	public void flatMap() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.flatMap(p -> {
				p.setEdad(p.getEdad() + 10);
				return Mono.just(p);
				})
			.subscribe(p -> Log.info(p.toString()));
	}
	
	public void groupBy() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(1, "Josue", 22));
		personas.add(new Persona(1, "Cristofer", 23));

		Flux.fromIterable(personas)
			.groupBy(Persona::getIdPersona)
			.flatMap(idFlux -> idFlux.collectList())
			.subscribe(x -> Log.info(x.toString()));
	
	}

}
