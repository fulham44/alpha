package com.example.insurance;

import com.example.insurance.domain.Insurance;
import com.example.insurance.service.InsuranceService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
class InsuranceResourceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonService jsonService;

    //Добавление позитивный
    @Test
    void addNewInsurance() throws Exception {
        InsuranceService.NewInsuranceRequest newInsuranceRequest = InsuranceService.NewInsuranceRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(14)
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .id(12)
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();


        mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(newInsuranceRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Добавление позитивный, минимальное значение
    @Test
    void addNewInsuranceCheckMinimum() throws Exception {
        InsuranceService.NewInsuranceRequest newInsuranceRequest = InsuranceService.NewInsuranceRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(5)
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .id(12)
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();


        mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(newInsuranceRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Добавление позитивный, максимальное значение
    @Test
    void addNewInsuranceCheckMax() throws Exception {
        InsuranceService.NewInsuranceRequest newInsuranceRequest = InsuranceService.NewInsuranceRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(17)
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .id(12)
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();


        mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(newInsuranceRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Добавление негативный, ниже допустимого значения
    @Test
    void addNewInsuranceCheckMinInvalid() throws Exception {
        InsuranceService.NewInsuranceRequest newInsuranceRequest = InsuranceService.NewInsuranceRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(4)
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .id(12)
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();


        mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(newInsuranceRequest))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    //Добавление негативный, выше допустимого значения
    @Test
    void addNewInsuranceCheckMaxInvalid() throws Exception {
        InsuranceService.NewInsuranceRequest newInsuranceRequest = InsuranceService.NewInsuranceRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(18) //уже не ребёнок
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .id(12)
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();


        mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(newInsuranceRequest))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    //Добавление, ошибка при другом типе данных
    @Test
    void invalidAgeType() throws Exception{
        String age = "invalid";

        NewInsuranceAgeStringRequest request = NewInsuranceAgeStringRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(age)
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();

        mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Получение страховки позитивный
    @Test
    void getInsuranceByInsuranceId() throws Exception {
        mockMvc.perform(get("/insurance/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    //Получение страховки негативный
	@Test
	void getInsuranceByInsuranceIdWithNotValid() throws Exception {
		mockMvc.perform(get("/insurance/999")) //несуществующий номер страховки
				.andDo(print())
                .andExpect(status().isBadRequest());
	}

    //Сохранение
	@Test
    void checkSave() throws Exception {
        Integer age = 15;

        MvcResult mvcResult = mockMvc.perform(post("/insurance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonService.toJson(getNewInsurance(age)))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String insuranceId = mvcResult.getResponse().getContentAsString();

        mockMvc.perform(get("/insurance/".concat(insuranceId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(age));
    }

    private InsuranceService.NewInsuranceRequest getNewInsurance(Integer age){
       return InsuranceService.NewInsuranceRequest.builder()
                .startDate(LocalDateTime.now().toString())
                .endDate(LocalDateTime.now().toString())
                .age(age)
                .region("Moscow")
                .city("Moscow")
                .insurance(Insurance.builder()
                        .startDate(LocalDateTime.now().toString())
                        .endDate(LocalDateTime.now().toString())
                        .build()
                ).build();
    }

    @Builder
    @Getter
    public static class NewInsuranceAgeStringRequest {
        private String startDate;
        private String endDate;
        private String age;
        private String region;
        private String city;
        private Insurance insurance;
    }
}
