package com.adil.payment_service.dto;

import com.adil.payment_service.entity.enums.ContributionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private ContributionStatus contributionStatus;
    private LocalDate createdAt;
}
