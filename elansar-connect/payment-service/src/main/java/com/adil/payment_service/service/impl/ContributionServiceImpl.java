package com.adil.payment_service.service.impl;

import com.adil.payment_service.dto.ContributionDTO;
import com.adil.payment_service.dto.MemberDTO;
import com.adil.payment_service.dto.UpdateContributionStatusRequest;
import com.adil.payment_service.entity.Contribution;
import com.adil.payment_service.entity.enums.ContributionStatus;
import com.adil.payment_service.exception.MaxYearlyContributionExceededException;
import com.adil.payment_service.feign.MemberRestClient;
import com.adil.payment_service.mapper.ContributionMapper;
import com.adil.payment_service.repository.ContributionRepository;
import com.adil.payment_service.service.ContributionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;
    private final ContributionMapper contributionMapper;
    private final MemberRestClient memberRestClient;

    private static final BigDecimal MAX_YEARLY_CONTRIBUTION = BigDecimal.valueOf(120); // Limite annuelle de 120€

    @Override
    public ContributionDTO createContribution(ContributionDTO contributionDTO) {
        Long memberId = contributionDTO.getIdMember();

        // Vérifier si le membre peut encore payer cette année
        BigDecimal totalPaidThisYear = calculateTotalPaymentsForCurrentYear(memberId);
        BigDecimal remainingAmount = MAX_YEARLY_CONTRIBUTION.subtract(totalPaidThisYear);

        if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MaxYearlyContributionExceededException(
                    "Maximum yearly contribution reached. No more payments allowed for this year."
            );
        }

        if (contributionDTO.getAmount().compareTo(remainingAmount) > 0) {
            throw new MaxYearlyContributionExceededException(
                    "Contribution amount exceeds the remaining allowed amount for the year: " + remainingAmount + " €."
            );
        }

        // Enregistrer la contribution
        Contribution contribution = contributionMapper.toEntity(contributionDTO);
        contribution.setDatePayment(LocalDate.now());
        Contribution savedContribution = contributionRepository.save(contribution);

        // Mettre à jour le statut du membre si nécessaire
        updateContributionStatusIfNeeded(memberId);

        return contributionMapper.toDTO(savedContribution);
    }

    @Override
    public List<ContributionDTO> getAllContributions() {
        return contributionRepository.findAll().stream()
                .map(contribution -> {
                    ContributionDTO contributionDTO = contributionMapper.toDTO(contribution);
                    enrichContributionWithMember(contributionDTO);
                    return contributionDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ContributionDTO> getContributionById(Long id) {
        return contributionRepository.findById(id)
                .map(contribution -> {
                    ContributionDTO contributionDTO = contributionMapper.toDTO(contribution);
                    enrichContributionWithMember(contributionDTO);
                    return contributionDTO;
                });
    }

    @Override
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        if (!contributionRepository.existsById(id)) {
            throw new IllegalArgumentException("Contribution not found with id: " + id);
        }

        Contribution contributionToUpdate = contributionMapper.toEntity(contributionDTO);
        contributionToUpdate.setIdContribution(id);
        Contribution updatedContribution = contributionRepository.save(contributionToUpdate);

        // Mettre à jour le statut du membre si nécessaire
        updateContributionStatusIfNeeded(contributionToUpdate.getIdMember());

        return contributionMapper.toDTO(updatedContribution);
    }

    @Override
    public void deleteContribution(Long id) {
        if (!contributionRepository.existsById(id)) {
            throw new IllegalArgumentException("Contribution not found with id: " + id);
        }
        contributionRepository.deleteById(id);
    }

    private void enrichContributionWithMember(ContributionDTO contributionDTO) {
        Long memberId = contributionDTO.getIdMember();
        try {
            MemberDTO memberDTO = memberRestClient.getMemberById(memberId);
            contributionDTO.setMemberDTO(memberDTO);
        } catch (Exception e) {
            System.err.println("Could not fetch member with ID " + memberId + ": " + e.getMessage());
        }
    }

    private BigDecimal calculateTotalPaymentsForCurrentYear(Long memberId) {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate endOfYear = LocalDate.now().withMonth(12).withDayOfMonth(31);

        List<Contribution> contributionsForYear = contributionRepository.findByIdMemberAndDatePaymentBetween(
                memberId, startOfYear, endOfYear);

        return contributionsForYear.stream()
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalPaymentsForMember(Long memberId) {
        List<Contribution> contributions = contributionRepository.findByIdMember(memberId);
        return contributions.stream()
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateContributionStatusIfNeeded(Long memberId) {
        BigDecimal totalPaid = calculateTotalPaymentsForMember(memberId);

        int currentMonth = LocalDate.now().getMonthValue();
        BigDecimal expectedAmount = BigDecimal.valueOf(currentMonth * 10); // 10€ par mois

        ContributionStatus newStatus = totalPaid.compareTo(expectedAmount) >= 0
                ? ContributionStatus.PAID
                : ContributionStatus.UNPAID;

        UpdateContributionStatusRequest request = new UpdateContributionStatusRequest(newStatus);

        if (newStatus == ContributionStatus.PAID) {
            memberRestClient.updateContributionStatus(memberId, request);
        }
    }
}
