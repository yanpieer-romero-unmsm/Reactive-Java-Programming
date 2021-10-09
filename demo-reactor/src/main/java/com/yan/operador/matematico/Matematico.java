package com.yan.operador.matematico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;
import com.yan.operador.combinacion.Combinacion;

import reactor.core.publisher.Flux;

public class Matematico {
	private static final Logger Log = LoggerFactory.getLogger(Combinacion.class);
	
	public void average() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.collect(Collectors.averagingInt(Persona::getEdad))
			.subscribe(p -> Log.info(p.toString()));
	}
	
	public void count() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.count()
			.subscribe(x -> Log.info("Cantidad: " + x));
	}
	
	public void min() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.collect(Collectors.minBy(Comparator.comparing(Persona::getEdad)))
			.subscribe(p -> Log.info(p.get().toString()));
	}
	
	public void sum() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.collect(Collectors.summingInt(Persona::getEdad))
			.subscribe(x -> Log.info("Suma: " + x));
	}
	
	public void summarizing() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.collect(Collectors.summarizingInt(Persona::getEdad))
			.subscribe(x -> Log.info("resumen: " + x));
	}
}
