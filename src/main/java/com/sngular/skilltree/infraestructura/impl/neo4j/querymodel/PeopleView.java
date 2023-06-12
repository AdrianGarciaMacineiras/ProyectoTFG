package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import java.time.LocalDate;
import java.util.List;

public interface PeopleView {
    String getCode();

    String getName();

    String getEmployeeId();

    String getSurname();

    String geTitle();

    boolean getAssignable();

    List<KnowsView> getKnows();

    List<SkillView> getWorkWith();

    List<AssignedView> getAssign();

    interface KnowsView {
        String getId();

        Integer getExperience();

        String getLevel();

        Boolean getPrimary();

        SkillView getSkill();
    }

    interface SkillView {
        String getCode();

        String getName();
    }

    interface AssignedView {
        String getId();

        PositionView getPosition();

        LocalDate getEndDate();

        LocalDate getInitDate();

        LocalDate getAssignDate();

        String getRole();

        Integer getDedication();
    }

    interface PositionView {
        String getCode();

        String getName();

        String getMode();

        String getRole();

        String getProjectName();
    }
}
