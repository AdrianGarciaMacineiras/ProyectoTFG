package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.PatchedPeopleDTO;
import com.sngular.skilltree.api.model.PeopleDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.People;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, ResolveService.class})
public interface PeopleMapper {

    PeopleDTO toPersonDTO(People people);

    @Mapping(source = "work_with", target = "work_with", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    @Mapping(source = "master", target = "master", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    @Mapping(source = "interest", target = "interest", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    People toPerson(PeopleDTO peopleDTO);

    List<PeopleDTO> toPeopleDto(Collection<People> people);

    @Mapping(source = "work_with", target = "work_with", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    @Mapping(source = "master", target = "master", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    @Mapping(source = "interest", target = "interest", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    People toPeople (PatchedPeopleDTO patchedPersonDTO);

    void update(@MappingTarget People oldPeople, People newPeople);
}
