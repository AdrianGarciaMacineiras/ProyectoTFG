package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Candidate;

public interface CandidateUpdater {

    Candidate update(final String candidateCode, final Candidate newCandidate);

    Candidate patch(final String candidateCode, final Candidate patchedCandidate);

}
