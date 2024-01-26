package com.tfg.skilltree.contract.mapper;

import java.util.List;

import com.tfg.skilltree.api.model.OfficeDTO;
import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.model.Office;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = CommonMapperConfiguration.class)
public interface OfficeMapper {

  OfficeDTO toOfficeDto(Office office);

    Office toOffice(OfficeDTO officeDTO);

    List<OfficeDTO> toOfficesDTO(List<Office> offices);

    void update(@MappingTarget Office oldOffice, Office newOffice);
}
