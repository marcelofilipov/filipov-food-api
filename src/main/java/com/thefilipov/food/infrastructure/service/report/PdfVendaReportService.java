package com.thefilipov.food.infrastructure.service.report;

import com.thefilipov.food.domain.filter.VendaDiariaFilter;
import com.thefilipov.food.domain.service.VendaQueryService;
import com.thefilipov.food.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/vendas-diarias.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw  new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }

    }

}
