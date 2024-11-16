package com.adil.payment_service;

import com.adil.payment_service.dto.ContributionDTO;
import com.adil.payment_service.dto.MemberDTO;
import com.adil.payment_service.entity.enums.PaymentMode;
import com.adil.payment_service.entity.enums.PaymentType;
import com.adil.payment_service.feign.MemberRestClient;
import com.adil.payment_service.service.ContributionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@AllArgsConstructor
public class PaymentServiceApplication {

	private ContributionService contributionService;
	private MemberRestClient memberRestClient;

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			List<MemberDTO> memberDTOCollection = memberRestClient.getAllMembers();
			List<ContributionDTO> existingContributions = contributionService.getAllContributions();

			memberDTOCollection.forEach(m->{
				if (existingContributions.isEmpty()) {
					ContributionDTO contribution1 = ContributionDTO.builder()
							.amount(new BigDecimal("10.00"))
							.datePayment(LocalDate.now())
							.typePayment(PaymentType.MONTHLY)
							.modePayment(PaymentMode.CASH)
							.idMember(m.getId())
							.build();

					ContributionDTO contribution2 = ContributionDTO.builder()
							.amount(new BigDecimal("40.00"))
							.datePayment(LocalDate.now())
							.typePayment(PaymentType.QUARTERLY)
							.modePayment(PaymentMode.CARD)
							.idMember(m.getId())
							.build();

					ContributionDTO contribution3 = ContributionDTO.builder()
							.amount(new BigDecimal("120.00"))
							.datePayment(LocalDate.now())
							.typePayment(PaymentType.ANNUAL)
							.modePayment(PaymentMode.CASH)
							.idMember(m.getId())
							.build();

					contributionService.createContribution(contribution1);
					contributionService.createContribution(contribution2);
					contributionService.createContribution(contribution3);

					System.out.println("Initial contributions inserted into the database.");
				} else {
					System.out.println("Contributions already exist in the database. No new records inserted.");
				}
			});

		};
	}
}
