package com.tfg.skilltree.common.config;

import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "assignable.configuration")
public class AssignableConfiguration {

  private List<String> titles;

  private List<String> jobCodeList;

  private List<String> shortTeamNames;
}
