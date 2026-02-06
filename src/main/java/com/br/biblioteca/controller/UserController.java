package com.br.biblioteca.controller;

import com.br.biblioteca.dto.projection.UserSummaryDTO;
import com.br.biblioteca.dto.user.UserCreateDTO;
import com.br.biblioteca.dto.user.UserFilterDTO;
import com.br.biblioteca.dto.user.UserUpdateDTO;
import com.br.biblioteca.entity.UserEntity;
import com.br.biblioteca.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<Page<UserSummaryDTO>> pesquisarPaginado(UserFilterDTO dto, Pageable pageable){
        return ResponseEntity.ok(service.pesquisarPaginado(dto, pageable));
    }
    
    @PostMapping
    public ResponseEntity<UserEntity> cadastrar(@RequestBody UserCreateDTO dto) {
    	return ResponseEntity.status(HttpStatus.CREATED)
    			.body(service.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody UserUpdateDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    
}
