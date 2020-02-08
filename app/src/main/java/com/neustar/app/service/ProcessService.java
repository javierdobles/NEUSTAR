package com.neustar.app.service;

import java.util.List;

public interface ProcessService {

  public List<String> getRunningProcess(String host);
}
