package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OfficeNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

}
