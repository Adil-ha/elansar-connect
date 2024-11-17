package com.adil.member_service.dto;

import com.adil.member_service.entity.enums.ContributionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

