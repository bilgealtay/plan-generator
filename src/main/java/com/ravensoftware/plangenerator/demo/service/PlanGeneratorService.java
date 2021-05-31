package com.ravensoftware.plangenerator.demo.service;

import com.ravensoftware.plangenerator.demo.entity.PaymentCalculationModel;
import com.ravensoftware.plangenerator.demo.entity.PaymentPlanModel;
import com.ravensoftware.plangenerator.demo.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by bilga on 31-05-2021
 */
@Service
public class PlanGeneratorService {

    private static BigDecimal HUNDRED = new BigDecimal(100);
    private static BigDecimal MONTH_OF_YEAR = new BigDecimal(12);
    private static BigDecimal DAY_OF_MONTH = new BigDecimal(30);
    private static BigDecimal DAY_OF_YEAR = new BigDecimal(360);
    private static int SCALE = 5;
    private static int ROUNDING = 5;

    public List<PaymentPlanModel> generatePlan(PaymentCalculationModel calculationModel){
        final BigDecimal[] annuity = {calculateAnnuity(calculationModel)};
        final BigDecimal[] totalPrincipal = {BigDecimal.ZERO};

        List<PaymentPlanModel> paymentPlans = new ArrayList<>();

        IntStream.range(0, calculationModel.getDuration()).forEach(i -> {
            BigDecimal initialOutstandingPrincipal;
            String startingDate;

            // we need to use initial values from calculationModel if it is first item.
            if (i == 0){
                initialOutstandingPrincipal = calculationModel.getLoanAmount();
                startingDate = calculationModel.getStartDate();
            } else {
                initialOutstandingPrincipal = paymentPlans.get(i-1).getRemainingOutstandingPrincipal();
                startingDate = DateUtils.getNextMonthToString(paymentPlans.get(i-1).getPaymentDate(), DateUtils.PATTERN_DD_MM_YYYY);
            }

            // calculate interest for monthly
            BigDecimal interest = (new BigDecimal(calculationModel.getRate()).divide(HUNDRED, SCALE, ROUNDING).multiply(DAY_OF_MONTH).multiply(initialOutstandingPrincipal)).divide(DAY_OF_YEAR, SCALE, ROUNDING).setScale(2, RoundingMode.HALF_UP);
            // calculate principal of the month.
            BigDecimal principal = annuity[0].subtract(interest).setScale(2, RoundingMode.HALF_UP);
            // save totalPrincipal, will use on the last item.
            totalPrincipal[0] = totalPrincipal[0].add(principal);

            // if, calculated principal amount exceeds the initial outstanding principal amount, take initial outstanding principal amount instead >> can happen in the very last installment
            if (i == calculationModel.getDuration() - 1 && totalPrincipal[0].compareTo(calculationModel.getLoanAmount()) > 0){
                principal = initialOutstandingPrincipal;
                annuity[0] = annuity[0].subtract(totalPrincipal[0].subtract(calculationModel.getLoanAmount()));
            }
            BigDecimal remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal).setScale(2, RoundingMode.HALF_UP);

            // add model to the List
            paymentPlans.add(new PaymentPlanModel(startingDate, annuity[0], principal, interest, initialOutstandingPrincipal, remainingOutstandingPrincipal));
        });

        return paymentPlans;
    }

    /**
     *
     * @param calculationModel
     * @return annuity value
     *
     * calculate Annuity from http://financeformulas.net/Annuity_Payment_Formula.html as reference
     */
    private BigDecimal calculateAnnuity(PaymentCalculationModel calculationModel){

        BigDecimal monthlyRate = (new BigDecimal(calculationModel.getRate()).divide(HUNDRED)).divide(MONTH_OF_YEAR, SCALE, ROUNDING);
        BigDecimal portion = calculationModel.getLoanAmount().multiply(monthlyRate);

        BigDecimal denominator = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(monthlyRate.add(BigDecimal.ONE).pow(calculationModel.getDuration()), SCALE, ROUNDING));
        BigDecimal annuity = portion.divide(denominator, SCALE, ROUNDING);

        return annuity.setScale(2, RoundingMode.HALF_UP);

    }

}
