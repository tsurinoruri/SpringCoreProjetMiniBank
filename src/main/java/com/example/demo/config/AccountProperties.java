package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    public final int defaultAmount;
    public final double defaultCommission;

    public AccountProperties
            (
                    @Value("${account.default-amount}") int defaultAmount,
                    @Value("${account.transfer-commission}") double defaultCommission
            )
    {
        this.defaultCommission = defaultCommission;
        this.defaultAmount = defaultAmount;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getDefaultCommission() {
        return defaultCommission;
    }
}
