package com.adil.payment_service.entity;

import com.adil.payment_service.entity.enums.PaymentMode;
import com.adil.payment_service.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contribution")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribution")
    private Long idContribution;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "date_payment", nullable = false)
    private LocalDate datePayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_payment", nullable = false)
    private PaymentType typePayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_payment", nullable = false)
    private PaymentMode modePayment;

    private long idMember;


}
