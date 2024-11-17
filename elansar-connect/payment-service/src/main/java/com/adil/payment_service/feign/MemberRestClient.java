package com.adil.payment_service.feign;

import com.adil.payment_service.dto.MemberDTO;
import com.adil.payment_service.dto.UpdateContributionStatusRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "member-service")
public interface MemberRestClient {

     @GetMapping("/api/members/{id}")
     @CircuitBreaker(name = "memberService", fallbackMethod = "getDefaultMember")
     MemberDTO getMemberById(@PathVariable("id") Long idMember);

     @GetMapping("/api/members")
     List<MemberDTO> getAllMembers();

     @PutMapping("/api/members/{id}/contributionStatus")
     @CircuitBreaker(name = "memberService", fallbackMethod = "updateDefaultContributionStatus")
     MemberDTO updateContributionStatus(@PathVariable("id") Long id,
                                        @RequestBody UpdateContributionStatusRequest request);


     default MemberDTO getDefaultMember(Long id, Exception exception) {
          logFallbackExecution(id, exception);
          return MemberDTO.builder()
                  .id(id)
                  .lastname("NOT AVAILABLE")
                  .firstname("NOT AVAILABLE")
                  .dateOfBirth(null)
                  .phoneNumber("NOT AVAILABLE")
                  .email("NOT AVAILABLE")
                  .build();
     }

     //      Fallback method matching the signature of updateContributionStatus
     default MemberDTO updateDefaultContributionStatus(Long id, UpdateContributionStatusRequest request, Throwable throwable) {
          logFallbackExecution(id, throwable);
          return MemberDTO.builder()
                  .id(id)
                  .lastname("NOT AVAILABLE")
                  .firstname("NOT AVAILABLE")
                  .contributionStatus(null) // Retourner null si échec
                  .build();
     }

     private void logFallbackExecution(Long id, Throwable throwable) {
          System.err.println("Fallback executed for Member ID: " + id + ". Reason: " + throwable.getMessage());
     }
}
