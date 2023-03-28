package com.sngular.skilltree.puesto.service;

import com.sngular.skilltree.application.PuestoService;
import com.sngular.skilltree.application.implement.PuestoServiceImpl;
import com.sngular.skilltree.contract.mapper.PuestoMapper;
import com.sngular.skilltree.infraestructura.PuestoRepository;
import com.sngular.skilltree.model.Puesto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static com.sngular.skilltree.puesto.service.PuestoFixtures.PUESTO_BY_CODE;
import static com.sngular.skilltree.puesto.service.PuestoFixtures.PUESTO_2_BY_CODE;
import static com.sngular.skilltree.puesto.service.PuestoFixtures.PUESTO_LIST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PuestoServiceTest {

    @Mock
    private PuestoRepository puestoRepository;

    private PuestoService puestoService;

    private PuestoMapper mapper = Mappers.getMapper(PuestoMapper.class);

    @BeforeEach
    void setUp() {
        puestoService = new PuestoServiceImpl(puestoRepository);}

    @Test
    @DisplayName("Testing save opportunity")
    void testSave(){
        when(puestoRepository.save(PUESTO_BY_CODE)).thenReturn(PUESTO_BY_CODE);
        Puesto result = puestoService.create(PUESTO_BY_CODE);
        assertThat(result).isEqualTo(PUESTO_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the opportunities")
    void testGetAll(){
        when(puestoRepository.findAll()).thenReturn(PUESTO_LIST);
        List<Puesto> result = puestoService.getAll();
        assertThat(result).containsExactly(PUESTO_BY_CODE, PUESTO_2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a opportunity")
    void testFindByCode(){
        when(puestoRepository.findByCode(anyString())).thenReturn(PUESTO_BY_CODE);
        Puesto result = puestoService.findByCode("itxtl1");
        assertThat(result).isEqualTo(PUESTO_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(puestoRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(puestoRepository.findByCode(anyString())).thenReturn(PUESTO_BY_CODE);
        boolean result = puestoService.deleteByCode("itxtl1");
        assertThat(result).isTrue();
    }
}
