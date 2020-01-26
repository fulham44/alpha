package com.example.insurance.resource;

import com.example.insurance.domain.Insurance;
import com.example.insurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurance/")
@RequiredArgsConstructor
public class InsuranceResource {

    private final InsuranceService insuranceService;

    @GetMapping("/new")
    public String test() {
        return "helloWord";
    }

    @PostMapping("/new")
    public Integer newInsurance(@RequestBody InsuranceService.NewInsuranceRequest newInsuranceRequest) {
        return insuranceService.newInsurance(newInsuranceRequest);
    }

    @GetMapping("/{insuranceId}")
    public Insurance getInsuranceById(@PathVariable Integer insuranceId) {
        return insuranceService.getInsuranceById(insuranceId);
    }

}
