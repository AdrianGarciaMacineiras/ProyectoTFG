package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.api.model.PatchedOpportunityDTO;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.SkillNodeMapper;
import com.sngular.skilltree.model.Opportunity;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {ClientMapper.class, SkillNodeMapper.class, SkillMapper.class}, componentModel = "spring")
public interface OpportunityMapper {

     OpportunityDTO toOpportunityDTO(Opportunity opportunity);

     Opportunity toOpportunity(OpportunityDTO opportunityDTO);
     List<OpportunityDTO> toOpportunitiesDTO(Collection<Opportunity> opportunities);

     Opportunity toOpportunity(PatchedOpportunityDTO patchedOpportunityDTO);

     void update(@MappingTarget Opportunity oldOpportunity, Opportunity newOpportunity);
}
