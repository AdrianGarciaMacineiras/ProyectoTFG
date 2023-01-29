package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedCandidateDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ResolveService.class, OpportunityMapper.class, PeopleMapper.class})
public interface CandidateMapper {

    CandidateDTO toCandidateDTO(Candidate candidate);

    @Mapping(source = "opportunityCode", target = "opportunity", qualifiedByName = {"resolveService", "resolveCodeOpportunity"})
    @Mapping(source = "candidateCode", target = "candidate", qualifiedByName = {"resolveService", "resolveCodePeople"})
    Candidate toCandidate(CandidateDTO candidateDTO);

    List<CandidateDTO> toCandidatesDTO(Collection<Candidate> candidates);

    Candidate toCandidate(PatchedCandidateDTO patchedCandidateDTO);

    void update(@MappingTarget Candidate oldCandidate, Candidate newCandidate);
}
