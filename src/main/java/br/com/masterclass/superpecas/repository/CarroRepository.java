package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    @Query("SELECT c FROM Carro c WHERE LOWER(c.nomeModelo) LIKE LOWER(concat('%', :termo, '%')) OR LOWER(c.fabricante) LIKE LOWER(concat('%', :termo, '%'))")
    Page<Carro> findAllPagedByTerm(String termo, Pageable pageable);
}

