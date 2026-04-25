package com.emanuel.rinha_de_backend.Controller;

import com.emanuel.rinha_de_backend.Service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContagemController {

    private final PessoaService pessoaService;

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> contagemPessoas(){
        return ResponseEntity.ok(pessoaService.contagemPessoas());
    }
}
