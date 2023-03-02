package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.api.model.OpportunitySkillDTO;
import com.sngular.skilltree.api.model.PatchedOpportunityDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.OpportunitySkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = {ClientMapper.class, SkillMapper.class, ResolveService.class, PeopleMapper.class, ProjectMapper.class, OfficeMapper.class}, componentModel = "spring")
public interface OpportunityMapper {

     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target= "projectCode", source = "project.code")
     @Mapping(target= "clientCode", source = "client.code")
     @Mapping(target = "office", source = "office.name")
     OpportunityDTO toOpportunityDTO(Opportunity opportunity);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveSkillCode"})
     OpportunitySkillDTO toOpportunitySkillsDTO(OpportunitySkill opportunitySkill);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveCodeToSkill"})
     OpportunitySkill toOpportunitySkills(OpportunitySkillDTO opportunitySkill);

     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "project", source = "projectCode", qualifiedByName = {"resolveService", "resolveCodeProject"})
     @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveService", "resolveCodePeople"})
     @Mapping(target = "office", source = "office", qualifiedByName = {"resolveService", "resolveCodeOffice"})
     @Mapping(target = "client", source = "clientCode", qualifiedByName = {"resolveService", "resolveCodeClient"})
     Opportunity toOpportunity(OpportunityDTO opportunityDTO);

     List<OpportunityDTO> toOpportunitiesDTO(Collection<Opportunity> opportunities);

     @Mapping(target = "skills", ignore = true)
     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "office", source = "office", qualifiedByName = {"resolveService", "resolveCodeOffice"})
     @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveService", "resolveCodePeople"})
     Opportunity toOpportunity(PatchedOpportunityDTO patchedOpportunityDTO);

     //void update(@MappingTarget Opportunity oldOpportunity, Opportunity newOpportunity);

     @Named("update")
     default Opportunity update(Opportunity newOpportunity, Opportunity oldOpportunity) {
          Opportunity.OpportunityBuilder opportunityBuilder = oldOpportunity.toBuilder();

          Opportunity opportunity = opportunityBuilder
                  .code(oldOpportunity.code())
                  .skills((newOpportunity.skills() == null) ? oldOpportunity.skills() : newOpportunity.skills())
                  .client((newOpportunity.client() == null) ? oldOpportunity.client() : newOpportunity.client())
                  .project((newOpportunity.project() == null) ? oldOpportunity.project() : newOpportunity.project())
                  .name((newOpportunity.name() == null) ? oldOpportunity.name() : newOpportunity.name())
                  .priority((newOpportunity.priority() == null) ? oldOpportunity.priority() : newOpportunity.priority())
                  .openingDate((newOpportunity.openingDate() == null) ? oldOpportunity.openingDate() : newOpportunity.openingDate())
                  .closingDate((newOpportunity.closingDate() == null) ? oldOpportunity.closingDate() : newOpportunity.closingDate())
                  .mode((newOpportunity.mode() == null) ? oldOpportunity.mode() : newOpportunity.mode())
                  .office((newOpportunity.office() == null) ? oldOpportunity.office() : newOpportunity.office())
                  .role((newOpportunity.role() == null) ? oldOpportunity.role() : newOpportunity.role())
                  .build();

          return opportunity;
     };
}
