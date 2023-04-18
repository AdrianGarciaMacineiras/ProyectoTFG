package com.sngular.skilltree.position.service;

import static com.sngular.skilltree.position.service.PositionFixtures.POSITION_2_BY_CODE;
import static com.sngular.skilltree.position.service.PositionFixtures.POSITION_BY_CODE;
import static com.sngular.skilltree.position.service.PositionFixtures.POSITION_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.application.implement.PositionServiceImpl;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    private PositionService positionService;

    @Mock
    private CandidateService candidateService;

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
}
