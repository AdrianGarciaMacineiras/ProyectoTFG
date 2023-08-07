package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamView {

  String getCode();

    String getShortName();

    String getName();

    String getDesc();

    List<String> getTags();

    List<Member> getMembers();

    List<SkillView> getUses();

    List<SkillView> getStrategics();

    LocalDateTime getLastUpdated();

    interface Member {
        String getId();

        PeopleView getPeople();

        String getCharge();
    }

    interface PeopleView {

        String getCode();

        String getName();

        String getEmployeeId();

        String getSurname();

        String getTitle();

        boolean getAssignable();

        List<Knows> getKnows();
    }

    interface Knows {
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
}
