package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


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

     @Named("patch")
     default Position patch(Position newPosition, Position oldPosition) {
          Position.PositionBuilder positionBuilder = oldPosition.toBuilder();

          return positionBuilder
                  .code(oldPosition.code())
                  .skills((Objects.isNull(newPosition.skills())) ? oldPosition.skills() : newPosition.skills())
                  .client((Objects.isNull(newPosition.client())) ? oldPosition.client() : newPosition.client())
                  .project((Objects.isNull(newPosition.project())) ? oldPosition.project() : newPosition.project())
                  .name((Objects.isNull(newPosition.name())) ? oldPosition.name() : newPosition.name())
                  .priority((Objects.isNull(newPosition.priority())) ? oldPosition.priority() : newPosition.priority())
                  .openingDate((Objects.isNull(newPosition.openingDate())) ? oldPosition.openingDate() : newPosition.openingDate())
                  .closingDate((Objects.isNull(newPosition.closingDate())) ? oldPosition.closingDate() : newPosition.closingDate())
                  .mode((Objects.isNull(newPosition.mode())) ? oldPosition.mode() : newPosition.mode())
                  .office((Objects.isNull(newPosition.office())) ? oldPosition.office() : newPosition.office())
                  .role((Objects.isNull(newPosition.role())) ? oldPosition.role() : newPosition.role())
                  .candidates(oldPosition.candidates())
                  .build();
     };
}
