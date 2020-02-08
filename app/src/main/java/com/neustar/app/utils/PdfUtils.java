package com.neustar.app.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.neustar.app.constants.Constants;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PdfUtils {
  private static final Logger LOG = LoggerFactory.getLogger(PdfUtils.class);

  public void exportToPDF(String host, Map<String, List<String>> output) {
    LOG.info("Starting export of the monitoring to pdf");

    Document document = new Document();
    try {
      LOG.info("export pdf to: {} ", System.getProperty("user.home"));
      PdfWriter.getInstance(
          document,
          new FileOutputStream(
              System.getProperty("user.home") + "/" + host.replace(".", "-") + ".pdf"));
      List<String> cpu = output.get(Constants.CPU);
      List<String> disk = output.get(Constants.DISK);
      List<String> memory = output.get(Constants.MEMORY);
      List<String> process = output.get(Constants.PROCESS);
      document.open();
      document.add(Chunk.NEWLINE);
      document.add(addChunk("Hostname: " + host));
      document.add(addParaGraph(" "));
      document.add(addChunk("CPU Report"));
      document.add(Chunk.NEWLINE);
      String headerCpu = cpu.get(0);
      document.add(addParaGraph(headerCpu));
      document.add(Chunk.NEWLINE);
      String headerDisk = disk.get(0);
      document.add(addChunk("DISK Report"));
      document.add(Chunk.NEWLINE);
      document.add(addParaGraph(headerDisk));
      document.add(Chunk.NEWLINE);
      String headerMemory = memory.get(0);
      document.add(addChunk("MEMORY Report"));
      document.add(Chunk.NEWLINE);
      document.add(addParaGraph(headerMemory));
      document.add(Chunk.NEWLINE);
      String headerProcess = process.get(0);
      document.add(addChunk("PROCESS REPORT"));
      document.add(Chunk.NEWLINE);
      document.add(addParaGraph(headerProcess));
      document.add(Chunk.NEWLINE);
    } catch (FileNotFoundException | DocumentException e) {
      LOG.error("error while creating to an output file: ", e);
    } finally {
      document.close();
    }
    LOG.info("finishing the export of the monitoring to pdf");
  }

  private Chunk addChunk(String text) {
    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    return new Chunk(text, font);
  }

  private Paragraph addParaGraph(String text) {
    return new Paragraph(text);
  }
}
