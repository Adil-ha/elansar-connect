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
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
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
			// Récupérer tous les membres et les contributions existantes
			List<MemberDTO> memberDTOCollection = memberRestClient.getAllMembers();
			List<ContributionDTO> existingContributions = contributionService.getAllContributions();

			// Vérifier s'il y a déjà des contributions
			if (!existingContributions.isEmpty()) {
				System.out.println("Contributions already exist in the database. No new records inserted.");
				return;
			}

			// Parcourir chaque membre pour générer des contributions
			memberDTOCollection.forEach(m -> {
				BigDecimal totalPaid = BigDecimal.ZERO;

				// Créer des contributions mensuelles pour simuler un membre qui paye progressivement
				for (int month = 1; month <= 12; month++) {
					// Vérifier si le total payé dépasse 120 €
					if (totalPaid.compareTo(new BigDecimal("120.00")) >= 0) {
						System.out.println("Member " + m.getId() + " has already paid the maximum for the year.");
						break;
					}

					// Ajouter une contribution mensuelle de 10 €
					ContributionDTO contribution = ContributionDTO.builder()
							.amount(new BigDecimal("10.00"))
							.typePayment(PaymentType.MONTHLY)
							.modePayment(PaymentMode.CASH)
							.idMember(m.getId())
							.build();

					contributionService.createContribution(contribution);
					totalPaid = totalPaid.add(contribution.getAmount());
				}

				System.out.println("Contributions for Member " + m.getId() + " inserted into the database.");
			});
		};
	}

}
