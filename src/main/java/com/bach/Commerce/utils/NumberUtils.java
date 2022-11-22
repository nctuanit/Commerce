package com.bach.Commerce.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    private static DecimalFormat Pricingformatter = new DecimalFormat("###,###,###.##");
    public static String toVnd(Object integer){
        return Pricingformatter.format(integer);
    }
}
