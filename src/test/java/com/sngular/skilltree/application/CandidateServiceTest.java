package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.CandidateFixtures.CANDIDATE2_BY_CODE;
import static com.sngular.skilltree.application.CandidateFixtures.CANDIDATE_BY_CODE;
import static com.sngular.skilltree.application.CandidateFixtures.CANDIDATE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.implement.CandidateServiceImpl;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PositionRepository positionRepository;

    private CandidateService candidateService;

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
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(candidateRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(candidateRepository.findByCode("c1120")).thenReturn(CANDIDATE_BY_CODE);
        boolean result = candidateService.deleteByCode("c1120");
        assertThat(result).isTrue();
    }
}
