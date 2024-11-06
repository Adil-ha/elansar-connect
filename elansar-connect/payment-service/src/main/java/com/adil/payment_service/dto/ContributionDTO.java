package com.adil.payment_service.dto;

import com.adil.payment_service.entity.enums.PaymentMode;
import com.adil.payment_service.entity.enums.PaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContributionDTO {

    private Long idContribution;
    private BigDecimal amount;
    private LocalDate datePayment;
    private PaymentType typePayment;
    private PaymentMode modePayment;
    private Long idMember;
    private MemberDTO memberDTO;
}