package com.sngular.skilltree.infraestructura.impl.neo4j.projections;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.*;

import java.util.List;

public interface OpportunityProjection {

    String getCode();

    String getName();

    ProjectSummary getProject();

    interface ProjectSummary{
        String getCode();

    }

    ClientSummary getClient();

    interface ClientSummary{

        String getCode();

    }

    String getOpeningDate();

    String getClosingDate();

    String getPriority();

    EnumMode getMode();

    OfficeSummary getOffice();

    interface OfficeSummary{

        String getCode();

        String getName();

    }

    PeopleSummary getManagedBy();

    interface PeopleSummary{

        String getCode();

    }

    String getRole();

    List<OpportunitySkillsRelationship> getSkills();
}
