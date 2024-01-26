package com.tfg.skilltree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SkillTreeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkillTreeApplication.class, args);
  }

}
