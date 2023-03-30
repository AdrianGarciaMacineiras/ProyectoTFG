package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;


import com.sngular.skilltree.api.model.PatchedPositionDTO;
import com.sngular.skilltree.api.model.PositionDTO;
import com.sngular.skilltree.api.model.PositionSkillDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionSkill;
import org.mapstruct.*;

@Mapper(uses = {ClientMapper.class, SkillMapper.class, ResolveService.class, PeopleMapper.class, ProjectMapper.class,
        OfficeMapper.class, CandidateMapper.class}, componentModel = "spring")
public interface PositionMapper {

     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target= "projectCode", source = "project.code")
     @Mapping(target= "clientCode", source = "client.code")
     @Mapping(target = "office", source = "office.code")
     PositionDTO toPositionDTO(Position position);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveSkillCode"})
     PositionSkillDTO toPositionSkillsDTO(PositionSkill positionSkill);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveCodeToSkill"})
     PositionSkill toPositionSkills(PositionSkillDTO opportunitySkill);

     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "project", source = "projectCode", qualifiedByName = {"resolveService", "resolveCodeProject"})
     @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveService", "resolveCodePeople"})
     @Mapping(target = "office", source = "office", qualifiedByName = {"resolveService", "resolveCodeOffice"})
     @Mapping(target = "client", source = "clientCode", qualifiedByName = {"resolveService", "resolveCodeClient"})
     Position toPosition(PositionDTO opportunityDTO);

     List<PositionDTO> toPositionsDTO(Collection<Position> positions);

     @Mapping(target = "skills", ignore = true)
     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "office", source = "office", qualifiedByName = {"resolveService", "resolveCodeOffice"})
     @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveService", "resolveCodePeople"})
     Position toPosition(PatchedPositionDTO patchedPuestoDTO);

     @Named("update")
     default Position update(Position newPosition, Position oldPosition) {
          Position.PositionBuilder opportunityBuilder = oldPosition.toBuilder();

          Position position = opportunityBuilder
                  .code(oldPosition.code())
                  .skills((newPosition.skills() == null) ? oldPosition.skills() : newPosition.skills())
                  .client((newPosition.client() == null) ? oldPosition.client() : newPosition.client())
                  .project((newPosition.project() == null) ? oldPosition.project() : newPosition.project())
                  .name((newPosition.name() == null) ? oldPosition.name() : newPosition.name())
                  .priority((newPosition.priority() == null) ? oldPosition.priority() : newPosition.priority())
                  .openingDate((newPosition.openingDate() == null) ? oldPosition.openingDate() : newPosition.openingDate())
                  .closingDate((newPosition.closingDate() == null) ? oldPosition.closingDate() : newPosition.closingDate())
                  .mode((newPosition.mode() == null) ? oldPosition.mode() : newPosition.mode())
                  .office((newPosition.office() == null) ? oldPosition.office() : newPosition.office())
                  .role((newPosition.role() == null) ? oldPosition.role() : newPosition.role())
                  .build();

          return position;
     };
}
