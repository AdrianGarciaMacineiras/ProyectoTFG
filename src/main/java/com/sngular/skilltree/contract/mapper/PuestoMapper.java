package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.sngular.skilltree.api.model.PatchedPuestoDTO;
import com.sngular.skilltree.api.model.PuestoDTO;
import com.sngular.skilltree.api.model.PuestoSkillDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Puesto;
import com.sngular.skilltree.model.PuestoSkill;
import org.mapstruct.*;

@Mapper(uses = {ClientMapper.class, SkillMapper.class, ResolveService.class, PeopleMapper.class, ProjectMapper.class,
        OfficeMapper.class, CandidateMapper.class}, componentModel = "spring")
public interface PuestoMapper {

     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target= "projectCode", source = "project.code")
     @Mapping(target= "clientCode", source = "client.code")
     @Mapping(target = "office", source = "office.code")
     PuestoDTO toPuestoDTO(Puesto puesto);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveSkillCode"})
     PuestoSkillDTO toPuestoSkillsDTO(PuestoSkill puestoSkill);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveCodeToSkill"})
     PuestoSkill toPuestoSkills(PuestoSkillDTO opportunitySkill);

     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "project", source = "projectCode", qualifiedByName = {"resolveService", "resolveCodeProject"})
     @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveService", "resolveCodePeople"})
     @Mapping(target = "office", source = "office", qualifiedByName = {"resolveService", "resolveCodeOffice"})
     @Mapping(target = "client", source = "clientCode", qualifiedByName = {"resolveService", "resolveCodeClient"})
     Puesto toPuesto(PuestoDTO opportunityDTO);

     List<PuestoDTO> toPuestosDTO(Collection<Puesto> puestos);

     @Mapping(target = "skills", ignore = true)
     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "office", source = "office", qualifiedByName = {"resolveService", "resolveCodeOffice"})
     @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveService", "resolveCodePeople"})
     Puesto toPuesto(PatchedPuestoDTO patchedPuestoDTO);

     @Named("update")
     default Puesto update(Puesto newPuesto, Puesto oldPuesto) {
          Puesto.PuestoBuilder opportunityBuilder = oldPuesto.toBuilder();

          Puesto puesto = opportunityBuilder
                  .code(oldPuesto.code())
                  .skills((newPuesto.skills() == null) ? oldPuesto.skills() : newPuesto.skills())
                  .client((newPuesto.client() == null) ? oldPuesto.client() : newPuesto.client())
                  .project((newPuesto.project() == null) ? oldPuesto.project() : newPuesto.project())
                  .name((newPuesto.name() == null) ? oldPuesto.name() : newPuesto.name())
                  .priority((newPuesto.priority() == null) ? oldPuesto.priority() : newPuesto.priority())
                  .openingDate((newPuesto.openingDate() == null) ? oldPuesto.openingDate() : newPuesto.openingDate())
                  .closingDate((newPuesto.closingDate() == null) ? oldPuesto.closingDate() : newPuesto.closingDate())
                  .mode((newPuesto.mode() == null) ? oldPuesto.mode() : newPuesto.mode())
                  .office((newPuesto.office() == null) ? oldPuesto.office() : newPuesto.office())
                  .role((newPuesto.role() == null) ? oldPuesto.role() : newPuesto.role())
                  .build();

          return puesto;
     };
}
