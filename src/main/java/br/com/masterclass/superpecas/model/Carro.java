package br.com.masterclass.superpecas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "carros")
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarroID")
    private Long carroID;
    @Column(name = "NomeModelo", nullable = false)
    private String nomeModelo;
    @Column(name = "Fabricante", nullable = false)
    private String fabricante;
    @Column(name = "CodigoUnico", nullable = false, unique = true)
    private String codigoUnico;
}
