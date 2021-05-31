package com.ravensoftware.plangenerator.demo.controller;

import com.ravensoftware.plangenerator.demo.entity.PaymentCalculationModel;
import com.ravensoftware.plangenerator.demo.entity.PaymentPlanModel;
import com.ravensoftware.plangenerator.demo.service.PlanGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by bilga on 31-05-2021
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/")
public class PlanGeneratorController {

    @Autowired
    private PlanGeneratorService planGeneratorService;

    @PostMapping(path = "generate-plan")
    public ResponseEntity generatePlan(@RequestBody @NotNull @Valid PaymentCalculationModel calculationModel) {
        try {
            List<PaymentPlanModel> response = planGeneratorService.generatePlan(calculationModel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
