package com.sngular.skilltree.person.service;

import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.PeopleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    private PeopleMapper mapper = Mappers.getMapper(PeopleMapper.class);

    private PeopleService peopleService;

    @BeforeEach
    void setUp(){
        peopleService = new PeopleServiceImpl(peopleRepository, mapper);
    }

    @Test
    @DisplayName("Testing getAll the people")
    void testGetAll(){
        when(peopleRepository.findAll()).thenReturn(List.of(PersonFixtures.PEOPLE_BY_CODE));
        List<People> result = peopleService.getAll();
        assertThat(result).containsExactly(PersonFixtures.PEOPLE_BY_CODE);
    }
}
