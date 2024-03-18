package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.Projection.CarroProjection;
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

    @Query("SELECT p.carro.carroID AS carroID, COUNT(p) AS count, c.nomeModelo AS nomeModelo, c.fabricante AS fabricante, c.codigoUnico AS codigoUnico FROM Peca p JOIN p.carro c GROUP BY p.carro.carroID, c.nomeModelo, c.fabricante, c.codigoUnico ORDER BY COUNT(p) DESC")
    List<CarroProjection> findTop10(Pageable pageable);

}

