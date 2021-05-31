package com.ravensoftware.plangenerator.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravensoftware.plangenerator.demo.entity.PaymentCalculationModel;
import com.ravensoftware.plangenerator.demo.entity.PaymentPlanModel;
import com.ravensoftware.plangenerator.demo.service.PlanGeneratorService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bilga on 31-05-2021
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlanGeneratorController.class)
public class PlanGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanGeneratorService planGeneratorService;

    ObjectMapper mapper =  new ObjectMapper();

    @Test
    public void should_return_bad_request_when_body_is_empty() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel();

        mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_duration_is_null() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(null, new Double(5), new BigDecimal(5000), "01.01.2018");

        mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_rate_is_null() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, null, new BigDecimal(5000), "01.01.2018");

        mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_loanAmount_is_null() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, new Double(5), null, "01.01.2018");

        mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_startDate_is_null() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, new Double(5), new BigDecimal(5000), null);

        mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_InternalServerError_when_date_format_is_different() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, new Double(5), new BigDecimal(5000), "01/01/2018");

        when( planGeneratorService.generatePlan( any()) )
                .thenThrow(DateTimeParseException.class);

        mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

    }

    @Test
    public void should_return_ok_when_paymentCalculationModel_is_ok() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, new Double(5), new BigDecimal(5000), "01.01.2018");

        List<PaymentPlanModel> planModels = new ArrayList<>();
        PaymentPlanModel model = new PaymentPlanModel("01.01.2018", new BigDecimal(12), new BigDecimal(12), new BigDecimal(12), new BigDecimal(12), new BigDecimal(12));
        planModels.add(model);

        given( planGeneratorService.generatePlan( any() ))
                .willReturn(planModels);

        MvcResult result = mockMvc.perform(post("/generate-plan")
                .content(mapper.writeValueAsString(calculationModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(1)))
                .andReturn();

    }

}
