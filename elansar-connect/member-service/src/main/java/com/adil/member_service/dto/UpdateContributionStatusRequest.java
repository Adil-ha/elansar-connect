package com.adil.member_service.dto;

import com.adil.member_service.entity.enums.ContributionStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateContributionStatusRequest {
    private ContributionStatus contributionStatus;
}
