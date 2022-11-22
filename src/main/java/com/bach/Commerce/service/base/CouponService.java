package com.bach.Commerce.service.base;

import com.bach.Commerce.model.dto.CouponDTO;

public interface CouponService {

    public CouponDTO findCouponByCode(String code);

}
