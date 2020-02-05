package com.tdc.api.controller;

import com.tdc.api.repository.ProdutoRepository;
import com.tdc.api.vo.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping(path = "produtos")
    public ResponseEntity<List<Produto>> findAll() {
        return new ResponseEntity<>(produtoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "produtos/{id}")
    public ResponseEntity<Produto> findById(@PathVariable("id") int id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if(produto.isPresent()) {
            return new ResponseEntity<>(produto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "produtos")
    public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) {
        return new ResponseEntity<>(produtoRepository.save(produto), HttpStatus.CREATED);
    }

    @PutMapping(path="produtos/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable("id") int id, @RequestBody Produto produto) {
        produto.setId(id);
        return new ResponseEntity<>(produtoRepository.save(produto), HttpStatus.OK);
    }

    @DeleteMapping(path="produtos/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable("id") int id) {
        produtoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
