package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.CandidateFixtures.*;
import static com.sngular.skilltree.application.PersonFixtures.*;
import static com.sngular.skilltree.application.PersonFixtures.PEOPLE_BY_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.implement.PeopleServiceImpl;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;
import org.junit.jupiter.api.Assertions;
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
    @DisplayName("Testing save person exception already exists")
    void testSaveExceptionDeletedFalse(){
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE);
        Assertions.assertThrows(EntityFoundException.class, () ->
                peopleService.create(PEOPLE_BY_CODE)
        );
    }

    @Test
    @DisplayName("Testing findByCode a person")
    void testFindByCode() {
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE);
        People result = peopleService.findByCode("1");
        assertThat(result).isEqualTo(PEOPLE_BY_CODE);
    }

    @Test
    @DisplayName("Testing find person exception is null")
    void testFindByCodeExceptionNull(){
        when(peopleRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.findByCode("1")
        );
    }

    @Test
    @DisplayName("Testing find person exception is deleted")
    void testFindByCodeExceptionDeleteTrue(){
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE_DELETE_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.findByCode("1")
        );
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode() {
        when(peopleRepository.deleteByCode(anyString())).thenReturn(true);
        when(peopleRepository.findByCode("1")).thenReturn(PEOPLE_BY_CODE);
        boolean result = peopleService.deleteByCode("1");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Testing delete person exception is deleted")
    void testDeleteByCodeExceptionDeleteTrue(){
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE_DELETE_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.deleteByCode("1")
        );
    }

    @Test
    @DisplayName("Testing delete person exception is null")
    void testDeleteByCodeExceptionNull(){
        when(peopleRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.deleteByCode("1")
        );
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

    @Test
    @DisplayName("Test assign a candidate to a position exception null")
    void testAssignCandidateExceptionNull(){
        when(peopleRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.assignCandidate("1", "itxtl1")
        );
    }

    @Test
    @DisplayName("Test assign a candidate to a position exception deleted")
    void testAssignCandidateExceptionDeleted(){
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE_DELETE_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.assignCandidate("1", "itxtl1")
        );
    }

    @Test
    @DisplayName("Test get candidates")
    void testGetCandidates() {
        when(candidateService.getCandidatesByPosition(anyString())).thenReturn(CANDIDATE_LIST);
        List<Candidate> result = candidateService.getCandidatesByPosition("1");
        assertThat(result).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }

    @Test
    @DisplayName("Test get candidates exception null")
    void testGetCandidateExceptionNull(){
        when(peopleRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.getCandidates("1")
        );
    }

    @Test
    @DisplayName("Test get candidates exception deleted")
    void testGetCandidateExceptionDeleted(){
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE_DELETE_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.getCandidates("1")
        );
    }

    @Test
    @DisplayName("Test get people assigned positions")
    void testGetPeopleAssignedPositions() {
        when(positionService.getPeopleAssignedPositions(anyString())).thenReturn(List.of(POSITION_BY_CODE));
        List<Position> result = positionService.getPeopleAssignedPositions("1");
        assertThat(result).containsExactly(POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Test get people assigned positions exception null")
    void testGetPeopleAssignedPositionsNull(){
        when(peopleRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.getPeopleAssignedPositions("1")
        );
    }

    @Test
    @DisplayName("Test get candidates exception deleted")
    void testGetPeopleAssignedPositionsDeleted(){
        when(peopleRepository.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE_DELETE_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                peopleService.getPeopleAssignedPositions("1")
        );
    }

}
