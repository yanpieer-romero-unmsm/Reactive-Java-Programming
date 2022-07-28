package com.springboot.webflux.client.app.models.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.springboot.webflux.client.app.models.dto.Product;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;

import static org.springframework.http.MediaType.*;
import org.springframework.stereotype.Service;
// import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

	private final WebClient.Builder client;

	public ProductServiceImpl(WebClient.Builder client) {
		this.client = client;
	}

	@Override
	public Flux<Product> findAll() {
		return client.build().get()
				.accept(APPLICATION_JSON_UTF8)
				.exchange()
				.flatMapMany(response -> response.bodyToFlux(Product.class));
	}

	@Override
	public Mono<Product> findById(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return client.build().get()
				.uri("/{id}", params)
				.accept(APPLICATION_JSON_UTF8)
				.retrieve()
				.bodyToMono(Product.class);
				/*.exchange()
				.flatMap(response -> response.bodyToMono(Product.class));*/
	}

	@Override
	public Mono<Product> save(Product product) {
		return client.build().post()
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				//.body(fromObject(product))
				.syncBody(product)
				.retrieve()
				.bodyToMono(Product.class);
	}

	@Override
	public Mono<Product> update(Product product, String id) {
		return client.build().put()
				.uri("/{id}", Collections.singletonMap("id", id))
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(product)
				.retrieve()
				.bodyToMono(Product.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		return client.build().delete()
				.uri("/{id}", Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(Void.class);
	}

	@Override
	public Mono<Product> upload(FilePart file, String id) {
		MultipartBodyBuilder parts = new MultipartBodyBuilder();
		parts.asyncPart("file", file.content(), DataBuffer.class)
				.headers(h -> h.setContentDispositionFormData("file", file.filename()));
		
		return client.build().post()
				.uri("/upload/{id}", Collections.singletonMap("id", id))
				.contentType(MULTIPART_FORM_DATA)
				.syncBody(parts.build())
				.retrieve()
				.bodyToMono(Product.class);
	}

}
