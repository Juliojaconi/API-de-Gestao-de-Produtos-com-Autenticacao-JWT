package br.com.krono.exerciciossb.model.repositories;

import br.com.krono.exerciciossb.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository
        extends JpaRepository<Produto, Integer> {


    Iterable<Produto> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT p FROM Produto p where p.nome like %:nome%")
    Iterable<Produto> searchByNomeLike(@Param("nome") String nome);

}
