package com.springboot.reactor.app.models;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class UsuarioComentarios {
    private Usuario usuario;
    private Comentarios comentarios;
}
