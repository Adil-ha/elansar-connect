package com.adil.payment_service.mapper;

import com.adil.payment_service.dto.ContributionDTO;
import com.adil.payment_service.entity.Contribution;
import org.springframework.stereotype.Component;


@Component
public class ContributionMapper {

    public ContributionDTO toDTO(Contribution contribution) {
        return ContributionDTO.builder()
                .idContribution(contribution.getIdContribution())
                .amount(contribution.getAmount())
                .datePayment(contribution.getDatePayment())
                .typePayment(contribution.getTypePayment())
                .modePayment(contribution.getModePayment())
                .idMember(contribution.getIdMember())
                .build();
    }


    public Contribution toEntity(ContributionDTO contributionDTO) {
        return Contribution.builder()
                .idContribution(contributionDTO.getIdContribution())
                .amount(contributionDTO.getAmount())
                .datePayment(contributionDTO.getDatePayment())
                .typePayment(contributionDTO.getTypePayment())
                .modePayment(contributionDTO.getModePayment())
                .idMember(contributionDTO.getIdMember())
                .build();
    }
}
