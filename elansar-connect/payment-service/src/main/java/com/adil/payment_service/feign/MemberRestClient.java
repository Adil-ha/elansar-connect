package com.adil.payment_service.feign;

import com.adil.payment_service.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberRestClient {
     @GetMapping("/api/members/{id}")
     MemberDTO getMemberById(@PathVariable Long idMember);

     @GetMapping("/api/members")
     PagedModel<MemberDTO> getAllMembers();
}
