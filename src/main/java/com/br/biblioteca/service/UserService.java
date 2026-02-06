package com.br.biblioteca.service;

import com.br.biblioteca.dto.projection.UserSummaryDTO;
import com.br.biblioteca.dto.user.UserCreateDTO;
import com.br.biblioteca.dto.user.UserFilterDTO;
import com.br.biblioteca.dto.user.UserUpdateDTO;
import com.br.biblioteca.entity.UserEntity;
import com.br.biblioteca.exception.BusinessException;
import com.br.biblioteca.repository.LoanRepository;
import com.br.biblioteca.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
	private final LoanRepository loanRepository;

	public Page<UserSummaryDTO> pesquisarPaginado(UserFilterDTO dto, Pageable pageable) {
    	List<UserSummaryDTO> modelos = repository.findByResume(dto, pageable);
    	return new PageImpl<>(modelos, pageable, modelos.size());
    }

	public UserEntity cadastrar(UserCreateDTO dto) {
		
		boolean emailAlreadyExists = repository.existsByEmailIgnoreCase(dto.getEmail());
		
		if (emailAlreadyExists) {
			throw new IllegalStateException("Email já cadastrado");
		}
		
		
		UserEntity user = new UserEntity();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		
		return repository.save(user);
	}

    public void deletar(String id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		boolean existsLoanActive = loanRepository.existsByUserId(user.getId());

		if (existsLoanActive) {
			throw new IllegalStateException("Usuário possui empréstimo ativo e não pode ser inativado");
		}

        user.setActive(false);
        user.setInactivedAt(LocalDateTime.now());
        repository.save(user);
    }

    public void atualizar(String id, UserUpdateDTO dto) {
		UserEntity user = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		if (!user.getActive()) {
			throw new IllegalStateException("Usuário inativo não pode ser atualizado");
		}

		if (dto.getName() != null) {
			user.setName(dto.getName());
		}

		if (dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
		}
		repository.save(user);
    }
}
