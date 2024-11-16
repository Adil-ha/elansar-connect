package com.adil.payment_service.web;

import com.adil.payment_service.dto.ContributionDTO;
import com.adil.payment_service.service.ContributionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contributions")
@AllArgsConstructor
public class ContributionController {

    private final ContributionService contributionService;

    @PostMapping
    public ResponseEntity<ContributionDTO> createContribution(@RequestBody ContributionDTO contributionDTO) {
        ContributionDTO createdContribution = contributionService.createContribution(contributionDTO);
        return new ResponseEntity<>(createdContribution, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContributionDTO>> getAllContributions() {
        List<ContributionDTO> contributions = contributionService.getAllContributions();
        return ResponseEntity.ok(contributions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributionDTO> getContributionById(@PathVariable Long id) {
        return contributionService.getContributionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContributionDTO> updateContribution(
            @PathVariable Long id,
            @RequestBody ContributionDTO contributionDTO) {
        try {
            ContributionDTO updatedContribution = contributionService.updateContribution(id, contributionDTO);
            return ResponseEntity.ok(updatedContribution);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        try {
            contributionService.deleteContribution(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}