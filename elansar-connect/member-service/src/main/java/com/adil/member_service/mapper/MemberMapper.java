package com.adil.member_service.mapper;


import com.adil.member_service.dto.MemberDTO;
import com.adil.member_service.entity.Member;
import com.adil.member_service.entity.enums.ContributionStatus;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDTO toDTO(Member member) {
        if (member == null) {
            return null;
        }
        return MemberDTO.builder()
                .id(member.getId())
                .lastname(member.getLastname())
                .firstname(member.getFirstname())
                .dateOfBirth(member.getDateOfBirth())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .contributionStatus(member.getContributionStatus())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public Member toEntity(MemberDTO memberDTO) {
        if (memberDTO == null) {
            return null;
        }
        return Member.builder()
                .id(memberDTO.getId())
                .lastname(memberDTO.getLastname())
                .firstname(memberDTO.getFirstname())
                .dateOfBirth(memberDTO.getDateOfBirth())
                .phoneNumber(memberDTO.getPhoneNumber())
                .email(memberDTO.getEmail())
                .contributionStatus(memberDTO.getContributionStatus())
                .createdAt(memberDTO.getCreatedAt())
                .build();
    }
}
