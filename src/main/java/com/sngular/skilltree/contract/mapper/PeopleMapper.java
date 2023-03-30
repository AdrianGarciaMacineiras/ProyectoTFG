package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.*;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.*;
import org.mapstruct.*;

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
    Assigns assignsDTOToAssigns(AssignsDTO assignsDTO);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    AssignsDTO assignsToAssignsDTO(Assigns assigns);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    Role rolesDTOToRole(RolesDTO rolesDTO);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    CertificateDTO certificateToCertificateDTO(Certificate certificate);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    Certificate certificateDTOToCertificate(CertificateDTO certificateDTO);

    @Named("update")
    default People update(People newPeople, People oldPeople) {
        People.PeopleBuilder peopleBuilder = oldPeople.toBuilder();

        People people = peopleBuilder
                .code(oldPeople.code())
                .employeeId((newPeople.employeeId() == null) ? oldPeople.employeeId() : newPeople.employeeId())
                .birthDate((newPeople.birthDate() == null) ? oldPeople.birthDate() : newPeople.birthDate())
                .name((newPeople.name() == null) ? oldPeople.name() : newPeople.name())
                .surname((newPeople.surname() == null) ? oldPeople.surname() : newPeople.surname())
                .title((newPeople.title() == null) ? oldPeople.title() : newPeople.title())
                .certificates((newPeople.certificates() == null) ? oldPeople.certificates() : newPeople.certificates())
                .interest((newPeople.interest() == null) ? oldPeople.interest() : newPeople.interest())
                .knows((newPeople.knows() == null) ? oldPeople.knows() : newPeople.knows())
                .master((newPeople.master() == null) ? oldPeople.master() : newPeople.master())
                .work_with((newPeople.work_with() == null) ? oldPeople.work_with() : newPeople.work_with())
                .roles((newPeople.roles() == null) ? oldPeople.roles() : newPeople.roles())
                .build();

        return people;
    };

    default Long toPeopleCode(final People people) {
        return people.code();
    }
}
