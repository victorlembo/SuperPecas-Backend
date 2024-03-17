package br.com.masterclass.superpecas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pecas")
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PecaID")
    private Long pecaID;
    @Column(name = "Nome", nullable = false)
    private String nome;
    @Column(name = "Descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;
    @Column(name = "NumeroSerie", nullable = false, unique = true)
    private String numeroSerie;
    @Column(name = "Fabricante", nullable = false)
    private String fabricante;
    @Column(name = "ModeloCarro", nullable = false)
    private String modeloCarro;
    @JoinColumn(name = "CarroID", nullable = false)
    @OneToOne
    private Carro carro;
}
