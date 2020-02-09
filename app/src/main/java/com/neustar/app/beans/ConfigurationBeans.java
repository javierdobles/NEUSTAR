package com.neustar.app.beans;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/** @author Javier Dobles */
@Configuration
@PropertySource("classpath:ssh.properties")
public class ConfigurationBeans {

  @Value("${com.neustar.ssh.port}")
  private Integer port;

  @Value("${com.neustar.ssh.username}")
  private String userName;

  @Value("${com.neustar.ssh.password}")
  private String password;

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public Session session(final String host) throws JSchException {
    // int portInt = Integer.valueOf(22);
    Session session = new JSch().getSession(userName, host, port);
    session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
    session.setConfig("StrictHostKeyChecking", "no");
    session.setPassword(password);

    return session;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
