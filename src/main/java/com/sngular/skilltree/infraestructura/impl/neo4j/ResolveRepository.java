package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OfficeNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ParticipateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.model.Roles;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Named("resolveRepository")
public class ResolveRepository {

    private final OfficeCrudRepository officeCrudRepository;

    private final PeopleCrudRepository peopleCrudRepository;

    @Named("resolveCodeToOfficeNode")
    public OfficeNode resolveCodeOfficeNode(final String code) {
        return officeCrudRepository.findByCode(code);
    }

    @Named("resolveCodeOfficeNodeList")
    public List<OfficeNode> resolveCodeOfficeNode(final List<String> codeList) {
        final var officeNodeList = new ArrayList<OfficeNode>();
        if (codeList != null) {
            for (var code : codeList) {
                officeNodeList.add(resolveCodeOfficeNode(code));
            }
        }
        return officeNodeList;
    }

    @Named("resolveCodeToPeopleNode")
    public PeopleNode resolveCodePeopleNode(final String code) {
        return peopleCrudRepository.findByCode(code);
    }

    @Named("resolveRoles")
    public List<Roles> resolveRoles(final List<ParticipateRelationship> participateRelationshipList) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        final var rolesList = new ArrayList<Roles>();
        if (participateRelationshipList != null){
            for( var participateRelationship: participateRelationshipList){
                var rol = Roles.builder()
                        .role(participateRelationship.role())
                        .initDate(formatter.parse(participateRelationship.initDate()))
                        .endDate(formatter.parse(participateRelationship.endDate()))
                        .build();
                rolesList.add(rol);
            }
        }
        return rolesList;
    }

}
