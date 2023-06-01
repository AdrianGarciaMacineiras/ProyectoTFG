package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.MemberRelationship;
import com.sngular.skilltree.model.Member;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Named("resolveServiceTeamNode")
public class ResolveServiceTeamNode {

    private final PeopleNodeMapper mapper;

    @Named("mapToMember")
    public List<Member> mapToMember(List<MemberRelationship> memberRelationshipList){
        final List<Member> memberList = new ArrayList<>();
        for (var memberRelationship : memberRelationshipList){
            var people = mapper.fromNode(memberRelationship.peopleNode());
            var member = Member.builder()
                    .people(people)
                    .charge(memberRelationship.charge())
                    .build();
            memberList.add(member);
        }
        return memberList;
    }

    @Named("mapToMemberRelationship")
    public List<MemberRelationship> mapToMemberRelationship(List<Member> memberList){
        final List<MemberRelationship> memberRelationshipList = new ArrayList<>();
        for (var member : memberList){
            var peopleNode = mapper.toNode(member.people());
            MemberRelationship memberRelationship = new MemberRelationship(null, peopleNode, member.charge());
            memberRelationshipList.add(memberRelationship);
        }
        return memberRelationshipList;
    }
}
