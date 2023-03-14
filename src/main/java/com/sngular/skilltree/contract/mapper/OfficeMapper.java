package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.OfficeDTO;
import com.sngular.skilltree.model.Office;
import org.mapstruct.MappingTarget;

public interface OfficeMapper {

    OfficeDTO toOfficeDto(Office office);

    Office toOffice(OfficeDTO officeDTO);

    void update(@MappingTarget Office oldOffice, Office newOffice);
}
