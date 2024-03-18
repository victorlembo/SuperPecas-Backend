package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.model.Projection.CarroProjection;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {
    @Autowired
    CarroRepository carroRepository;
    @Autowired
    PecaRepository pecaRepository;
    @Autowired
    ModelMapper modelMapper;

    public Page<CarroDTO> findAllPaged(int numPage) {
        Pageable pageable = PageRequest.of(numPage, 10);
        Page<Carro> carroPage = carroRepository.findAll(pageable);
        return carroPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
    }

    public Page<CarroDTO> findAllPagedByTerm(String termo, int numPage) throws Exception {
        Pageable pageable = PageRequest.of(numPage, 10);

        if (termo != null && !termo.isEmpty()) {
            Page<Carro> carroPage = carroRepository.findAllPagedByTerm(termo, pageable);
            if (carroPage.isEmpty()) {
                throw new BadRequestException("Nenhum resultado encontrado para o termo de pesquisa: " + termo);
            }
            return carroPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
        } else {
            throw new BadRequestException("Termo de pesquisa não fornecido");
        }
    }

    public List<String> findTop10Fabricantes() {
        List<Carro> carros = carroRepository.findAll();

        Map<String, Long> count = carros.stream()
                .collect(Collectors.groupingBy(
                        carro -> carro.getFabricante(),
                        Collectors.counting()));

        return count.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> save(CarroDTO carroDTO) {
        try {
            if (carroDTO.getCarroID() != null) {
                Optional<Carro> existingCarroOptional = carroRepository.findById(carroDTO.getCarroID());

                if (existingCarroOptional.isPresent()) {
                    Carro existingCarro = existingCarroOptional.get();
                    modelMapper.map(carroDTO, existingCarro);
                    carroRepository.save(existingCarro);
                    return ResponseEntity.status(HttpStatus.OK).body("Carro atualizado com sucesso!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carro não encontrado");
                }
            } else {
                Carro novoCarro = modelMapper.map(carroDTO, Carro.class);
                carroRepository.save(novoCarro);
                return ResponseEntity.status(HttpStatus.OK).body("Carro criado com sucesso!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }
    }

    public ResponseEntity<?> delete(Long id) {
        try {
            Optional<Carro> carroOptional = carroRepository.findById(id);
            if (carroOptional.isPresent()) {
                Carro carro = carroOptional.get();

                List<Peca> pecas = pecaRepository.findByCarroID(carro.getCarroID());
                if (!pecas.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O carro está vinculado a uma ou mais peças. Não pode ser excluído.");
                }
                carroRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Carro excluído com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carro não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }
    }

    public List<CarroProjection> findTop10() {
        Pageable pageable = PageRequest.of(0, 10);
        return carroRepository.findTop10(pageable);
    }

}
