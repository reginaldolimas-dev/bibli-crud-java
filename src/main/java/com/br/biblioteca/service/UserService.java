package com.br.biblioteca.service;

import com.br.biblioteca.dto.UserFilterDTO;
import com.br.biblioteca.dto.projection.UserSummaryDTO;
import com.br.biblioteca.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<UserSummaryDTO> pesquisarPaginado(UserFilterDTO dto) {
        return repository.findAll(dto);
    }
}
