package br.com.masterclass.superpecas.model.DTO;

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

    private Long carroID;
}
