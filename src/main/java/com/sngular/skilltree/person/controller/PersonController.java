package com.sngular.skilltree.person.controller;

import com.sngular.skilltree.api.PeopleApi;
import com.sngular.skilltree.api.model.PatchedPersonDTO;
import com.sngular.skilltree.api.model.PersonDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PersonController implements PeopleApi {
    @Override
    public ResponseEntity<PersonDTO> getPersonByCode(String personcode) {
        return PeopleApi.super.getPersonByCode(personcode);
    }

    @Override
    public ResponseEntity<Void> deletePerson(String personcode) {
        return PeopleApi.super.deletePerson(personcode);
    }

    @Override
    public ResponseEntity<PersonDTO> updatePerson(String personcode, PersonDTO personDTO) {
        return PeopleApi.super.updatePerson(personcode, personDTO);
    }

    @Override
    public ResponseEntity<PersonDTO> patchPerson(String personcode, PatchedPersonDTO patchedPersonDTO) {
        return PeopleApi.super.patchPerson(personcode, patchedPersonDTO);
    }

    @Override
    public ResponseEntity<List<PersonDTO>> getPeople() {
        return PeopleApi.super.getPeople();
    }

    @Override
    public ResponseEntity<PersonDTO> addPerson(PersonDTO personDTO) {
        return PeopleApi.super.addPerson(personDTO);
    }
}
