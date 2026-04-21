package com.emanuel.rinha_de_backend.Controller.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


import java.util.List;

@Builder
public record PessoaRequest(
        @NotBlank(message = "Apelido nao pode ficar vazio")
        @Size(max = 32, message = "Tamanho maximo do apelido e de 32 caracteres")
        String apelido,

        @NotBlank(message = "Nome nao pode ficar vazio")
        @Size(max = 100, message ="Tamanho maximo para o nome e de 100 caracteres")
        String nome,

        @NotBlank(message = "Nascimento nao pode ficar vazio")
        String nascimento,

        List<@Size(max=32, message=("tamanho maximo para a stack de 32 caracteres")) @NotBlank(message = "Itens invalidos para lista") String> stack
) {
}
