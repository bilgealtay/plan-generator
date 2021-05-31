package com.ravensoftware.plangenerator.demo.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by bilga on 31-05-2021
 */
public class PaymentCalculationModel implements Serializable {

    @NotNull
    private Integer duration;
    @NotNull
    private Double rate;
    @NotNull
    private BigDecimal loanAmount;
    @NotNull
    private String startDate;

    public PaymentCalculationModel() {
    }

    public PaymentCalculationModel(@NotNull Integer duration, @NotNull Double rate, @NotNull BigDecimal loanAmount, @NotNull String startDate) {
        this.duration = duration;
        this.rate = rate;
        this.loanAmount = loanAmount;
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
