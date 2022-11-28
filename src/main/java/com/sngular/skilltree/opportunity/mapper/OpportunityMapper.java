package com.sngular.skilltree.opportunity.mapper;

import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.opportunity.model.Opportunity;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface OpportunityMapper {

     OpportunityDTO toOpportunityDTO(Opportunity opportunity);
     Opportunity toOpportunity(OpportunityDTO opportunityDTO);
     List<OpportunityDTO> toOpportunitiesDto(Collection<Opportunity> opportunities);

}
