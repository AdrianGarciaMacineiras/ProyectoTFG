package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.api.model.OpportunitySkillDTO;
import com.sngular.skilltree.api.model.PatchedOpportunityDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.SkillNodeMapper;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.OpportunitySkill;
import com.sngular.skilltree.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {ClientMapper.class, SkillMapper.class, ResolveService.class}, componentModel = "spring")
public interface OpportunityMapper {

     @Mapping(source = "project.code", target = "projectCode")
     @Mapping(source = "client.code", target = "clientCode")
     @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
     @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
     OpportunityDTO toOpportunityDTO(Opportunity opportunity);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveSkillCode"})
     OpportunitySkillDTO toOpportunitySkillsDTO(OpportunitySkill opportunitySkill);

     @Mapping(target = "skill", source = "skill", qualifiedByName = {"resolveService", "resolveCodeToSkill"})
     OpportunitySkill toOpportunitySkills(OpportunitySkillDTO opportunitySkill);

     Opportunity toOpportunity(OpportunityDTO opportunityDTO);

     List<OpportunityDTO> toOpportunitiesDTO(Collection<Opportunity> opportunities);

     @Mapping(target = "skills", ignore = true)
     Opportunity toOpportunity(PatchedOpportunityDTO patchedOpportunityDTO);

     void update(@MappingTarget Opportunity oldOpportunity, Opportunity newOpportunity);
}
