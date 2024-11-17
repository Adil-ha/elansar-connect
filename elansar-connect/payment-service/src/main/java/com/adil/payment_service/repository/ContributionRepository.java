package com.adil.payment_service.repository;

import com.adil.payment_service.entity.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByIdMember(Long memberId);

    List<Contribution> findByIdMemberAndDatePaymentBetween(Long memberId, LocalDate startOfYear, LocalDate endOfYear);

    @Query("SELECT DISTINCT c.idMember FROM Contribution c")
    List<Long> findAllMemberIds();

}
