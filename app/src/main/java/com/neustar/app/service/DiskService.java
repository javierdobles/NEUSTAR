package com.neustar.app.service;

import java.util.List;

public interface DiskService {

  public List<String> getDiskSpace(String host);

  public List<String> getDiskSpaceHumanReadable(String host);
}
