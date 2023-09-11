package com.sngular.skilltree.common.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.NullValueMappingStrategy;

@MapperConfig(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = ComponentModel.SPRING, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CommonMapperConfiguration {
}
