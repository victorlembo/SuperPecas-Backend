package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.repository.CarroRepository;
import br.com.masterclass.superpecas.service.CarroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public CarroDTO getById(@PathVariable Long id) {
        Optional<Carro> carro = carroRepository.findById(id);
        return modelMapper.map(carro, CarroDTO.class);
    }

    @GetMapping("/listarTodos")
    public List<CarroDTO> getAll() {
        List<Carro> carros = carroRepository.findAll();
        return carros.stream()
                .map(carro -> modelMapper.map(carro, CarroDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/listarTodosPaginados")
    public Page<CarroDTO> getAllPaged(@RequestParam(defaultValue = "0", name = "page") int numPage) {
        return carroService.findAllPaged(numPage);
    }

    @GetMapping("/listarTodosPaginados/{termo}")
    public Page<CarroDTO> getAllPaged(@PathVariable String termo,
                                      @RequestParam(defaultValue = "0", name = "page") int numPage) throws Exception {
        return carroService.findAllPagedByTerm(termo, numPage);
    }

    @GetMapping("/listarTodosFabricantes")
    public List<String> getAllFabricantes() {
        List<Carro> carros = carroRepository.findAll();

        return carros.stream()
                .map(carro -> carro.getFabricante())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/listarTop10Fabricantes")
    public List<String> findTop10Fabricantes() {
        return carroService.findTop10Fabricantes();
    }
}
