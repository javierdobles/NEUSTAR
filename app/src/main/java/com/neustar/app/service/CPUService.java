package com.neustar.app.service;

import java.util.List;

public interface CPUService {

  public List<String> getTopThreeCpuProcess(String host);
}
