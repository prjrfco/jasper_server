package com.ipdec.reportsapi.domain.repository;

import com.ipdec.reportsapi.domain.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    List<Relatorio> findAllByBackendId(UUID backendId);

    Optional<Relatorio> findByIdAndAndBackend_Id(UUID relatorioId, UUID backendId);

    Optional<Relatorio> findByNomeAndBackend_Nome(String relatorioNome, String backendId);

    List<Relatorio> findAllByBackend_Nome(String nome);
}
