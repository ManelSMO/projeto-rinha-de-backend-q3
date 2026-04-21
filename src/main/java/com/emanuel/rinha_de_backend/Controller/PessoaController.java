package com.emanuel.rinha_de_backend.Controller;

import com.emanuel.rinha_de_backend.Service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;


}
