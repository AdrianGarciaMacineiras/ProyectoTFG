package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.PatchedPeopleDTO;
import com.sngular.skilltree.api.model.PeopleDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.EnumTitle;
import com.sngular.skilltree.model.People;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ValueMapping;
import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, ResolveService.class})
public interface PeopleMapper {

    @Mapping(target = "work_with", source = "work_with", qualifiedByName = {"resolveService", "resolveSkillCodeList"})
    @Mapping(target = "master", source = "master", qualifiedByName = {"resolveService", "resolveSkillCodeList"})
    @Mapping(target = "interest", source = "interest", qualifiedByName = {"resolveService", "resolveSkillCodeList"})
    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    PeopleDTO toPersonDTO(People people);

    @Mapping(source = "work_with", target = "work_with", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(source = "master", target = "master", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(source = "interest", target = "interest", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    People toPerson(PeopleDTO peopleDTO);

    List<PeopleDTO> toPeopleDto(Collection<People> people);

    @Mapping(source = "work_with", target = "work_with", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(source = "master", target = "master", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(source = "interest", target = "interest", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    People toPeople (PatchedPeopleDTO patchedPersonDTO);

    void update(@MappingTarget People oldPeople, People newPeople);

    default String toPeopleCode(final People people) {
        return people.code();
    }
}
