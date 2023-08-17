package com.ipdec.reportsapi.config.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ScheduleService service;

    @Scheduled(fixedRate = 60000)
    public void filaRelatorios() {
        service.processarFila();
        log.info("PROCESSANDO FILA DE ENVIO DE RELATÓRIOS: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

}
