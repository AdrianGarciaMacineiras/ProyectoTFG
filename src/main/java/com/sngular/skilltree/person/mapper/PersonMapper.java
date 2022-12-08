package com.sngular.skilltree.person.mapper;

import com.sngular.skilltree.api.model.PatchedPersonDTO;
import com.sngular.skilltree.api.model.PersonDTO;
import com.sngular.skilltree.person.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PersonMapper {

    PersonDTO toPersonDTO(Person person);

    Person toPerson(PersonDTO personDTO);

    List<PersonDTO> toPeopleDto(Collection<Person> people);

    Person toPerson (PatchedPersonDTO patchedPersonDTO);

    void update(@MappingTarget Person oldPerson, Person newPerson);
}
