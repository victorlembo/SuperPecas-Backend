package br.com.masterclass.superpecas.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarroDTO {
    private Long carroID;

    private String nomeModelo;

    private String fabricante;

    private String codigoUnico;

}