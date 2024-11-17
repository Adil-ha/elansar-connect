package com.adil.member_service.entity;

import com.adil.member_service.entity.enums.ContributionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(name = "date_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribution_status", length = 50)
    private ContributionStatus contributionStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

//    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
//    private LegalRepresentative legalRepresentative;


}
