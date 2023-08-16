package com.ipdec.reportsapi.config.scheduler;

import com.ipdec.reportsapi.config.feignService.ApiService;
import com.ipdec.reportsapi.config.feignService.SendRelatorioDto;
import com.ipdec.reportsapi.domain.model.FilaEnvio;
import com.ipdec.reportsapi.domain.repository.FilaEnvioRepository;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private FilaEnvioRepository filaEnvioRepository;

    ApiService apiService;

    @Autowired
    public void FeignDemoController(Decoder decoder, Encoder encoder) {
        apiService = Feign.builder().encoder(encoder).decoder(decoder)
                .target(Target.EmptyTarget.create(ApiService.class));
    }

    public void processarFila() {
        List<FilaEnvio> lista = filaEnvioRepository.findAll();
        lista.forEach(l -> {
            boolean erro = false;
            try {
                this.apiService.sendReport(new URI(l.getRelatorio().getBackend().getUrl()), new SendRelatorioDto(l.getRelatorio()));
            } catch (Exception e) {
                erro = true;
                l.setCriadoEm(new Date());
                filaEnvioRepository.save(l);
            }
            if (!erro) {
                filaEnvioRepository.delete(l);
            }
        });
    }
}
