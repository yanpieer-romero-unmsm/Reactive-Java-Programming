package com.theyan.coffeeservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class CoffeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeServiceApplication.class, args);
    }

}

@RestController
@RequestMapping("/coffees")
class CoffeeController {
    private final CoffeeService service;

    CoffeeController(CoffeeService service) {
        this.service = service;
    }

    @GetMapping()
    public Flux<Coffee> all() {
        return service.getAllCoffees();
    }

    @GetMapping("/{id}")
    public Mono<Coffee> byId(@PathVariable String id) {
        return service.getCoffeeById(id);
    }

    @GetMapping(value = "/{id}/orders", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CoffeeOrfer> orders(@PathVariable String id) {
        return service.getOrders(id);
    }

}

@Service
class CoffeeService {
    private final CoffeeRepository repo;

    CoffeeService(CoffeeRepository repo) {
        this.repo = repo;
    }

    Flux<Coffee> getAllCoffees() {
        return repo.findAll();
    }

    Mono<Coffee> getCoffeeById(String id) {
        return repo.findById(id);
    }

    Flux<CoffeeOrfer> getOrders(String coffeeId) {
        return Flux.<CoffeeOrfer>generate(sink -> sink.next(new CoffeeOrfer(coffeeId, Instant.now())))
                .delayElements(Duration.ofSeconds(1));
    }
}

@Component
class DataLoader {
    private final CoffeeRepository repo;

    DataLoader(CoffeeRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    private void load() {
        repo.deleteAll().thenMany(
                        Flux.just("Americano", "Esmeralda", "Kaldi's Coffee", "Caf?? Ol??", "Delta", "Java")
                                .map(name -> new Coffee(UUID.randomUUID().toString(), name))
                                .flatMap(repo::save))
                .thenMany(repo.findAll())
                .subscribe(System.out::println);
    }
}

interface CoffeeRepository extends ReactiveCrudRepository<Coffee, String> {
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CoffeeOrfer {
    private String cofeeId;
    private Instant dateOrdered;
}

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
class Coffee {
    @Id
    private String id;
    private String name;
}