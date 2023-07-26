package com.ipdec.reportsapi.domain.repository;

import com.ipdec.reportsapi.domain.model.Backend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackendRepository extends JpaRepository<Backend, Long> {

}
