package com.clinic.inv_api.ReportService;


import com.clinic.inv_api.Model.Movimentacao;
import com.clinic.inv_api.Repository.MovimentacaoRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MovimentacaoRepository movimentacaoRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public byte[] gerarRelatorioMovimentacoes(LocalDate startDate, LocalDate endDate) {
        // Define o intervalo de tempo completo do dia
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // Busca as movimentações no banco de dados
        List<Movimentacao> movimentacoes = movimentacaoRepository.findByDataHoraBetweenOrderByDataHoraAsc(startDateTime, endDateTime);

        // Cria o PDF em memória
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Adiciona o Título
        document.add(new Paragraph("Relatório de Movimentação de Estoque")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Período: " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " a " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Cria a Tabela
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 4, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Adiciona os Cabeçalhos
        table.addHeaderCell("Data/Hora");
        table.addHeaderCell("Produto");
        table.addHeaderCell("Tipo");
        table.addHeaderCell("Quantidade");

        // Adiciona as Linhas com os dados
        for (Movimentacao m : movimentacoes) {
            table.addCell(m.getDataHora().format(FORMATTER));
            table.addCell(m.getProduto().getDescricao());
            table.addCell(m.getTipo().toString());
            table.addCell(String.format("%.2f", m.getQuantidade()));
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
}