package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.RelatorioInputDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.repository.RelatorioRepository;
import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    @Autowired
    ServletContext context;

    private static final String JASPER_DIRETORIO = "temp";
    private static final String JASPER_PREFIXO = "relatorio_";
    private static final String JASPER_SUFIXO = ".jasper";

    private Map<String, Object> params = new HashMap<>();

    public void addParams(String key, Object value) {
        this.params.put(key, value);
    }

    public byte[] exportarPDF(Long id, RelatorioInputDto dto) throws IOException {
        byte[] bytes = null;
        Relatorio relatorio = repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Relatorio não encontrado"));

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
            e.printStackTrace();
        } finally {
            if (jasperFile != null) jasperFile.close();
            FileUtils.forceDelete(tempFile);
            FileUtils.deleteDirectory(tempDirectory.toFile());
        }
        return bytes;
    }

    public Relatorio create(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Relatorio relatorio = new Relatorio();
        relatorio.setNome(fileName);
        relatorio.setArquivo(file.getBytes());
        relatorio.setTipo(file.getContentType());

        return repository.save(relatorio);
    }

    public Relatorio buscar(Long id) throws IOException {
        Relatorio relatorio = repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        return repository.save(relatorio);
    }
}
