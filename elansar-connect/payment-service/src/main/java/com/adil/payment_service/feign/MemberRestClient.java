package com.adil.payment_service.feign;

import com.adil.payment_service.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "member-service")
public interface MemberRestClient {
     @GetMapping("/members/{id}")
     MemberDTO getMemberById(@PathVariable("id") Long idMember);

     @GetMapping("/members")
     List<MemberDTO> getAllMembers();
}

