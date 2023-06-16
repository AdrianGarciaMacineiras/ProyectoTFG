package com.sngular.skilltree.application;

import com.sngular.skilltree.application.implement.CandidateServiceImpl;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Candidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.sngular.skilltree.application.CandidateFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private CandidateService candidateService;

    @Captor
    ArgumentCaptor<Candidate> candidateArgumentCaptor;

    @BeforeEach
    void setUp() {candidateService = new CandidateServiceImpl(candidateRepository, positionRepository);}

    @Test
    @DisplayName("Testing getAll the candidates")
    void testGetAll(){
        when(candidateRepository.findAllCandidates()).thenReturn(CANDIDATE_LIST);
        List<Candidate> result = candidateService.getAll();
        assertThat(result).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a candidate")
    void testFindByCode(){
        when(candidateRepository.findByCode(anyString())).thenReturn(CANDIDATE_BY_CODE);
        Candidate result = candidateService.findByCode("c1120");
        assertThat(result).isEqualTo(CANDIDATE_BY_CODE);
    }

    @Test
    @DisplayName("Test find candidate by code exception")
    void testFindByCodeException(){
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            candidateService.findByCode("c1120");
        });
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(candidateRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(candidateRepository.findByCode("c1120")).thenReturn(CANDIDATE_BY_CODE);
        boolean result = candidateService.deleteByCode("c1120");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test delete exception")
    void testDeleteException(){
        Assertions.assertThrows(EntityNotFoundException.class,()->
            candidateService.deleteByCode("c1120")
        );
    }

    @Test
    @DisplayName("Testing generate candidates")
    void testGenerateCandidates(){
        when(candidateRepository.generateCandidates(anyString(), anyList())).thenReturn(CANDIDATE_LIST);
        List<Candidate> result = candidateService.generateCandidates("itxtl1", List.of(POSITION_SKILL));
        assertThat(result).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }


    @Test
    @DisplayName("Testing assign candidate")
    void testAssignCandidate() {
        when(candidateRepository.findByPeopleAndPosition(anyString(), anyString())).thenReturn(Optional.of(CANDIDATE_BY_CODE));
        doNothing().when(candidateRepository).assignCandidate(anyString(), anyString(), candidateArgumentCaptor.capture());
        candidateService.assignCandidate("itxtl1", "1");
        assertThat(candidateArgumentCaptor.getValue()).isEqualTo(CANDIDATE_BY_CODE);
    }

    @Test
    @DisplayName("Testing assign empty exception")
    void testAssignCandidateExceptionEmpty() {
        when(candidateRepository.findByPeopleAndPosition(anyString(), anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                candidateService.assignCandidate("c1120", "1")
        );
    }



    @Test
    @DisplayName("Testing get candidates using long as parameter")
    void testGetCandidatesLong() {
        when(candidateRepository.getCandidatesByPosition(anyString())).thenReturn(CANDIDATE_LIST);
        List<Candidate> result = candidateService.getCandidatesByPeople("1");
        assertThat(result).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }

    @Test
    @DisplayName("Testing get candidates using string as parameter")
    void testGetCandidatesString() {
        when(candidateRepository.getCandidatesByPosition(anyString())).thenReturn(CANDIDATE_LIST);
        List<Candidate> result = candidateService.getCandidatesByPosition("itxtl1");
        assertThat(result).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }


}
