package com.example.demo.util;

import java.util.Arrays;

/**
 * @author ptomjie
 * @since 2024-02-20 22:05
 */
public class ImprovedDynamicPowerCapping {

    private final double[] predictedPowers; // 预测功率
    private final double[] currentPowers; // 当前功率
    private final double powerLimit; // 总功率限制

    public ImprovedDynamicPowerCapping(double[] predictedPowers, double[] currentPowers, double powerLimit) {
        this.predictedPowers = predictedPowers;
        this.currentPowers = currentPowers;
        this.powerLimit = powerLimit;
    }

    public double[] getAdjustedPowers() {
        // 排序
        Arrays.sort(predictedPowers);

        double totalCurrentPower = 0;
        for (double currentPower : currentPowers) {
            totalCurrentPower += currentPower;
        }

        double availablePower = powerLimit - totalCurrentPower;

        double[] adjustedPowers = new double[predictedPowers.length];
        double allocatedPower = 0;
        for (int i = 0; i < predictedPowers.length; i++) {
            if (availablePower >= predictedPowers[i]) {
                adjustedPowers[i] = predictedPowers[i];
                availablePower -= predictedPowers[i];
            } else {
                adjustedPowers[i] = availablePower;
                availablePower = 0;
            }
            allocatedPower += adjustedPowers[i];
        }

        // 修正：将已经分配的功率减去
        for (int i = 0; i < 3; i++) {
            adjustedPowers[i] -= allocatedPower;
        }

        return adjustedPowers;
    }

    public static void main(String[] args) {
        double[] predictedPowers = {100, 120, 150, 180};
        double[] currentPowers = {80, 90, 110, 130};
        double powerLimit = 500;

        ImprovedDynamicPowerCapping dynamicPowerCapping = new ImprovedDynamicPowerCapping(predictedPowers, currentPowers, powerLimit);
        double[] adjustedPowers = dynamicPowerCapping.getAdjustedPowers();

        for (int i = 0; i < adjustedPowers.length; i++) {
            System.out.println("机柜 " + i + " 调整后的功率：" + adjustedPowers[i]);
        }
    }
}
