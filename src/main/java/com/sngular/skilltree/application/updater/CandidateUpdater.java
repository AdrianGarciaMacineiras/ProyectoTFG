package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Candidate;

public interface CandidateUpdater {

    Candidate update(final String candidatecode, final Candidate newCandidate);

    Candidate patch(final String candidatecode, final Candidate patchedCandidate);

}
