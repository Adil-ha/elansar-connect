package com.adil.member_service.service;

import com.adil.member_service.dto.MemberDTO;
import com.adil.member_service.entity.enums.ContributionStatus;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    MemberDTO createMember(MemberDTO memberDTO);

    List<MemberDTO> getAllMembers();

    Optional<MemberDTO> getMemberById(Long id);

    MemberDTO updateMember(Long id, MemberDTO memberDTO);

    MemberDTO updateContributionStatus(Long id, ContributionStatus contributionStatus);

    void deleteMember(Long id);
}
