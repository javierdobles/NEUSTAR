package com.neustar.app.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class DiskServiceImplTest {

  @InjectMocks DiskServiceImpl diskService;

  @Mock private AnnotationConfigApplicationContext context;
  @Mock Session session;

  @Mock ChannelExec channel;

  InputStream inputstream = null;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    inputstream = IOUtils.toInputStream("some test data, for my input stream", "UTF-8");
  }

  @SuppressWarnings("unchecked")
  @Test
  void getDiskSpace() throws JSchException, IOException {
    when(context.getBean(any(Class.class), anyString())).thenReturn(session);
    doNothing().when(session).connect(anyInt());
    when(session.openChannel(anyString())).thenReturn(channel);
    when(channel.getInputStream()).thenReturn(inputstream);
    doNothing().when(channel).connect(anyInt());
    doNothing().when(session).disconnect();
    doNothing().when(channel).disconnect();

    List<String> diskSpace = diskService.getDiskSpace("TEST");

    assertEquals(diskSpace.size(), 2);
  }

  @SuppressWarnings("unchecked")
  @Test
  void getDiskSpaceHumanReadable() throws JSchException, IOException {
    when(context.getBean(any(Class.class), anyString())).thenReturn(session);
    doNothing().when(session).connect(anyInt());
    when(session.openChannel(anyString())).thenReturn(channel);
    when(channel.getInputStream()).thenReturn(inputstream);
    doNothing().when(channel).connect(anyInt());
    doNothing().when(session).disconnect();
    doNothing().when(channel).disconnect();

    List<String> diskSpaceHuman = diskService.getDiskSpaceHumanReadable("TEST");

    assertEquals(diskSpaceHuman.size(), 2);
  }
}
