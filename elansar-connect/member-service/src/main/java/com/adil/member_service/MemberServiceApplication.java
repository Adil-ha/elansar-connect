package com.adil.member_service;

import com.adil.member_service.dto.MemberDTO;
import com.adil.member_service.exception.MemberAlreadyExistsException;
import com.adil.member_service.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@AllArgsConstructor
public class MemberServiceApplication {
	private final MemberService memberService;

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			MemberDTO member1 = new MemberDTO();
			member1.setFirstname("John");
			member1.setLastname("Doe");
			member1.setEmail("john.doe@example.com");
			member1.setPhoneNumber("123456789");
			member1.setDateOfBirth(LocalDate.of(1990, 1, 1));
			member1.setCreatedAt(LocalDate.from(LocalDateTime.now()));

			MemberDTO member2 = new MemberDTO();
			member2.setFirstname("Jane");
			member2.setLastname("Smith");
			member2.setEmail("jane.smith@example.com");
			member2.setPhoneNumber("987654321");
			member2.setDateOfBirth(LocalDate.of(1985, 5, 20));
			member2.setCreatedAt(LocalDate.from(LocalDateTime.now()));

			MemberDTO member3 = new MemberDTO();
			member3.setFirstname("Alice");
			member3.setLastname("Johnson");
			member3.setEmail("alice.johnson@example.com");
			member3.setPhoneNumber("456123789");
			member3.setDateOfBirth(LocalDate.of(2000, 12, 31));
			member3.setCreatedAt(LocalDate.from(LocalDateTime.now()));

			createMemberSafely(member1);
			createMemberSafely(member2);
			createMemberSafely(member3);
		};
	}

	private void createMemberSafely(MemberDTO memberDTO) {
		try {
			memberService.createMember(memberDTO);
		} catch (MemberAlreadyExistsException e) {
			System.out.println("Member already exists: " + memberDTO.getEmail());
		}
	}
}
