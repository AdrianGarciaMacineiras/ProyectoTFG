package com.sngular.skilltree.person.controller;

import com.sngular.skilltree.api.PeopleApi;
import com.sngular.skilltree.api.model.PatchedPersonDTO;
import com.sngular.skilltree.api.model.PersonDTO;
import com.sngular.skilltree.person.mapper.PersonMapper;
import com.sngular.skilltree.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController implements PeopleApi {

    private final PersonService personService;

    private final PersonMapper personMapper;

    @Override
    public ResponseEntity<PersonDTO> getPersonByCode(String personcode) {
        return ResponseEntity.ok(personMapper
                .toPersonDTO(personService
                        .findByCode(personcode)));
    }

    @Override
    public ResponseEntity<Void> deletePerson(String personcode) {
        var result = personService.deleteByCode(personcode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();    }

    @Override
    public ResponseEntity<PersonDTO> updatePerson(String personcode, PersonDTO personDTO) {
        return ResponseEntity.ok(personMapper
                .toPersonDTO(personService
                        .update(personcode, personMapper
                                .toPerson(personDTO))));
    }

    @Override
    public ResponseEntity<PersonDTO> patchPerson(String personcode, PatchedPersonDTO patchedPersonDTO) {
        return ResponseEntity.ok(personMapper
                .toPersonDTO(personService
                        .patch(personcode, personMapper
                                .toPerson(patchedPersonDTO))));
    }

    @Override
    public ResponseEntity<List<PersonDTO>> getPeople() {
        var people = personService.getAll();
        return ResponseEntity.ok(personMapper.toPeopleDto(people));
    }

    @Override
    public ResponseEntity<PersonDTO> addPerson(PersonDTO personDTO) {
        return ResponseEntity.ok(personMapper
                .toPersonDTO(personService
                        .create(personMapper
                                .toPerson(personDTO))));
    }
}
