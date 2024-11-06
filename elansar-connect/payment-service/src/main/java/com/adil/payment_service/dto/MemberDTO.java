package com.adil.payment_service.dto;

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
    private String address;
    private String phoneNumber;
    private String email;
    private String contributionStatus;
    private LocalDate createdAt;
}
