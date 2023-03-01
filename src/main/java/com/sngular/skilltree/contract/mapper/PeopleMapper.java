package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.*;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.*;
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

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    RolesDTO roleToRolesDTO(Role role);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    Roles roleDTOToRoles(RoleDTO roleDTO);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    RoleDTO rolesToRoleDTO(Roles roles);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    Role rolesDTOToRole(RolesDTO rolesDTO);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    CertificateDTO certificateToCertificateDTO(Certificate certificate);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    Certificate certificateDTOToCertificate(CertificateDTO certificateDTO);

    @Mapping(target = "name", source = "code")
    ParticipateDTO participateToParticipateDTO(Participate participate);

    @Mapping(target = "code", source = "name")
    Participate participateDTOToParticipate(ParticipateDTO participateDTO);

    void update(@MappingTarget People oldPeople, People newPeople);

    default String toPeopleCode(final People people) {
        return people.code();
    }
}
