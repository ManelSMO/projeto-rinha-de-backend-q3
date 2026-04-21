package com.emanuel.rinha_de_backend.Controller.Response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record PessoaResponse(UUID id,
                             String apelido,
                             String nome,
                             LocalDate nascimento,
                             List<String> stack
) {
}
