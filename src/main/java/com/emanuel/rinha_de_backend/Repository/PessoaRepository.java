package com.emanuel.rinha_de_backend.Repository;

import com.emanuel.rinha_de_backend.Entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    //Validacao se o apelido ja existe pelo registro do banco
    boolean existsByApelido(String apelido);

    //Utiliza o termo como uma variavel de busca no banco e retorna os resultados obtidos
    @Query("SELECT p FROM Pessoa p where lower(p.apelido) like lower(concat('%',:termo,'%'))" +
            " or lower(p.nome) like lower(concat('%',:termo,'%'))" +
            " or lower(p.stack) like lower(concat('%',:termo,'%'))")
    List<Pessoa> buscaPorTermo(@Param("termo") String termo);

}
