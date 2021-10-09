package com.yan.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.yan.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PersonHandler {
	
	public Mono<ServerResponse> findAll(ServerRequest req) {
		List<Person> list = Arrays.asList(new Person(1, "Yanpieer"), new Person(2, "Josue"));
		Flux<Person> fxPerson = Flux.fromIterable(list);
		
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fxPerson, Person.class);
	}
}
