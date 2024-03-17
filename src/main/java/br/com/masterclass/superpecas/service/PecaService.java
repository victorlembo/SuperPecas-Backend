package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.CarroRepository;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PecaService {
    @Autowired
    PecaRepository pecaRepository;
    @Autowired
    CarroRepository carroRepository;
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

    public ResponseEntity<?> save(PecaDTO pecaDTO) {
        try {
            Optional<Carro> carroOptional = carroRepository.findById(pecaDTO.getCarroID());
            if (carroOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carro não encontrado");
            }

            Carro carro = carroOptional.get();
            Peca peca = modelMapper.map(pecaDTO, Peca.class);
            peca.setCarro(carro);

            if (peca.getPecaID() != null) {
                Optional<Peca> existingPecaOptional = pecaRepository.findById(peca.getPecaID());
                if (existingPecaOptional.isPresent()) {
                    Peca existingPeca = existingPecaOptional.get();
                    modelMapper.map(pecaDTO, existingPeca);
                    pecaRepository.save(existingPeca);
                    return ResponseEntity.status(HttpStatus.OK).body("Peça atualizada com sucesso!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada");
                }
            } else {
                pecaRepository.save(peca);
                return ResponseEntity.status(HttpStatus.OK).body("Peça criada com sucesso!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }
    }

    public ResponseEntity<?> delete(Long id) {
        if (pecaRepository.existsById(id)) {
            pecaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Peça excluída com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada");
        }
    }
}
