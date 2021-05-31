# This application returns the final repayment schedule of the borrowers.

## Requirements

For building and running the application you need:
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Spring Boot](https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/htmlsingle/) 


# Used http://financeformulas.net/Annuity_Payment_Formula.html as reference for calculate annuity

# The end point url is http://localhost:8080/generate-plan. It must be a POST method with Restful webservices.

    request body;
        {
            "duration": 12,
            "rate": 5,
            "loanAmount": 5000,
            "startDate": "01.01.2018"
        }
        
    response body;
        [
            {
                "paymentDate": "01.01.2018",
                "annuity": 428.04,
                "principal": 407.21,
                "interest": 20.83,
                "initialOutstandingPrincipal": 5000,
                "remainingOutstandingPrincipal": 4592.79
            },
            {
                "paymentDate": "01.02.2018",
                "annuity": 428.04,
                "principal": 408.90,
                "interest": 19.14,
                "initialOutstandingPrincipal": 4592.79,
                "remainingOutstandingPrincipal": 4183.89
            },
            ...
            {
                "paymentDate": "01.12.2018",
                "annuity": 428.01,
                "principal": 426.23,
                "interest": 1.78,
                "initialOutstandingPrincipal": 426.23,
                "remainingOutstandingPrincipal": 0.00
            }
        ]
        
        
