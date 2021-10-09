package com.springboot.webflux.app.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@Data
@Document(collection = "productos")
public class Producto {
    @Id
    private String id;
    private String nombre;
    private Double precio;
    private Date createAt;

    public Producto(String nombre, Double precio){
        this.nombre = nombre;
        this.precio = precio;
    }
}
