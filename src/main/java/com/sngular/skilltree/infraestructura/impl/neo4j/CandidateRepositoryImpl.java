package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

    private final CandidateCrudRepository crud;

    private final CandidateNodeMapper mapper;

    @Override
    public List<Candidate> findAll() {
        return mapper.map(crud.findAll());
    }

    @Override
    public Candidate save(Candidate candidate) {
        return mapper.fromNode(crud.save(mapper.toNode(candidate)));
    }

    @Override
    public Candidate findByCode(String candidatecode) {
        return mapper.fromNode(crud.findByCode(candidatecode));
    }

    @Override
    public boolean deleteByCode(String candidatecode) {
        var node = crud.findByCode(candidatecode);
        node.isDeleted();
        crud.save(node);
        //crud.delete(node);
        return true;
    }
}
