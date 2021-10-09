package com.yan.operador.combinacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;
import com.yan.model.Venta;

import reactor.core.publisher.Flux;

public class Combinacion {
	
	private static final Logger Log = LoggerFactory.getLogger(Combinacion.class);
	
	public void merge() {
		
		List<Persona> personas1 = new ArrayList<>();
		personas1.add(new Persona(1, "Yanpieer", 21));
		personas1.add(new Persona(2, "Josue", 22));
		personas1.add(new Persona(3, "Cristofer", 23));

		List<Persona> personas2 = new ArrayList<>();
		personas2.add(new Persona(4, "Roberto", 16));
		personas2.add(new Persona(5, "Adolfo", 17));
		personas2.add(new Persona(6, "Valentin", 54));
		
		List<Venta> ventas = new ArrayList<>();
		ventas.add(new Venta(1, LocalDateTime.now()));
		
		Flux<Persona> fx1 = Flux.fromIterable(personas1);
		Flux<Persona> fx2 = Flux.fromIterable(personas2);
		Flux<Venta> fx3 = Flux.fromIterable(ventas);
		
		Flux.merge(fx1, fx2, fx3)
			.subscribe(p -> Log.info(p.toString()));
	}
	
	public void zip() {
		
		List<Persona> personas1 = new ArrayList<>();
		personas1.add(new Persona(1, "Yanpieer", 21));
		personas1.add(new Persona(2, "Josue", 22));
		personas1.add(new Persona(3, "Cristofer", 23));

		List<Persona> personas2 = new ArrayList<>();
		personas2.add(new Persona(4, "Roberto", 16));
		personas2.add(new Persona(5, "Adolfo", 17));
		personas2.add(new Persona(6, "Valentin", 54));
		
		List<Venta> ventas = new ArrayList<>();
		ventas.add(new Venta(1, LocalDateTime.now()));
		
		Flux<Persona> fx1 = Flux.fromIterable(personas1);
		Flux<Persona> fx2 = Flux.fromIterable(personas2);
		Flux<Venta> fx3 = Flux.fromIterable(ventas);
		
		/*
		Flux.zip(fx1, fx2, (p1, p2) -> String.format("Flux1: %s, Flux2: %s", p1, p2))
			.subscribe(x -> Log.info(x));
		*/
		
		Flux.zip(fx1, fx2, fx3)
			.subscribe(x -> Log.info(x.toString()));
	}
	
	public void zipWith() {
		
		List<Persona> personas1 = new ArrayList<>();
		personas1.add(new Persona(1, "Yanpieer", 21));
		personas1.add(new Persona(2, "Josue", 22));
		personas1.add(new Persona(3, "Cristofer", 23));

		List<Persona> personas2 = new ArrayList<>();
		personas2.add(new Persona(4, "Roberto", 16));
		personas2.add(new Persona(5, "Adolfo", 17));
		personas2.add(new Persona(6, "Valentin", 54));
		
		List<Venta> ventas = new ArrayList<>();
		ventas.add(new Venta(1, LocalDateTime.now()));
		
		Flux<Persona> fx1 = Flux.fromIterable(personas1);
		Flux<Venta> fx3 = Flux.fromIterable(ventas);
		
		
		fx1.zipWith(fx3, (p1, v2) -> String.format("Flux1: %s, Flux2: %s", p1, v2))
			.subscribe(x -> Log.info(x.toString()));
	}
	
}
