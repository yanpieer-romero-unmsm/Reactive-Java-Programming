package com.springboot.webflux.client.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springboot.webflux.client.app.handlers.ProductHandler;

@Configuration
public class RouterConfig {
	
	@Bean
	public RouterFunction<ServerResponse> paths(ProductHandler handler){
		return route(GET("/api/client"), handler::showListOfProducts)
				.andRoute(GET("/api/client/{id}"), handler::showAProduct)
				.andRoute(POST("/api/client"), handler::create)
				.andRoute(PUT("/api/client/{id}"), handler::update)
				.andRoute(DELETE("/api/client/{id}"), handler::delete)
				.andRoute(POST("/api/client/upload/{id}"), handler::upload);
	}

}
