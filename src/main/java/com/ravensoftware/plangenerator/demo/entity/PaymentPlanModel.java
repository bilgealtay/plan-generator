package com.ravensoftware.plangenerator.demo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by bilga on 31-05-2021
 */
public class PaymentPlanModel implements Serializable {

    private String paymentDate;
    private BigDecimal annuity;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal initialOutstandingPrincipal;
    private BigDecimal remainingOutstandingPrincipal;

    public PaymentPlanModel() {
    }

    public PaymentPlanModel(String paymentDate, BigDecimal annuity, BigDecimal principal, BigDecimal interest, BigDecimal initialOutstandingPrincipal, BigDecimal remainingOutstandingPrincipal) {
        this.paymentDate = paymentDate;
        this.annuity = annuity;
        this.principal = principal;
        this.interest = interest;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAnnuity() {
        return annuity;
    }

    public void setAnnuity(BigDecimal annuity) {
        this.annuity = annuity;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal;
    }

    public void setInitialOutstandingPrincipal(BigDecimal initialOutstandingPrincipal) {
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
    }

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    public void setRemainingOutstandingPrincipal(BigDecimal remainingOutstandingPrincipal) {
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }
}
