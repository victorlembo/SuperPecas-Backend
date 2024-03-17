package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Peca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {

    @Query("SELECT p FROM Peca p WHERE LOWER(p.nome) LIKE LOWER(concat('%', :termo, '%')) OR LOWER(p.numeroSerie) LIKE LOWER(concat('%', :termo, '%')) OR LOWER(p.modeloCarro) LIKE LOWER(concat('%', :termo, '%')) OR LOWER(p.fabricante) LIKE LOWER(concat('%', :termo, '%'))")
    Page<Peca> findAllPagedByTerm(String termo, Pageable pageable);
    @Query("SELECT p FROM Peca p WHERE p.carro.carroID = :carroID")
    List<Peca> findByCarroID(Long carroID);

}
