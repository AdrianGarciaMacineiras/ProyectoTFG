package com.sngular.skilltree.candidate.services;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.CandidateServiceImpl;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.model.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static com.sngular.skilltree.candidate.services.CandidateFixtures.CANDIDATE2_BY_CODE;
import static com.sngular.skilltree.candidate.services.CandidateFixtures.CANDIDATE_BY_CODE;
import static com.sngular.skilltree.candidate.services.CandidateFixtures.CANDIDATE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    private CandidateService candidateService;

    private CandidateMapper mapper = Mappers.getMapper(CandidateMapper.class);

    @BeforeEach
    void setUp() {candidateService = new CandidateServiceImpl(candidateRepository, mapper);}

    @Test
    @DisplayName("Testing save a candidate")
    void testSave(){
        when(candidateRepository.save(CANDIDATE_BY_CODE)).thenReturn(CANDIDATE_BY_CODE);
        Candidate result = candidateService.create(CANDIDATE_BY_CODE);
        assertThat(result).isEqualTo(CANDIDATE_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the candidates")
    void testGetAll(){
        when(candidateRepository.findAll()).thenReturn(CANDIDATE_LIST);
        List<Candidate> result = candidateService.getAll();
        assertThat(result).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a person")
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
