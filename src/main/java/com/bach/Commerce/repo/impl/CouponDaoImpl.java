package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.Coupon;
import com.bach.Commerce.repo.dao.CounponDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
@Repository
@AllArgsConstructor
public class CouponDaoImpl implements CounponDAO {


    final EntityManager EntityManager;

    @Override
    public Coupon findCouponByCode(String code) {
        String jql = "SELECT c FROM Coupon c WHERE c.code = :code";
        return EntityManager.createQuery(jql, Coupon.class).setParameter("code", code).getSingleResult();
    }

}
