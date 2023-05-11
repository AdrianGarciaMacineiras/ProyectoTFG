package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedCandidateDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = CommonMapperConfiguration.class, uses = {ResolveService.class, PositionMapper.class, PeopleMapper.class})
public interface CandidateMapper {

    @Mapping(source = "candidate.code", target = "candidateCode")
    @Mapping(source = "position.code", target = "positionCode")
    CandidateDTO toCandidateDTO(Candidate candidate);

    @Mapping(source = "positionCode", target = "position", qualifiedByName = {"resolveCodePosition"})
    @Mapping(source = "candidateCode", target = "candidate", qualifiedByName = {"resolveCodePeople"})
    Candidate toCandidate(CandidateDTO candidateDTO);

    List<CandidateDTO> toCandidatesDTO(Collection<Candidate> candidates);

    Candidate toCandidate(PatchedCandidateDTO patchedCandidateDTO);

    @Named("update")
    default Candidate update(Candidate newCandidate, Candidate oldCandidate) {
        Candidate.CandidateBuilder candidateBuilder = oldCandidate.toBuilder();

        return candidateBuilder
                .status((Objects.isNull(newCandidate.status())) ? oldCandidate.status() : newCandidate.status())
                .resolutionDate((Objects.isNull(newCandidate.resolutionDate())) ? oldCandidate.resolutionDate() : newCandidate.resolutionDate())
                .introductionDate((Objects.isNull(newCandidate.introductionDate())) ? oldCandidate.introductionDate() : newCandidate.introductionDate())
                .interviewDate((Objects.isNull(newCandidate.interviewDate())) ? oldCandidate.interviewDate() : newCandidate.interviewDate())
                .build();
    }
}
