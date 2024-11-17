package com.adil.payment_service.dto;

import com.adil.payment_service.entity.enums.ContributionStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateContributionStatusRequest {
    private ContributionStatus contributionStatus;
}
