package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedCandidateDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ResolveService.class, PuestoMapper.class, PeopleMapper.class})
public interface CandidateMapper {

    @Mapping(source = "candidate.code", target = "candidateCode")
    @Mapping(source = "puesto.code", target = "puestoCode")
    CandidateDTO toCandidateDTO(Candidate candidate);

    @Mapping(source = "puestoCode", target = "puesto", qualifiedByName = {"resolveService", "resolveCodePuesto"})
    @Mapping(source = "candidateCode", target = "candidate", qualifiedByName = {"resolveService", "resolveCodePeople"})
    Candidate toCandidate(CandidateDTO candidateDTO);

    List<CandidateDTO> toCandidatesDTO(Collection<Candidate> candidates);

    Candidate toCandidate(PatchedCandidateDTO patchedCandidateDTO);

    @Named("update")
    default Candidate update(Candidate newCandidate, Candidate oldCandidate) {
        Candidate.CandidateBuilder candidateBuilder = oldCandidate.toBuilder();

        Candidate candidate = candidateBuilder
                .status((newCandidate.status() == null) ? oldCandidate.status() : newCandidate.status())
                .resolutionDate((newCandidate.resolutionDate() == null) ? oldCandidate.resolutionDate() : newCandidate.resolutionDate())
                .introductionDate((newCandidate.introductionDate() == null) ? oldCandidate.introductionDate() : newCandidate.introductionDate())
                .build();

        return candidate;
    };
}
