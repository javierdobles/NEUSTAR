package com.neustar.app.controller;

import com.neustar.app.constants.Constants;
import com.neustar.app.service.CPUService;
import com.neustar.app.service.DiskService;
import com.neustar.app.service.MemoryService;
import com.neustar.app.service.ProcessService;
import com.neustar.app.utils.PdfUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** @author Javier Dobles */
@Component
public class MonitoringController implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(MonitoringController.class);

  private ProcessService processService;
  private CPUService cpuService;
  private MemoryService memoryService;
  private DiskService diskService;
  private PdfUtils pdfUtils;

  @Override
  public void run(String... args) throws Exception {
    if (args.length > 0) {
      LOG.info("Starting to process the hosts");
      for (String arg : args[0].split(",")) {
        LOG.info("starting with host: {} ", arg);
        List<String> process = displayRunningProcess(arg);
        List<String> cpu = displayCpuProcess(arg);
        List<String> memory = displayMemoryUsage(arg);
        List<String> disk = displayDiskSpace(arg);
        List<String> diskHuman = displayDiskSpaceHumanReadble(arg);

        Map<String, List<String>> output = new HashMap<String, List<String>>();
        output.put(Constants.PROCESS, process);
        output.put(Constants.CPU, cpu);
        output.put(Constants.MEMORY, memory);
        output.put(Constants.DISK, disk);
        output.put(Constants.DISK_HUMAN, diskHuman);

        pdfUtils.exportToPDF(arg, output);
      }

    } else {
      LOG.error(
          "please, please pass the argument with IPS, example: 192.168.0.1,192.170.23.1,170.39.42.01");
    }
  }

  public MonitoringController(
      final ProcessService processService,
      final CPUService cpuService,
      final MemoryService memoryService,
      final DiskService diskService,
      final PdfUtils pdfUtils) {
    this.processService = processService;
    this.cpuService = cpuService;
    this.memoryService = memoryService;
    this.diskService = diskService;
    this.pdfUtils = pdfUtils;
  }

  private List<String> displayRunningProcess(final String host) {
    List<String> processRunning = processService.getRunningProcess(host);

    if (processRunning != null) {

      Stream.of(processRunning)
          .forEach(
              process -> {
                LOG.info("process: \n {} ", process);
              });
    } else {
      LOG.error("unable to retrieve the list of process, something went wrong");
    }
    return processRunning;
  }

  private List<String> displayCpuProcess(final String host) {
    List<String> highCpu = cpuService.getTopThreeCpuProcess(host);
    if (highCpu != null) {

      Stream.of(highCpu)
          .forEach(
              process -> {
                LOG.info("High CPU: \r {} ", process);
              });
    } else {
      LOG.error("unable to retrieve the list of process consuming high CPU, something went wrong");
    }

    return highCpu;
  }

  private List<String> displayMemoryUsage(final String host) {
    List<String> memory = memoryService.getTopThreeMemory(host);
    if (memory != null) {

      Stream.of(memory)
          .forEach(
              process -> {
                LOG.info("High Memory: \n {} ", process);
              });
    } else {
      LOG.error(
          "unable to retrieve the list of process consuming high Memory, something went wrong");
    }

    return memory;
  }

  private List<String> displayDiskSpace(final String host) {
    List<String> diskSpace = diskService.getDiskSpace(host);
    if (diskSpace != null) {
      Stream.of(diskSpace)
          .forEach(
              diskSpaceAvailable -> {
                LOG.info("Disk Space Available: \n {} ", diskSpaceAvailable);
              });
    } else {
      LOG.error("unable to retrieve the disk space available, something went wrong");
    }

    return diskSpace;
  }

  private List<String> displayDiskSpaceHumanReadble(final String host) {
    List<String> diskSpaceHumanReadble = diskService.getDiskSpaceHumanReadable(host);
    if (diskSpaceHumanReadble != null) {
      Stream.of(diskSpaceHumanReadble)
          .forEach(
              diskSpaceAvailable -> {
                LOG.info("Disk Space Human Readable Available: \n {} ", diskSpaceAvailable);
              });
    } else {
      LOG.error("unable to retrieve the disk space available, something went wrong");
    }

    return diskSpaceHumanReadble;
  }
}
