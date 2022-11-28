package com.sngular.skilltree.opportunity.model;

import com.sngular.skilltree.client.model.Client;
import com.sngular.skilltree.project.model.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;
import java.util.List;


@Node
@Getter
@Setter
@NoArgsConstructor
public class Opportunity {

    @Id
    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private Project project;

    private Client client;

    private Date openingDate;

    private Date closingDate;

    private String priority;

    private Mode mode;

    private enum Mode{
        REMOTE,
        PRESENTIAL,
        MIX
    }

    private String office;

    private String role;

    private List<Skill> skills;

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
