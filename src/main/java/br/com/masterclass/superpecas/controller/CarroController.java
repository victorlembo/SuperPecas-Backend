package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.repository.CarroRepository;
import br.com.masterclass.superpecas.service.CarroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    CarroRepository carroRepository;
    @Autowired
    CarroService carroService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    public Optional<Carro> getById(@PathVariable Long id) {
        Optional<Carro> carroDTO = carroRepository.findById(id);
        return carroDTO;
    }

    @GetMapping("/listarTodos")
    public List<CarroDTO> getAll() {
        List<Carro> carros = carroRepository.findAll();
        return carros.stream()
                .map(carro -> modelMapper.map(carro, CarroDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/listarTodosPaginados/{numPage}")
    public Page<CarroDTO> getAllPaged(@PathVariable int numPage) {
        return carroService.findAllPaged(numPage);
    }



}
