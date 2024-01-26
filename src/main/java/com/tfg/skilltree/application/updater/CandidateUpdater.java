package com.tfg.skilltree.application.updater;

import com.tfg.skilltree.model.Candidate;

public interface CandidateUpdater {

    Candidate update(final String candidateCode, final Candidate newCandidate);

    Candidate patch(final String candidateCode, final Candidate patchedCandidate);

}
