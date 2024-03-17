package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PecaService {
    @Autowired
    PecaRepository pecaRepository;
    @Autowired
    ModelMapper modelMapper;

    public Page<PecaDTO> findAllPaged(int numPage) {
        Pageable pageable = PageRequest.of(numPage, 10);
        Page<Peca> pecaPage = pecaRepository.findAll(pageable);
        return pecaPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
    }

    public Page<PecaDTO> findAllPagedByTerm(String termo, int numPage) throws Exception {
        Pageable pageable = PageRequest.of(numPage, 10);

        if (termo != null && !termo.isEmpty()) {
            Page<Peca> pecaPage = pecaRepository.findAllPagedByTerm(termo, pageable);
            if (pecaPage.isEmpty()) {
                throw new BadRequestException("Nenhum resultado encontrado para o termo de pesquisa: " + termo);
            }
            return pecaPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
        } else {
            throw new BadRequestException("Termo de pesquisa não fornecido");
        }
    }
}
