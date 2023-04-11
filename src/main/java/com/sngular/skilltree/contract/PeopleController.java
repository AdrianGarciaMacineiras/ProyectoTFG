package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.PeopleApi;
import com.sngular.skilltree.api.model.PatchedPeopleDTO;
import com.sngular.skilltree.api.model.PeopleDTO;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.updater.PeopleUpdater;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeopleController implements PeopleApi {

    private final PeopleService peopleService;

    private final PeopleUpdater peopleUpdater;

    private final PeopleMapper peopleMapper;

    @Override
    public ResponseEntity<PeopleDTO> getPersonByCode(Long peoplecode) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleService
                        .findByCode(peoplecode)));
    }

    @Override
    public ResponseEntity<Void> deletePerson(Long peoplecode) {
        peopleService.deleteByCode(peoplecode);
        return ResponseEntity.status(HttpStatus.OK).build();    }

    @Override
    public ResponseEntity<PeopleDTO> updatePerson(Long peoplecode, PeopleDTO peopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleUpdater
                        .update(peoplecode, peopleMapper
                                .toPerson(peopleDTO))));
    }

    @Override
    public ResponseEntity<PeopleDTO> patchPerson(Long personcode, PatchedPeopleDTO patchedPeopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleUpdater
                        .patch(personcode, peopleMapper
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
    public ResponseEntity<PeopleDTO> assignCandidate(Long peopleCode, String positionCode){
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleService
                        .assignCandidate(peopleCode,positionCode)));
    }
}
