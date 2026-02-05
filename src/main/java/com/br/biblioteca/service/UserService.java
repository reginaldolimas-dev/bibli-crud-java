package com.br.biblioteca.service;

import com.br.biblioteca.dto.projection.UserSummaryDTO;
import com.br.biblioteca.dto.user.UserCreateDTO;
import com.br.biblioteca.dto.user.UserFilterDTO;
import com.br.biblioteca.entity.UserEntity;
import com.br.biblioteca.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Page<UserSummaryDTO> pesquisarPaginado(UserFilterDTO dto, Pageable pageable) {
    	List<UserSummaryDTO> modelos = repository.findByResume(dto, pageable);
    	return new PageImpl<>(modelos, pageable, modelos.size());
    }

	public UserEntity cadastrar(UserCreateDTO dto) {
		
		boolean emailAlreadyExists = repository.existsByEmailIgnoreCase(dto.getEmail());
		
		if (emailAlreadyExists) {
			throw new IllegalArgumentException("Email já cadastrado");
		}
		
		
		UserEntity user = new UserEntity();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		
		return repository.save(user);
	}

    public void deletar(String id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        user.setActive(false);
        user.setInactivedAt(LocalDateTime.now());
        repository.save(user);
    }
}
