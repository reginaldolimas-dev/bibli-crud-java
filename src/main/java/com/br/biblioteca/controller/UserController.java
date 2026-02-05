package com.br.biblioteca.controller;

import com.br.biblioteca.dto.UserFilterDTO;
import com.br.biblioteca.dto.projection.UserSummaryDTO;
import com.br.biblioteca.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserSummaryDTO> pesquisarPaginado(UserFilterDTO dto){
        return service.pesquisarPaginado(dto);
    }
}
