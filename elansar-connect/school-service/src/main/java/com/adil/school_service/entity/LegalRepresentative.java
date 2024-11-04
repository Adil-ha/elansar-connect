package com.adil.school_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "legal_representatives")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class LegalRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(length = 50)
    private String relation;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private String address;

    @Column(name = "is_member", nullable = false)
    private Boolean isMember;

//    @OneToOne
//    @JoinColumn(name = "member_id", referencedColumnName = "id")
//    private Member member;


}
