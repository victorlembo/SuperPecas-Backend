package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.repository.CarroRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarroService {
    @Autowired
    CarroRepository carroRepository;
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

}
