package com.springboot.webflux.client.app.handlers;

import com.springboot.webflux.client.app.models.dto.Product;
import com.springboot.webflux.client.app.models.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;

import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class ProductHandler {
	
	private final ProductService service;

	public ProductHandler(ProductService service) {
		this.service = service;
	}

	public Mono<ServerResponse> showListOfProducts(ServerRequest request){
		return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
				.body(service.findAll(), Product.class);
	}
	
	public Mono<ServerResponse> showAProduct(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				service.findById(id).flatMap(p -> ServerResponse.ok()
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	public Mono<ServerResponse> create(ServerRequest request){
		Mono<Product> product = request.bodyToMono(Product.class);
		
		return product.flatMap(p-> {
			if(p.getCreateAt() == null)
				p.setCreateAt(new Date());
			return service.save(p);
			}).flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
					.contentType(APPLICATION_JSON_UTF8)
					.syncBody(p))
				.onErrorResume(error -> {
					WebClientResponseException errorResponse = (WebClientResponseException) error;
					if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST)
						return ServerResponse.badRequest()
								.contentType(APPLICATION_JSON_UTF8)
								.syncBody(errorResponse.getResponseBodyAsString());
					return Mono.error(errorResponse);
				});
	}
	
	public Mono<ServerResponse> update(ServerRequest request){
		Mono<Product> product = request.bodyToMono(Product.class);
		String id = request.pathVariable("id");
		
		return errorHandler(
				product.flatMap(p -> service.update(p, id))
					.flatMap(p-> ServerResponse.created(URI.create("/api/client/".concat(id)))
						.contentType(APPLICATION_JSON_UTF8)
						.syncBody(p))
				);
	}
	
	public Mono<ServerResponse> delete(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(service.delete(id).then(ServerResponse.noContent().build()));
	}
	
	public Mono<ServerResponse> upload(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				request.multipartData()
				.map(multipart -> multipart.toSingleValueMap().get("file"))
				.cast(FilePart.class)
				.flatMap(file -> service.upload(file, id))
				.flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
						.contentType(APPLICATION_JSON_UTF8)
						.syncBody(p))
				);
	}

	private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response) {
		return response.onErrorResume(error -> {
			WebClientResponseException errorResponse = (WebClientResponseException) error;
			if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				Map<String, Object> body = new HashMap<>();
				body.put("error", "Doesn't exist the product: ".concat(Objects.requireNonNull(errorResponse.getMessage())));
				body.put("timestamp", new Date());
				body.put("status", errorResponse.getStatusCode().value());
				return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(body);
			}
			return Mono.error(errorResponse);
		});
	}

}
