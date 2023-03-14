package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedCandidateDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ResolveService.class, OpportunityMapper.class, PeopleMapper.class})
public interface CandidateMapper {

    @Mapping(source = "candidate.code", target = "candidateCode")
    @Mapping(source = "opportunity.code", target = "opportunityCode")
    CandidateDTO toCandidateDTO(Candidate candidate);

    @Mapping(source = "opportunityCode", target = "opportunity", qualifiedByName = {"resolveService", "resolveCodeOpportunity"})
    @Mapping(source = "candidateCode", target = "candidate", qualifiedByName = {"resolveService", "resolveCodePeople"})
    Candidate toCandidate(CandidateDTO candidateDTO);

    List<CandidateDTO> toCandidatesDTO(Collection<Candidate> candidates);

    Candidate toCandidate(PatchedCandidateDTO patchedCandidateDTO);

    @Named("update")
    default Candidate update(Candidate newCandidate, Candidate oldCandidate) {
        Candidate.CandidateBuilder candidateBuilder = oldCandidate.toBuilder();

        Candidate candidate = candidateBuilder
                .code(oldCandidate.code())
                .build();

        return candidate;
    };
}
