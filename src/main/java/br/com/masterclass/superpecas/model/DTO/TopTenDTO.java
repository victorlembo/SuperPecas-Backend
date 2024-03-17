package br.com.masterclass.superpecas.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopTenDTO {

    private Long carroID;

    private String nomeModelo;

    private String fabricante;

    private String codigoUnico;

    private long numPecasAssociadas;

}
