package com.sngular.skilltree.opportunity.model;

import com.sngular.skilltree.client.model.Client;
import com.sngular.skilltree.project.model.Project;
import lombok.Value;
import java.util.Date;
import java.util.List;


@Value
public class Opportunity {

    String code;

    String name;

    Project project;

    Client client;

    Date openingDate;

    Date closingDate;

    String priority;

    Mode mode;

    private enum Mode{
        REMOTE,
        PRESENTIAL,
        MIX
    }

    String office;

    String role;

    List<Skill> skills;

    private class Skill{
        String skill;
        LevelReq levelReq;
        enum LevelReq{
            MANDATORY,
            NICE_TO_HAVE
        }
        MinLevel minLevel;
        enum MinLevel{
            HIGH,
            MEDIUM,
            LOW
        }
        String minExp;
    }
}
