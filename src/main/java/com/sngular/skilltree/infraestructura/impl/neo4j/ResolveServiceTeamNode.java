package com.sngular.skilltree.infraestructura.impl.neo4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumCharge;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.MemberRelationship;
import com.sngular.skilltree.model.Member;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Named("resolveServiceTeamNode")
public class ResolveServiceTeamNode {

    private final PeopleNodeMapper mapper;

    @Named("mapToMember")
    public List<Member> mapToMember(List<MemberRelationship> memberRelationshipList){
        final List<Member> memberList = new ArrayList<>();
        for (var memberRelationship : memberRelationshipList){
          var people = mapper.fromNode(memberRelationship.people());
          var member = Member.builder()
                             .people(people)
                             .charge(Objects.isNull(memberRelationship.charge()) ? EnumCharge.UNKNOWN.getValue() : memberRelationship.charge().getValue())
                             .build();
            memberList.add(member);
        }
        return memberList;
    }

    @Named("mapToMemberRelationship")
    public List<MemberRelationship> mapToMemberRelationship(List<Member> memberList) {
      final List<MemberRelationship> memberRelationshipList = new ArrayList<>();
      for (var member : memberList) {
        var peopleNode = mapper.toNode(member.people());
        MemberRelationship memberRelationship = MemberRelationship
          .builder()
          .people(peopleNode)
          .charge(EnumCharge.from(member.charge()))
          .build();
        memberRelationshipList.add(memberRelationship);
      }
      return memberRelationshipList;
    }
}
