package com.example.demo.util;

/**
 * @author ptomjie
 * @since 2024-02-20 22:02
 */
public class DynamicPowerCapping {

    private final double[] predictedPowers; // 预测功率
    private final double[] currentPowers; // 当前功率
    private final double powerLimit; // 总功率限制

    public DynamicPowerCapping(double[] predictedPowers, double[] currentPowers, double powerLimit) {
        this.predictedPowers = predictedPowers;
        this.currentPowers = currentPowers;
        this.powerLimit = powerLimit;
    }

    public double[] getAdjustedPowers() {
        double totalPredictedPower = 0;
        for (double predictedPower : predictedPowers) {
            totalPredictedPower += predictedPower;
        }

        double totalCurrentPower = 0;
        for (double currentPower : currentPowers) {
            totalCurrentPower += currentPower;
        }

        double availablePower = powerLimit - totalCurrentPower;

        double[] adjustedPowers = new double[predictedPowers.length];
        for (int i = 0; i < predictedPowers.length; i++) {
            if (predictedPowers[i] > currentPowers[i] + availablePower) {
                adjustedPowers[i] = currentPowers[i] + availablePower;
            } else {
                adjustedPowers[i] = predictedPowers[i];
            }
        }

        return adjustedPowers;
    }

    public static void main(String[] args) {
        double[] predictedPowers = {100, 120, 150, 180};
        double[] currentPowers = {80, 90, 110, 130};
        double powerLimit = 500;

        DynamicPowerCapping dynamicPowerCapping = new DynamicPowerCapping(predictedPowers, currentPowers, powerLimit);
        double[] adjustedPowers = dynamicPowerCapping.getAdjustedPowers();

        for (int i = 0; i < adjustedPowers.length; i++) {
            System.out.println("机柜 " + i + " 调整后的功率：" + adjustedPowers[i]);
        }
    }
}
