package br.com.masterclass.superpecas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pecas")
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PecaID")
    private Long pecaID;
    private String nome;
    private String descricao;
    private String numeroSerie;
    private String fabricante;
    private String modeloCarro;
    @JoinColumn(name="CarroID")
    @OneToOne
    private Carro carro;
}
