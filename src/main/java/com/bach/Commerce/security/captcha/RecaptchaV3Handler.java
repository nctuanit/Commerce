package com.bach.Commerce.security.captcha;

import com.bach.Commerce.common.exception.InvalidReCaptchaTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RecaptchaV3Handler {
    @Value("${recaptcha.secret}")
    private String recaptchaSecret = "6LfjhogeAAAAANJa6AidZJbnf4cBwjnX7pNbnEKa";
    private String recaptchaServerURL = "https://www.google.com/recaptcha/api/siteverify";

    public float verify(String recaptchaFormResponse) throws InvalidReCaptchaTokenException {
        System.out.println("ReCaptchaV3 Handler called.");
        System.out.println("g-recaptcha-ressponse: " + recaptchaFormResponse);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", recaptchaFormResponse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ReCapchaResponse response = restTemplate.postForObject(recaptchaServerURL, request, ReCapchaResponse.class);

        System.out.println("Recaptcha Response : \n");

        System.out.println("Success: " + response.isSuccess());
        System.out.println("Action: " + response.getAction());
        System.out.println("Hostname: " + response.getHostname());
        System.out.println("Score: " + response.getScore());
        System.out.println("Challenge Timestamp" + response.getChallenge_ts());

        if (response.getErrorCodes() != null) {
            System.out.println("Error Codes:");
            for (String errorCode : response.getErrorCodes()) {
                System.out.println("\t" + errorCode);
            }
        }

        if (!response.isSuccess()) {
            throw new InvalidReCaptchaTokenException("Invalid Recaptcha Token. Please check site key.");
        }

        return response.getScore();
        //return 0.4f;
    }
}
