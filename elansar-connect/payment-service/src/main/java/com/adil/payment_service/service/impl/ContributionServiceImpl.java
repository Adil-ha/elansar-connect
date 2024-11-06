package com.adil.payment_service.service.impl;

import com.adil.payment_service.dto.ContributionDTO;
import com.adil.payment_service.entity.Contribution;
import com.adil.payment_service.mapper.ContributionMapper;
import com.adil.payment_service.repository.ContributionRepository;
import com.adil.payment_service.service.ContributionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;
    private final ContributionMapper contributionMapper;

    @Override
    public ContributionDTO createContribution(ContributionDTO contributionDTO) {
        Contribution contribution = contributionMapper.toEntity(contributionDTO);
        Contribution savedContribution = contributionRepository.save(contribution);
        return contributionMapper.toDTO(savedContribution);
    }

    @Override
    public List<ContributionDTO> getAllContributions() {
        return contributionRepository.findAll().stream()
                .map(contributionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ContributionDTO> getContributionById(Long id) {
        return contributionRepository.findById(id)
                .map(contributionMapper::toDTO);
    }

    @Override
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        if (!contributionRepository.existsById(id)) {
            throw new IllegalArgumentException("Contribution not found with id: " + id); // Gérer les exceptions selon vos besoins
        }

        Contribution contributionToUpdate = contributionMapper.toEntity(contributionDTO);
        contributionToUpdate.setIdContribution(id); // S'assurer que l'ID reste le même pour la mise à jour
        Contribution updatedContribution = contributionRepository.save(contributionToUpdate);
        return contributionMapper.toDTO(updatedContribution);
    }

    @Override
    public void deleteContribution(Long id) {
        if (!contributionRepository.existsById(id)) {
            throw new IllegalArgumentException("Contribution not found with id: " + id);
        }
        contributionRepository.deleteById(id);
    }
}
