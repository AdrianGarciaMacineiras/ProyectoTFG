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

import static com.sngular.skilltree.person.service.PersonFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    private PeopleMapper mapper = Mappers.getMapper(PeopleMapper.class);

    private PeopleService peopleService;

    @BeforeEach
    void setUp(){
        peopleService = new PeopleServiceImpl(peopleRepository);
    }

    @Test
    @DisplayName("Testing getAll the people")
    void testGetAll(){
        when(peopleRepository.findAll()).thenReturn(PEOPLE_LIST);
        List<People> result = peopleService.getAll();
        assertThat(result).containsExactly(PEOPLE_BY_CODE, PEOPLE2_BY_CODE);
    }

    @Test
    @DisplayName("Testing save a person")
    void testSave(){
        when(peopleRepository.save(PEOPLE_BY_CODE)).thenReturn(PEOPLE_BY_CODE);
        People result = peopleService.create(PEOPLE_BY_CODE);
        assertThat(result).isEqualTo(PEOPLE_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a person")
    void testFindByCode(){
        when(peopleRepository.findByCode(anyLong())).thenReturn(PEOPLE_BY_CODE);
        People result = peopleService.findByCode(1L);
        assertThat(result).isEqualTo(PEOPLE_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(peopleRepository.deleteByCode(anyLong())).thenReturn(true);
        when(peopleRepository.findByCode(1L)).thenReturn(PEOPLE_BY_CODE);
        boolean result = peopleService.deleteByCode(1L);
        assertThat(result).isTrue();
    }

}
