package com.sngular.skilltree.opportunity.service;

import com.sngular.skilltree.application.OpportunityService;
import com.sngular.skilltree.application.OpportunityServiceImpl;
import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.model.Opportunity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static com.sngular.skilltree.opportunity.service.OpportunityFixtures.OPPORTUNITY_BY_CODE;
import static com.sngular.skilltree.opportunity.service.OpportunityFixtures.OPPORTUNITY2_BY_CODE;
import static com.sngular.skilltree.opportunity.service.OpportunityFixtures.OPPORTUNITY_LIST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OpportunityServiceTest {

    @Mock
    private OpportunityRepository opportunityRepository;

    private OpportunityService opportunityService;

    private OpportunityMapper mapper = Mappers.getMapper(OpportunityMapper.class);

    @BeforeEach
    void setUp() {opportunityService = new OpportunityServiceImpl(opportunityRepository);}

    @Test
    @DisplayName("Testing save opportunity")
    void testSave(){
        when(opportunityRepository.save(OPPORTUNITY_BY_CODE)).thenReturn(OPPORTUNITY_BY_CODE);
        Opportunity result = opportunityService.create(OPPORTUNITY_BY_CODE);
        assertThat(result).isEqualTo(OPPORTUNITY_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the opportunities")
    void testGetAll(){
        when(opportunityRepository.findAll()).thenReturn(OPPORTUNITY_LIST);
        List<Opportunity> result = opportunityService.getAll();
        assertThat(result).containsExactly(OPPORTUNITY_BY_CODE, OPPORTUNITY2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a opportunity")
    void testFindByCode(){
        when(opportunityRepository.findByCode(anyString())).thenReturn(OPPORTUNITY_BY_CODE);
        Opportunity result = opportunityService.findByCode("itxtl1");
        assertThat(result).isEqualTo(OPPORTUNITY_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(opportunityRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(opportunityRepository.findByCode(anyString())).thenReturn(OPPORTUNITY_BY_CODE);
        boolean result = opportunityService.deleteByCode("itxtl1");
        assertThat(result).isTrue();
    }
}
