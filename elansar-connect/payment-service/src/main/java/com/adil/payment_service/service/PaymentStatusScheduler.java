package com.adil.payment_service.service;

import com.adil.payment_service.dto.UpdateContributionStatusRequest;
import com.adil.payment_service.entity.Contribution;
import com.adil.payment_service.entity.enums.ContributionStatus;
import com.adil.payment_service.feign.MemberRestClient;
import com.adil.payment_service.repository.ContributionRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentStatusScheduler {

    private final ContributionRepository contributionRepository;
    private final MemberRestClient memberRestClient;



//    @Scheduled(cron = "0 0 0 28 * ?") // Exécute tous les 28 du mois à minuit
    @Scheduled(cron = "0 */3 * * * *")
    public void updateMemberStatuses() {
        System.out.println("Starting monthly payment status check...");

        List<Long> allMemberIds = contributionRepository.findAllMemberIds();
        for (Long memberId : allMemberIds) {
            try {
                BigDecimal totalPaid = calculateTotalPaymentsForMember(memberId);
                int currentMonth = LocalDate.now().getMonthValue();
                BigDecimal expectedAmount = BigDecimal.valueOf(currentMonth * 10); // 10€ par mois

                ContributionStatus newStatus = totalPaid.compareTo(expectedAmount) >= 0
                        ? ContributionStatus.PAID
                        : ContributionStatus.UNPAID;

                UpdateContributionStatusRequest request = new UpdateContributionStatusRequest(newStatus);

                // Mettre à jour le statut du membre
                memberRestClient.updateContributionStatus(memberId, request);
                System.out.println("Updated status for member ID " + memberId + " to " + newStatus);

            } catch (Exception e) {
                System.err.println("Failed to update status for member ID " + memberId + ": " + e.getMessage());
            }
        }

        System.out.println("Monthly payment status check completed.");
    }

    private BigDecimal calculateTotalPaymentsForMember(Long memberId) {
        List<Contribution> contributions = contributionRepository.findByIdMember(memberId);
        return contributions.stream()
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

