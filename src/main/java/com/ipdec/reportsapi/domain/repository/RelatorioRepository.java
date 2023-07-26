package com.ipdec.reportsapi.domain.repository;

import com.ipdec.reportsapi.domain.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

}
