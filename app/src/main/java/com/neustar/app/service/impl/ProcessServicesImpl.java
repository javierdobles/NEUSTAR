package com.neustar.app.service.impl;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.neustar.app.constants.Constants;
import com.neustar.app.service.ProcessService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/** @author Javier Dobles */
@Service
public class ProcessServicesImpl implements ProcessService {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessServicesImpl.class);

  private AnnotationConfigApplicationContext context;

  @Override
  public List<String> getRunningProcess(final String host) {
    Session session = context.getBean(Session.class, host);
    List<String> result = null;
    try {
      ChannelExec channel = null;
      session.connect(Constants.TIME_OUT);
      channel = (ChannelExec) session.openChannel("exec");
      channel.setCommand("ps -ef");
      channel.setInputStream(null);
      InputStream output = channel.getInputStream();
      channel.connect(Constants.TIME_OUT);

      String text = IOUtils.toString(output, StandardCharsets.UTF_8.name());
      channel.disconnect();
      result = Stream.of(text.split(",")).collect(Collectors.toList());

    } catch (JSchException | IOException e) {
      LOG.error("something goes wrong with the execution of the command, please contact an admin");

    } finally {

      session.disconnect();
    }
    return result;
  }

  /**
   * Process Services Constructor.
   *
   * @param context instance of {@link AnnotationConfigApplicationContext}.
   */
  public ProcessServicesImpl(final AnnotationConfigApplicationContext context) {
    this.context = context;
  }
}
