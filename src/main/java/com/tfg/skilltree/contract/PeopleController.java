package com.tfg.skilltree.contract;

import java.util.List;

import com.tfg.skilltree.api.PeopleApi;
import com.tfg.skilltree.api.PersonApi;
import com.tfg.skilltree.api.model.CandidateDTO;
import com.tfg.skilltree.api.model.PatchedPeopleDTO;
import com.tfg.skilltree.api.model.PeopleDTO;
import com.tfg.skilltree.api.model.PeopleNamesDTO;
import com.tfg.skilltree.api.model.PeopleResumedDTO;
import com.tfg.skilltree.api.model.PeopleSkillDTO;
import com.tfg.skilltree.api.model.PositionDTO;
import com.tfg.skilltree.application.PeopleService;
import com.tfg.skilltree.application.updater.PeopleUpdater;
import com.tfg.skilltree.contract.mapper.CandidateMapper;
import com.tfg.skilltree.contract.mapper.PeopleMapper;
import com.tfg.skilltree.contract.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PeopleController implements PeopleApi, PersonApi {

  private final PeopleService peopleService;

  private final PeopleUpdater peopleUpdater;

  private final PeopleMapper peopleMapper;

  private final CandidateMapper candidateMapper;

  private final PositionMapper positionMapper;

    @Override
    public ResponseEntity<PeopleDTO> getPersonByCode(String peopleCode) {
        final var aux = peopleMapper
                .toPersonDTO(peopleService
                        .findByCode(peopleCode));
        return ResponseEntity.ok(aux);
    }

    @Override
    public ResponseEntity<Void> deletePerson(String peopleCode) {
        peopleService.deleteByCode(peopleCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<PeopleDTO> updatePerson(String peopleCode, PeopleDTO peopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleUpdater
                        .update(peopleCode, peopleMapper
                                .toPerson(peopleDTO))));
    }

    @Override
    public ResponseEntity<PeopleDTO> patchPerson(String personCode, PatchedPeopleDTO patchedPeopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleUpdater
                        .patch(personCode, peopleMapper
                                .toPeople(patchedPeopleDTO))));
    }

    @Override
    public ResponseEntity<List<PeopleDTO>> getPeople() {
        var people = peopleService.getAll();
        return ResponseEntity.ok(peopleMapper.toPeopleDto(people));
    }

    @Override
    public ResponseEntity<PeopleDTO> addPerson(PeopleDTO peopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleService
                        .create(peopleMapper
                                .toPerson(peopleDTO))));
    }

    @Override
    public ResponseEntity<PeopleDTO> assignCandidate(String peopleCode, String positionCode) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleService
                        .assignCandidate(peopleCode, positionCode)));
    }

    @Override
    public ResponseEntity<List<CandidateDTO>> getPeopleCandidates(String peopleCode) {
        return ResponseEntity.ok(candidateMapper
                .toCandidatesDTO(peopleService
                        .getCandidates(peopleCode)));
    }

    @Override
    public ResponseEntity<List<PeopleDTO>> getPeopleSkills(final List<PeopleSkillDTO> listPeopleSkillDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPeopleDto(peopleService
                                 .getPeopleBySkills(peopleMapper.mapPeopleSkill(listPeopleSkillDTO))));
    }

    @Override
    public ResponseEntity<List<PeopleDTO>> getOtherPeopleStrategicSkills(String teamCode) {
        return ResponseEntity.ok(peopleMapper
                .toPeopleDto(peopleService
                        .getOtherPeopleStrategicSkills(teamCode)));
    }

    @Override
    public ResponseEntity<List<PositionDTO>> getPeopleAssignedPositions(String peopleCode) {
        return ResponseEntity.ok(positionMapper
                .toPositionsDTO(peopleService
                        .getPeopleAssignedPositions(peopleCode)));
    }

    @Override
    public ResponseEntity<List<PeopleResumedDTO>> getPeopleResume() {
        return ResponseEntity.ok(peopleMapper.toPeopleResumedDto(peopleService.getAllResumed()));
    }

    @Override
    public ResponseEntity<List<PeopleNamesDTO>> getPeopleNames() {
        return ResponseEntity.ok(peopleMapper.toPeopleNamesDto(peopleService.getAllNames()));
    }
}