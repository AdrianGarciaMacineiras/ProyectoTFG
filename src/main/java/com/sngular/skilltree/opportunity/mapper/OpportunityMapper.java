package com.sngular.skilltree.opportunity.mapper;

import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.api.model.PatchedOpportunityDTO;
import com.sngular.skilltree.opportunity.model.Opportunity;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface OpportunityMapper {

     OpportunityDTO toOpportunityDTO(Opportunity opportunity);
     Opportunity toOpportunity(OpportunityDTO opportunityDTO);
     List<OpportunityDTO> toOpportunitiesDTO(Collection<Opportunity> opportunities);

     Opportunity toOpportunity(PatchedOpportunityDTO patchedOpportunityDTO);

     void update(@MappingTarget Opportunity oldOpportunity, Opportunity newOpportunity);
}
