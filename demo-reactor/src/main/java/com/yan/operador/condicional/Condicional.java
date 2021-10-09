package com.yan.operador.condicional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.model.Persona;
import com.yan.operador.combinacion.Combinacion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Condicional {
	
	private static final Logger Log = LoggerFactory.getLogger(Combinacion.class);
	
	public void defaultIfEmpty() {
		Mono.just(new Persona(1, "Yan", 23))
		//Mono.empty()
		//Flux.empty()
			.defaultIfEmpty(new Persona(0, "DEFAULT", 99))
			.subscribe(x -> Log.info(x.toString()));
	}
	
	public void takeUntil() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 21));
		personas.add(new Persona(2, "Josue", 22));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas)
			.takeUntil(p -> p.getEdad()>20)
			.subscribe(x -> Log.info(x.toString()));
	}
	
	public void timeOut() throws InterruptedException {
		List<Persona> personas1 = new ArrayList<>();
		personas1.add(new Persona(1, "Yanpieer", 21));
		personas1.add(new Persona(2, "Josue", 22));
		personas1.add(new Persona(3, "Cristofer", 23));
		
		Flux.fromIterable(personas1)
			.delayElements(Duration.ofSeconds(1))
			.timeout(Duration.ofSeconds(2))
			.subscribe(x -> Log.info(x.toString()));
		
		Thread.sleep(10000);
	}
	
}
