package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    @Query("SELECT c FROM Carro c WHERE LOWER(c.nomeModelo) LIKE LOWER(concat('%', :termo, '%')) OR LOWER(c.fabricante) LIKE LOWER(concat('%', :termo, '%'))")
    Page<Carro> findAllPagedByTerm(String termo, Pageable pageable);

    @Query("SELECT p.carro.carroID, COUNT(p) FROM Peca p GROUP BY p.carro.carroID ORDER BY COUNT(p) DESC")
    List<Object[]> findTop10(Pageable pageable);



}

