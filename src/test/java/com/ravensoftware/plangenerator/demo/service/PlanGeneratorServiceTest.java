package com.ravensoftware.plangenerator.demo.service;

import com.ravensoftware.plangenerator.demo.entity.PaymentCalculationModel;
import com.ravensoftware.plangenerator.demo.entity.PaymentPlanModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Created by bilga on 31-05-2021
 */
@RunWith(SpringRunner.class)
public class PlanGeneratorServiceTest {

    @TestConfiguration
    static class PlanGeneratorServicesImplTestContextConfiguration {

        @Bean
        public PlanGeneratorService planGeneratorService() {
            return new PlanGeneratorService();
        }
    }

    @Autowired
    private PlanGeneratorService planGeneratorService;

    @Test
    public void should_return_exception_when_date_format_is_different_from_expected() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, new Double(5), new BigDecimal(5000), "01/01/2018");

        Exception exception = Assertions.assertThrows(DateTimeParseException.class, () -> {
            planGeneratorService.generatePlan(calculationModel);
        });

        String expectedMessage = "Text '" + calculationModel.getStartDate() + "' could not be parsed ";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void should_return_same_list_size_with_duration_when_paymentCalculationModel_is_ok() throws Exception {

        PaymentCalculationModel calculationModel = new PaymentCalculationModel(12, new Double(5), new BigDecimal(5000), "01.01.2018");
        List<PaymentPlanModel> list = planGeneratorService.generatePlan(calculationModel);
        Assert.assertTrue(calculationModel.getDuration() == list.size());

    }
}
