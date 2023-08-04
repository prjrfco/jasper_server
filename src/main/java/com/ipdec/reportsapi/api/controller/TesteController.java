package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.api.dto.BackendDto;
import com.ipdec.reportsapi.api.dto.BackendInputDto;
import com.ipdec.reportsapi.domain.service.BackendService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @GetMapping
    public String listar() {
        return ResponseEntity.ok("sucesso").getBody();
    }
}
