package com.ipdec.reportsapi.config.feignService;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;


@FeignClient(name = "apiService")
public interface ApiService {

    @RequestLine("POST")
    void sendReport(URI baseUri, @RequestBody SendRelatorioDto dto);

}
