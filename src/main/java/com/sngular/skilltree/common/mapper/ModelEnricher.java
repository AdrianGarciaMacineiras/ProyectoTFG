package com.sngular.skilltree.common.mapper;

import com.sngular.skilltree.application.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelEnricher {

  private final SkillService skillService;
}
