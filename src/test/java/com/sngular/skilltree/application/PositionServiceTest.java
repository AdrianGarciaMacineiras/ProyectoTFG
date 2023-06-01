package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.PositionFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import com.sngular.skilltree.application.implement.PositionServiceImpl;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionSkill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    private PositionService positionService;

    @Mock
    private CandidateService candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Captor
    ArgumentCaptor<List<PositionSkill>> listArgumentCaptor;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        positionService = new PositionServiceImpl(positionRepository, candidateService);
    }

    @Test
    @DisplayName("Testing save opportunity")
    void testSave() {
        when(positionRepository.save(POSITION_BY_CODE)).thenReturn(POSITION_BY_CODE);
        when(positionRepository.findByCode(anyString())).thenReturn(null, POSITION_BY_CODE);
        Position result = positionService.create(POSITION_BY_CODE);
        assertThat(result).isEqualTo(POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the opportunities")
    void testGetAll(){
        when(positionRepository.findAll()).thenReturn(POSITION_LIST);
        List<Position> result = positionService.getAll();
        assertThat(result).containsExactly(POSITION_BY_CODE, POSITION_2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a opportunity")
    void testFindByCode(){
        when(positionRepository.findByCode(anyString())).thenReturn(POSITION_BY_CODE);
        Position result = positionService.findByCode("itxtl1");
        assertThat(result).isEqualTo(POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode() {
        when(positionRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(positionRepository.findByCode(anyString())).thenReturn(POSITION_BY_CODE);
        boolean result = positionService.deleteByCode("itxtl1");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Testing get positions that a person has been assigned")
    void testGetPeopleAssignedPositions(){
        when(positionRepository.getPeopleAssignedPositions(anyLong())).thenReturn(POSITION_LIST);
        List<Position> result = positionService.getPeopleAssignedPositions(1L);
        assertThat(result).containsExactly(POSITION_BY_CODE, POSITION_2_BY_CODE);
    }

    @Test
    @DisplayName("Testing generating candidates for a position")
    void testGenerateCandidates(){
        when(positionRepository.findByCode(anyString())).thenReturn(POSITION_BY_CODE);

        Position result = positionService.generateCandidates("itxtl1");

        verify(candidateService).generateCandidates(stringArgumentCaptor.capture(), listArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualTo(result.code());
        assertThat(listArgumentCaptor.getValue()).containsExactly(POSITION_SKILL);
        assertThat(result.candidates()).containsExactly(CANDIDATE_BY_CODE, CANDIDATE2_BY_CODE);
    }
}
