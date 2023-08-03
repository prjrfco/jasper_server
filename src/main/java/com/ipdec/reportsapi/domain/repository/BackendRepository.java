package com.ipdec.reportsapi.domain.repository;

import com.ipdec.reportsapi.domain.model.Backend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BackendRepository extends JpaRepository<Backend, Long> {

    Optional<Backend> findById(UUID backendId);
}
