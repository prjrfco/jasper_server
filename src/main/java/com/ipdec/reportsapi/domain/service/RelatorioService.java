package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.RelatorioDto;
import com.ipdec.reportsapi.api.dto.RelatorioInputDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.domain.model.Backend;
import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.repository.BackendRepository;
import com.ipdec.reportsapi.domain.repository.RelatorioRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    @Autowired
    private BackendRepository backendRepository;

    private static final String JASPER_DIRETORIO = "temp";
    private static final String JASPER_PREFIXO = "relatorio_";
    private static final String JASPER_SUFIXO = ".jasper";

    public List<RelatorioDto> listar(UUID id) {
        List<Relatorio> lista = repository.findAllByBackendId(id);

        return lista.stream().map(RelatorioDto::new).toList();
    }

    public RelatorioDto buscar(UUID backendId, UUID relatorioId) {
        Relatorio relatorio = repository.findByIdAndAndBackend_Id(relatorioId, backendId
        ).orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        return new RelatorioDto(relatorio);
    }

    public RelatorioDto create(MultipartFile file, UUID backendId) throws IOException {
        Backend backend = backendRepository.findById(backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Relatorio relatorio = new Relatorio();
        relatorio.setNome(fileName);
        relatorio.setArquivo(file.getBytes());
        relatorio.setTipo(file.getContentType());
        relatorio.setBackend(backend);
        relatorio.setVersao(1);

        return new RelatorioDto(repository.save(relatorio));
    }

    public byte[] exportarPDF(UUID backendId, UUID relatorioId, RelatorioInputDto dto) throws IOException {
        byte[] bytes = null;
        Relatorio relatorio = repository.findByIdAndAndBackend_Id(relatorioId, backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatorio não encontrado"));

        Path tempDirectory = Files.createTempDirectory(Paths.get("target"), JASPER_DIRETORIO);

        File tempFile = File.createTempFile(JASPER_PREFIXO, JASPER_SUFIXO, tempDirectory.toFile());
        OutputStream os = new FileOutputStream(tempFile);
        os.write(relatorio.getArquivo());
        os.close();

        InputStream jasperFile = null;
        try {
            jasperFile = new FileInputStream(tempFile);

//            JasperPrint print = JasperFillManager.fillReport(jasperFile, dto.getParams(), new JREmptyDataSource());

            JasperPrint print = JasperFillManager.fillReport(jasperFile, dto.getParams(), new JRBeanCollectionDataSource(dto.getParameter_list()));


            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (JRException e) {
            jasperFile.close();
            e.printStackTrace();
        } finally {
            if (jasperFile != null) jasperFile.close();
            FileUtils.forceDelete(tempFile);
            FileUtils.deleteDirectory(tempDirectory.toFile());
        }
        return bytes;
    }

}
