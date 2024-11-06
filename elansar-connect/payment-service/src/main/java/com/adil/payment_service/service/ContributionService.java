package com.adil.payment_service.service;

import com.adil.payment_service.dto.ContributionDTO;

import java.util.List;
import java.util.Optional;

public interface ContributionService {
    ContributionDTO createContribution(ContributionDTO contributionDTO);

    List<ContributionDTO> getAllContributions();

    Optional<ContributionDTO> getContributionById(Long id);

    ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO);

    void deleteContribution(Long id);
}
