package com.ipdec.reportsapi.domain.repository;

import com.ipdec.reportsapi.domain.model.FilaEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilaEnvioRepository extends JpaRepository<FilaEnvio, Long> {

    Optional<FilaEnvio> findByRelatorioId(UUID uuid);

}
