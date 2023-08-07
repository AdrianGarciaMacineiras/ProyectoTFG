package com.sngular.skilltree.contract.mapper;

import java.util.List;

import com.sngular.skilltree.api.model.OfficeDTO;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Office;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = CommonMapperConfiguration.class)
public interface OfficeMapper {

  OfficeDTO toOfficeDto(Office office);

    Office toOffice(OfficeDTO officeDTO);

    List<OfficeDTO> toOfficesDTO(List<Office> offices);

    void update(@MappingTarget Office oldOffice, Office newOffice);
}
