package com.bach.Commerce.model.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserOder {
    private String shipName;
    private String phone;
    private String address;
    private String customerName;
}
