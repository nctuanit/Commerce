package com.bach.Commerce.security.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReCapchaResponse {

    private boolean success;
    private String hostname;
    private String action;
    private float score;
    private String challenge_ts;

    @JsonProperty("error-codes")
    private String[] errorCodes;
}
