package br.com.masterclass.superpecas.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarroDTO {
    private Long carroID;
    private String NomeModelo;
    private String Fabricante;
    private String CodigoUnico;

}