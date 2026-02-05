package com.br.biblioteca.repository;

import com.br.biblioteca.dto.projection.UserSummaryDTO;
import com.br.biblioteca.dto.user.UserFilterDTO;
import com.br.biblioteca.entity.UserEntity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	
	@Query("""
			SELECT u.id AS id,
				   u.name AS name,
				   u.email AS email
			FROM UserEntity u
			WHERE (:#{#dto.id} IS NULL OR u.id = :#{#dto.id})
			AND (:#{#dto.name} IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :#{#dto.name}, '%')))
			AND (:#{#dto.email} IS NULL OR LOWER(u.email) = LOWER(:#{#dto.email}))
			""")
	List<UserSummaryDTO> findByResume(UserFilterDTO dto, Pageable pageable);
	
	boolean existsByEmailIgnoreCase(String email);
}
