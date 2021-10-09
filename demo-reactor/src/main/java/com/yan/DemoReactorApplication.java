package com.yan;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yan.model.Persona;
import com.yan.operador.matematico.Matematico;
import com.yan.operador.transformacion.Transformacion;

import io.reactivex.Observable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class DemoReactorApplication implements CommandLineRunner{

	private static final Logger Log = LoggerFactory.getLogger(DemoReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DemoReactorApplication.class, args);
	}
	
	public void reactor(){
		Mono.just(new Persona(1, "Yanpieer", 23))
			.doOnNext(p -> Log.info("[Reactor] Persona: " + p))
			.subscribe(p -> Log.info("[Reactor] Persona: " + p));
	}
	
	public void rxjava2(){
		Observable.just(new Persona(1, "Yanpieer", 23))
			.doOnNext(p -> Log.info("[RxJava2] Persona: " + p))
			.subscribe(p -> Log.info("[RxJava2] Persona: " + p));
	}
	
	public void mono() {
		Mono.just(new Persona(1, "Yanpieer", 23)).subscribe(p -> Log.info(p.toString()));
	}

	public void flux() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 23));
		personas.add(new Persona(2, "Josue", 23));
		personas.add(new Persona(3, "Cristofer", 23));
		Flux.fromIterable(personas).subscribe(p -> Log.info(p.toString()));
	}
	
	public void fluxMono() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Yanpieer", 23));
		personas.add(new Persona(2, "Josue", 23));
		personas.add(new Persona(3, "Cristofer", 23));
		
		Flux<Persona> fx = Flux.fromIterable(personas);
		fx.collectList().subscribe(lista -> Log.info(lista.toString()));
	
	}
	
	@Override
	public void run(String... args) throws Exception {
		Matematico app = new Matematico();
		//app.summarizing();
		Transformacion t = new Transformacion();
		t.flatMap();
	}

}
