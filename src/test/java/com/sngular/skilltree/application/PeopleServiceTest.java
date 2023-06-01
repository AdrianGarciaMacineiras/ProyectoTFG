package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.PersonFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.implement.PeopleServiceImpl;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.model.People;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private CandidateService candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PositionService positionService;

    private PeopleService peopleService;

    @BeforeEach
    void setUp(){
        peopleService = new PeopleServiceImpl(peopleRepository, candidateService, positionService);
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

    @Test
    @DisplayName("Testing get people with a set of skills")
    void testGetPeopleSkills(){
        when(peopleRepository.getPeopleSkills(anyList())).thenReturn(PEOPLE_LIST);
        List<People> result = peopleService.getPeopleSkills(List.of("s1120"));
        assertThat(result).containsExactly(PEOPLE_BY_CODE, PEOPLE2_BY_CODE);
    }

    @Test
    @DisplayName("Test get other people that work with the strategic skills of a team")
    void testGetOtherPeopleStrategicSkills(){
        when(peopleRepository.getOtherPeopleStrategicSkills(anyString())).thenReturn(PEOPLE_LIST);
        List<People> result = peopleService.getOtherPeopleStrategicSkills("t1120");
        assertThat(result).containsExactly(PEOPLE_BY_CODE, PEOPLE2_BY_CODE);
    }


}
