package com.sngular.skilltree.position.service;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.application.implement.PositionServiceImpl;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static com.sngular.skilltree.position.service.PositionFixtures.POSITION_BY_CODE;
import static com.sngular.skilltree.position.service.PositionFixtures.POSITION_2_BY_CODE;
import static com.sngular.skilltree.position.service.PositionFixtures.POSITION_LIST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PositionServiceTest {

    @Mock
    private PositionRepository puestoRepository;

    private PositionService positionService;

    private CandidateService candidateService;

    private PositionMapper mapper = Mappers.getMapper(PositionMapper.class);

    @BeforeEach
    void setUp() {
        positionService = new PositionServiceImpl(puestoRepository, candidateService);}

    @Test
    @DisplayName("Testing save opportunity")
    void testSave(){
        when(puestoRepository.save(POSITION_BY_CODE)).thenReturn(POSITION_BY_CODE);
        Position result = positionService.create(POSITION_BY_CODE);
        assertThat(result).isEqualTo(POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the opportunities")
    void testGetAll(){
        when(puestoRepository.findAll()).thenReturn(POSITION_LIST);
        List<Position> result = positionService.getAll();
        assertThat(result).containsExactly(POSITION_BY_CODE, POSITION_2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a opportunity")
    void testFindByCode(){
        when(puestoRepository.findByCode(anyString())).thenReturn(POSITION_BY_CODE);
        Position result = positionService.findByCode("itxtl1");
        assertThat(result).isEqualTo(POSITION_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(puestoRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(puestoRepository.findByCode(anyString())).thenReturn(POSITION_BY_CODE);
        boolean result = positionService.deleteByCode("itxtl1");
        assertThat(result).isTrue();
    }
}
