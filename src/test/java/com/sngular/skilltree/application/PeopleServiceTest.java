package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.PersonFixtures.PEOPLE2_BY_CODE;
import static com.sngular.skilltree.application.PersonFixtures.PEOPLE_BY_CODE;
import static com.sngular.skilltree.application.PersonFixtures.PEOPLE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.implement.PeopleServiceImpl;
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

}
