package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.Coupon;
import com.bach.Commerce.model.dto.CouponDTO;
import com.bach.Commerce.repo.dao.CounponDAO;
import com.bach.Commerce.service.base.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {


    final CounponDAO couponDAO;

    @Override
    public CouponDTO findCouponByCode(String code) {

        Coupon couponEntity = couponDAO.findCouponByCode(code);

        CouponDTO couponDTO = new CouponDTO();

        couponDTO.setId(couponEntity.getId());
        couponDTO.setCode(couponEntity.getCode());
        couponDTO.setDiscount(couponEntity.getDiscount());

        return couponDTO;
    }


}
