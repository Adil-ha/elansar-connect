package com.adil.member_service.service.impl;

import com.adil.member_service.dto.MemberDTO;
import com.adil.member_service.entity.Member;
import com.adil.member_service.entity.enums.ContributionStatus;
import com.adil.member_service.exception.MemberAlreadyExistsException;
import com.adil.member_service.exception.MemberNotFoundException;
import com.adil.member_service.mapper.MemberMapper;
import com.adil.member_service.repository.MemberRepository;
import com.adil.member_service.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;



    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new MemberAlreadyExistsException(memberDTO.getEmail());
        }
        Member member = memberMapper.toEntity(memberDTO);
        member.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        member.setContributionStatus(ContributionStatus.UNPAID);
        Member savedMember = memberRepository.save(member);
        return memberMapper.toDTO(savedMember);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(memberMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::toDTO)
                .or(() -> {
                    throw new MemberNotFoundException(id);
                });
    }

    @Override
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        if (!memberRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        Member memberToUpdate = memberMapper.toEntity(memberDTO);
        memberToUpdate.setId(id); // S'assurer que l'ID est celui du membre existant
        Member updatedMember = memberRepository.save(memberToUpdate);
        return memberMapper.toDTO(updatedMember);
    }

    @Override
    public MemberDTO updateContributionStatus(Long id, ContributionStatus contributionStatus) {
        return memberRepository.findById(id)
                .map(member -> {
                    member.setContributionStatus(contributionStatus);
                    return memberRepository.save(member);
                })
                .map(memberMapper::toDTO)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        memberRepository.deleteById(id);
    }
}