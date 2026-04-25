package com.emanuel.rinha_de_backend.Controller;

import com.emanuel.rinha_de_backend.Controller.Request.PessoaRequest;
import com.emanuel.rinha_de_backend.Controller.Response.PessoaResponse;
import com.emanuel.rinha_de_backend.Service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponse> create(@Valid @RequestBody PessoaRequest pessoaRequest) {
        PessoaResponse pessoaSalva = pessoaService.create(pessoaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> getById(@PathVariable UUID id) {
        try{
            return ResponseEntity.ok(pessoaService.findById(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> buscarPorTermo(@RequestParam("t") String termo) {
        return ResponseEntity.ok(pessoaService.buscarPorTermo(termo));
    }
}
