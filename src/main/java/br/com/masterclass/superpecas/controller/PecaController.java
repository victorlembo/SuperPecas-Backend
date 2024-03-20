package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.PecaRepository;
import br.com.masterclass.superpecas.service.PecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/peca")
public class PecaController {

    @Autowired
    PecaRepository pecaRepository;
    @Autowired
    PecaService pecaService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    public PecaDTO getById(@PathVariable Long id) {
        Optional<Peca> peca = pecaRepository.findById(id);
        return modelMapper.map(peca, PecaDTO.class);
    }

    @GetMapping("/listarTodos")
    public List<PecaDTO> getAll() {
        List<Peca> pecas = pecaRepository.findAll();
        return pecas.stream()
                .map(peca -> modelMapper.map(peca, PecaDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/listarTodosPaginados")
    public Page<PecaDTO> getAllPaged(@RequestParam(defaultValue = "0", name = "page") int numPage) {
        return pecaService.findAllPaged(numPage);
    }

    @GetMapping("/listarTodosPaginados/{termo}")
    public Page<PecaDTO> getAllPaged(@PathVariable String termo,
                                     @RequestParam(defaultValue = "0", name = "page") int numPage) throws Exception {
        return pecaService.findAllPagedByTerm(termo, numPage);
    }

    @PostMapping
    public ResponseEntity<?> createPeca(@RequestBody PecaDTO pecaDTO) {
        return pecaService.save(pecaDTO);
    }

    @PutMapping
    public ResponseEntity<?> updatePeca(@RequestBody PecaDTO pecaDTO) {
        return pecaService.save(pecaDTO);
    }

    @Operation(summary = "Excluir Peca", description = "Exclui uma peca do sistema")
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeca(@PathVariable Long id) {
     return pecaService.delete(id);
    }
}

