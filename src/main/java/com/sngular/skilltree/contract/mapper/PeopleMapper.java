package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.api.model.AssignmentDTO;
import com.sngular.skilltree.api.model.CertificateDTO;
import com.sngular.skilltree.api.model.PatchedPeopleDTO;
import com.sngular.skilltree.api.model.PeopleDTO;
import com.sngular.skilltree.api.model.RolesDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Assignment;
import com.sngular.skilltree.model.Certificate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = CommonMapperConfiguration.class, uses = {SkillMapper.class, CandidateMapper.class, ResolveService.class})
public interface PeopleMapper {

  @Mapping(target = "work_with", source = "work_with", qualifiedByName = {"resolveSkillNameList"})
  @Mapping(target = "master", source = "master", qualifiedByName = {"resolveSkillNameList"})
  @Mapping(target = "interest", source = "interest", qualifiedByName = {"resolveSkillNameList"})
  @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
  PeopleDTO toPersonDTO(People people);

  @Mapping(source = "work_with", target = "work_with", qualifiedByName = {"resolveNameSkillList"})
  @Mapping(source = "master", target = "master", qualifiedByName = {"resolveNameSkillList"})
  @Mapping(source = "interest", target = "interest", qualifiedByName = {"resolveNameSkillList"})
  @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
  People toPerson(PeopleDTO peopleDTO);

  List<PeopleDTO> toPeopleDto(Collection<People> people);

  @Mapping(source = "work_with", target = "work_with", qualifiedByName = {"resolveCodeSkillList"})
  @Mapping(source = "master", target = "master", qualifiedByName = {"resolveCodeSkillList"})
  @Mapping(source = "interest", target = "interest", qualifiedByName = {"resolveCodeSkillList"})
  @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
  People toPeople(PatchedPeopleDTO patchedPersonDTO);

  @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
  RolesDTO roleToRolesDTO(Role role);

  @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "assignDate", dateFormat = "dd-MM-yyyy")
  Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO);

  @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "assignDate", dateFormat = "dd-MM-yyyy")
  AssignmentDTO assignmentToAssignmentDTO(Assignment assignment);

  @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
  Role rolesDTOToRole(RolesDTO rolesDTO);

  @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
  CertificateDTO certificateToCertificateDTO(Certificate certificate);

  @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
  Certificate certificateDTOToCertificate(CertificateDTO certificateDTO);

  @Named("patch")
  default People patch(People newPeople, People oldPeople) {

        People.PeopleBuilder peopleBuilder = oldPeople.toBuilder();

        return peopleBuilder
                .code(oldPeople.code())
                .employeeId((Objects.isNull(newPeople.employeeId())) ? oldPeople.employeeId() : newPeople.employeeId())
                .birthDate((Objects.isNull(newPeople.birthDate())) ? oldPeople.birthDate() : newPeople.birthDate())
                .name((Objects.isNull(newPeople.name())) ? oldPeople.name() : newPeople.name())
                .surname((Objects.isNull(newPeople.surname())) ? oldPeople.surname() : newPeople.surname())
                .title((Objects.isNull(newPeople.title())) ? oldPeople.title() : newPeople.title())
                .certificates((Objects.isNull(newPeople.certificates())) ? oldPeople.certificates() : newPeople.certificates())
                .interest((Objects.isNull(newPeople.interest())) ? oldPeople.interest() : newPeople.interest())
                .knows((Objects.isNull(newPeople.knows())) ? oldPeople.knows() : newPeople.knows())
                .master((Objects.isNull(newPeople.master())) ? oldPeople.master() : newPeople.master())
                .work_with((Objects.isNull(newPeople.work_with())) ? oldPeople.work_with() : newPeople.work_with())
                .roles((Objects.isNull(newPeople.roles())) ? oldPeople.roles() : newPeople.roles())
                .assignable(newPeople.assignable())
                .assigns((Objects.isNull(newPeople.assigns())) ? oldPeople.assigns() : newPeople.assigns())
                .build();

  }

    default String toPeopleCode(final People people) {
      return people.code();
    }
}
