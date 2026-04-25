package com.emanuel.rinha_de_backend.Service;

import com.emanuel.rinha_de_backend.Controller.Request.PessoaRequest;
import com.emanuel.rinha_de_backend.Controller.Response.PessoaResponse;
import com.emanuel.rinha_de_backend.Entity.Pessoa;
import com.emanuel.rinha_de_backend.Repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    //Metodos auxiliares para validacao de create pessoa
    //metodo de converter a data de nascimento de String para LocalDate, deixar no formato valido e com as informacoes validas
    private LocalDate converterNascimento(String nascimento) {
        try{
            return LocalDate.parse(nascimento);
        }catch(DateTimeParseException e){
            throw new RuntimeException("Data de nascimento invalida");
        }
    }

    //Metodo auxiliar que converte a Stack para lista de String
    private String converterStackString(List<String> stack){
        if(stack == null || stack.isEmpty()){
            return null;
        }
        return String.join(";", stack);
    }

    //Metodo converter lista de string para stack
    private List<String> converterStringStack(String stack){
        if(stack == null || stack.isBlank()){
            return null;
        }
        return Arrays.asList(stack.split(";"));
    }

    //Medoto auxiliar de converter a entity para response, para a busca por termo
    private PessoaResponse converterParaResponse(Pessoa pessoa){
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getApelido(),
                pessoa.getNome(),
                pessoa.getNascimento(),
                converterStringStack(pessoa.getStack())
        );
    }

    //Metodo create pessoa com as validacoes dos metodos auxiliares
    public PessoaResponse create(PessoaRequest pessoaRequest) {
        //Utilizo o metodo criado na repository para validar se o apelido existe
        if(pessoaRepository.existsByApelido(pessoaRequest.apelido())){
            throw new RuntimeException("Apelido ja existe");
        }

        //Metodos auxiliares para converter os dados para salvar no banco
        LocalDate nascimentoConverter = converterNascimento(pessoaRequest.nascimento());
        String stackConverter = converterStackString(pessoaRequest.stack());

        //Salvo as informacoes convertidas
        Pessoa pessoa = new Pessoa();
        pessoa.setApelido(pessoaRequest.apelido());
        pessoa.setNome(pessoaRequest.nome());
        pessoa.setNascimento(nascimentoConverter);
        pessoa.setStack(stackConverter);
        Pessoa pessoaSave = pessoaRepository.save(pessoa);

        //Retorno a response para o usuario
        return converterParaResponse(pessoaSave);

    }

    //Buscar por ID a pessoa cadastrada, e validar se existe, retorna a Response
    public PessoaResponse findById(UUID id){
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pessoa nao encontrada"));

        return converterParaResponse(pessoa);
    }

    //Metodo de busca por termo
    public List<PessoaResponse> buscarPorTermo(String termo){
        //Crio a lista de pessoas e adiciono aquelas que se encaixam com o termo de busca -> faz a consulta no banco
        List<Pessoa> pessoas = pessoaRepository.buscaPorTermo(termo);

        //Converto elas para reponse com o metodo auxiliar criado e retorno para o usuario.
        return pessoas.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    //Metodo de contagem nao obrigatorio
    public Long contagemPessoas(){
        return  pessoaRepository.count();
    }
}
