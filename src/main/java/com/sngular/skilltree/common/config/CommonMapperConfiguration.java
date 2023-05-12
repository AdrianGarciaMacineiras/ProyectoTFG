package com.sngular.skilltree.common.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants.ComponentModel;

@MapperConfig(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = ComponentModel.SPRING)
public interface CommonMapperConfiguration {
}
