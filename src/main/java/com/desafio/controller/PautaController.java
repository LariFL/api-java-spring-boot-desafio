package com.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.model.Pauta;
import com.desafio.repository.PautaRepository;

@RestController
@RequestMapping("/pautas")
public class PautaController {
	
	@Autowired
	private PautaRepository pautaRepository;

	@GetMapping(path = "/listar")
	public List<Pauta> listar() {
		return pautaRepository.findAll();
	}
	
    @GetMapping(path = "/{id}")
    public ResponseEntity<Pauta> consultar(@PathVariable("id") Long id) {
        return pautaRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping(path = "/criar")
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta criar(@RequestBody Pauta pauta) {
		return pautaRepository.save(pauta);
	}
}
