package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.PatchedPeopleDTO;
import com.sngular.skilltree.api.model.PeopleDTO;
import com.sngular.skilltree.model.People;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

    PeopleDTO toPersonDTO(People people);

    People toPerson(PeopleDTO peopleDTO);

    List<PeopleDTO> toPeopleDto(Collection<People> people);

    People toPeople (PatchedPeopleDTO patchedPersonDTO);

    void update(@MappingTarget People oldPeople, People newPeople);
}
