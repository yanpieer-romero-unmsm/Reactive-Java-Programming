package com.springboot.webflux.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.webflux.app.models.documents.Category;
import com.springboot.webflux.app.models.documents.Product;
import com.springboot.webflux.app.models.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpringBootWebfluxApirestApplicationTests {

	@Autowired
	private WebTestClient client;

	@Autowired
	private ProductService service;

	@Value("${config.base.endpoint}")
	private String url;

	@Test
	public void showListOfProductsTest() {
		client.get().uri(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Product.class)
				.consumeWith(response -> {
					List<Product> products = response.getResponseBody();
					products.forEach(p -> System.out.println(p.getName()));
					Assertions.assertThat(products.size() > 0).isTrue();
				});
				//.hasSize(9);
	}

	@Test
	public void showAProductTest() {
		Product product = service.findByName("TV Panasonic Pantalla LCD").block();
		client.get().uri(url + "/{id}", Collections.singletonMap("id", product.getId()))
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(Product.class)
				.consumeWith(response -> {
					Product p = response.getResponseBody();
					Assertions.assertThat(p.getId().length() > 0).isTrue();
					Assertions.assertThat(p.getName()).isEqualTo("TV Panasonic Pantalla LCD");
				});
				/*.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.name").isEqualTo("TV Panasonic Pantalla LCD");*/
	}

	@Test
	public void createTest() {
		Category category = service.findCategoryByName("Furniture").block();
		Product product = new Product("Mesa comedor", 100.00, category);
		client.post().uri(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(product), Product.class)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.product.id").isNotEmpty()
				.jsonPath("$.product.name").isEqualTo("Mesa comedor")
				.jsonPath("$.product.category.name").isEqualTo("Furniture");
	}

	@Test
	public void createTestV2() {
		Category category = service.findCategoryByName("Furniture").block();
		Product product = new Product("Mesa comedor", 100.00, category);
		client.post().uri(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(product), Product.class)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
				.consumeWith(response -> {
					Object o = response.getResponseBody().get("product");
					Product p = new ObjectMapper().convertValue(o, Product.class);
					Assertions.assertThat(p.getId()).isNotEmpty();
					Assertions.assertThat(p.getName()).isEqualTo("Mesa comedor");
					Assertions.assertThat(p.getCategory().getName()).isEqualTo("Furniture");
				});
	}

	@Test
	public void updateTest() {
		Product product = service.findByName("Sony Notebook").block();
		Category category = service.findCategoryByName("Electronic").block();

		Product editedProduct = new Product("Asus Notebook", 700.00, category);

		client.put().uri(url + "/{id}", Collections.singletonMap("id", product.getId()))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(editedProduct), Product.class)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.name").isEqualTo("Asus Notebook")
				.jsonPath("$.category.name").isEqualTo("Electronic");
	}

	@Test
	public void deleteTest() {
		Product product = service.findByName("Mica Cómoda 5 Cajones").block();
		client.delete().uri(url + "/{id}", Collections.singletonMap("id", product.getId()))
				.exchange()
				.expectStatus().isNoContent()
				.expectBody()
				.isEmpty();

		client.get().uri(url + "/{id}", Collections.singletonMap("id", product.getId()))
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.isEmpty();
	}
}
