package com.yan.operador.filtrado;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;

import reactor.core.publisher.Flux;

public class Filtrado {
	
	public static final Logger Log = LoggerFactory.getLogger(Filtrado.class);
	
	public void filter() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));

		Flux.fromIterable(personas)
			.filter(p -> p.getEdad() > 22)
			.subscribe(x -> Log.info(x.toString()));
	
	}
	
	public void distinct() {
		Flux.fromIterable(List.of(1, 1, 2, 2))
		.distinct()
		.subscribe(x -> Log.info(x.toString()));
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(1, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));

		Flux.fromIterable(personas)
			.distinct()
			.subscribe(x -> Log.info(x.toString()));
	
	}
	
	public void take() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));

		Flux.fromIterable(personas)
			.take(2)
			.subscribe(x -> Log.info(x.toString()));
	
	}
	
	public void takeLast() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));

		Flux.fromIterable(personas)
			.takeLast(1)
			.subscribe(x -> Log.info(x.toString()));
	
	}
	
	public void skip() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));

		Flux.fromIterable(personas)
			.skip(1)
			.subscribe(x -> Log.info(x.toString()));
	
	}
	
	public void skipLast() {
		
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));

		Flux.fromIterable(personas)
			.skipLast(2)
			.subscribe(x -> Log.info(x.toString()));
	
	}
	
}
