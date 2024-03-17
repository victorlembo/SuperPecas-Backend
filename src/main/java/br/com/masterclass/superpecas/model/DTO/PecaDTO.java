package br.com.masterclass.superpecas.model.DTO;

import br.com.masterclass.superpecas.model.Carro;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PecaDTO {
    private Long pecaID;
    private String nome;
    private String descricao;
    private String numeroSerie;
    private String fabricante;
    private String modeloCarro;
    private Carro carro;
}
