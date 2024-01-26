package com.tfg.skilltree.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.tfg.skilltree.application.implement.PositionServiceImpl;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.PositionRepository;
import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.PositionSkill;
import org.junit.jupiter.api.Assertions;
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
        when(positionRepository.save(PositionFixtures.POSITION_BY_CODE)).thenReturn(PositionFixtures.POSITION_BY_CODE);
        when(positionRepository.findByCode(anyString())).thenReturn(PositionFixtures.POSITION_BY_CODE);
        Position result = positionService.create(PositionFixtures.POSITION_BY_CODE);
        assertThat(result).isEqualTo(PositionFixtures.POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Testing save position exception already exists")
    void testSaveExceptionDeletedFalse(){
        when(positionRepository.existByCode(anyString())).thenReturn(true);
        Assertions.assertThrows(EntityFoundException.class, () ->
                positionService.create(PositionFixtures.POSITION_BY_CODE)
        );
    }

    @Test
    @DisplayName("Testing getAll the opportunities")
    void testGetAll(){
        when(positionRepository.findAll()).thenReturn(PositionFixtures.POSITION_LIST);
        List<Position> result = positionService.getAll();
        assertThat(result).containsExactly(PositionFixtures.POSITION_BY_CODE, PositionFixtures.POSITION_2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a position")
    void testFindByCode(){
        when(positionRepository.findByCode(anyString())).thenReturn(PositionFixtures.POSITION_BY_CODE);
        Position result = positionService.findByCode("itxtl1");
        assertThat(result).isEqualTo(PositionFixtures.POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode() {
        when(positionRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(positionRepository.findByCode(anyString())).thenReturn(PositionFixtures.POSITION_BY_CODE);
        boolean result = positionService.deleteByCode("itxtl1");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Testing delete position exception is deleted")
    void testDeleteByCodeExceptionDeleteTrue(){
        when(positionRepository.findByCode(anyString())).thenReturn(PositionFixtures.POSITION_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                positionService.deleteByCode("itxtl1")
        );
    }

    @Test
    @DisplayName("Testing delete position exception is null")
    void testDeleteByCodeExceptionNull(){
        when(positionRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                positionService.deleteByCode("itxtl1")
        );
    }

    @Test
    @DisplayName("Testing get positions that a person has been assigned")
    void testGetPeopleAssignedPositions(){
        when(positionRepository.getPeopleAssignedPositions(anyString())).thenReturn(PositionFixtures.POSITION_LIST);
        List<Position> result = positionService.getPeopleAssignedPositions("1");
        assertThat(result).containsExactly(PositionFixtures.POSITION_BY_CODE, PositionFixtures.POSITION_2_BY_CODE);
    }

    @Test
    @DisplayName("Testing generating candidates for a position")
    void testGenerateCandidates(){
        when(positionRepository.findByCode(anyString())).thenReturn(PositionFixtures.POSITION_BY_CODE);

        Position result = positionService.generateCandidates("itxtl1");

        verify(candidateService).generateCandidates(stringArgumentCaptor.capture(), listArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualTo(result.code());
        assertThat(listArgumentCaptor.getValue()).containsExactly(PositionFixtures.POSITION_SKILL);
        assertThat(result.candidates()).containsExactly(PositionFixtures.CANDIDATE_BY_CODE, PositionFixtures.CANDIDATE2_BY_CODE);
    }
}
