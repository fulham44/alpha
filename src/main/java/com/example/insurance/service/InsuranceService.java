package com.example.insurance.service;

import com.example.insurance.domain.Insurance;
import com.example.insurance.support.InsuranceNotFoundException;
import com.example.insurance.support.WrongDataException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {


    public Integer newInsurance(NewInsuranceRequest request) {
        if (request.age != null) {
            if (request.age >= 18) {
                throw new WrongDataException("Age is too old ");
            }
            if (request.age < 5) {
                throw new WrongDataException("Age is too young");
            }

            return 15;
        }
        return 100;
    }

    public Insurance getInsuranceById(Integer insuranceId) {
        switch (insuranceId) {
            case 1:
                return Insurance.createInstanceWithId(1);
            case 1111:
                return Insurance.createInstanceWithId(354);
            default:
                if (insuranceId > 5 && insuranceId < 18) {
                    return Insurance.createInstanceWithId(insuranceId);
                }
                throw new InsuranceNotFoundException("Insurance not found with id" + insuranceId);
        }
    }

    @Builder
    @Getter
    public static class NewInsuranceRequest {
        private String startDate;
        private String endDate;
        private Integer age;
        private String region;
        private String city;
        private Insurance insurance;
    }
}
