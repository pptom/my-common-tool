package com.example.demo.util;

import java.util.Arrays;

/**
 * @author ptomjie
 * @since 2024-02-19 22:17
 */
public class PowerLimitAllocation {
    public static int[] calculatePowerLimits(int[] currentPower, int[] predictedPower, int totalPowerLimit) {
        int numCabinets = currentPower.length;
        int[] powerLimits = new int[numCabinets];

        // Initialize the dynamic programming table
        int[][] dp = new int[numCabinets + 1][totalPowerLimit + 1];
        for (int i = 0; i <= numCabinets; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0;

        // Perform dynamic programming
        for (int i = 1; i <= numCabinets; i++) {
            for (int j = 0; j <= totalPowerLimit; j++) {
                // Case 1: Do not include current cabinet
                dp[i][j] = dp[i - 1][j];

                // Case 2: Include current cabinet if it doesn't exceed limit and increases power
                int newPowerLimit = currentPower[i - 1] + predictedPower[i - 1];
                if (j >= newPowerLimit && newPowerLimit > currentPower[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - newPowerLimit] + newPowerLimit);
                }
            }
        }

        // Reconstruct the solution
        int remainingPower = totalPowerLimit;
        for (int i = numCabinets; i > 0; i--) {
            if (dp[i][remainingPower] != dp[i - 1][remainingPower]) {
                powerLimits[i - 1] = currentPower[i - 1] + predictedPower[i - 1];
                remainingPower -= powerLimits[i - 1];
            }
        }

        return powerLimits;
    }

    public static void main(String[] args) {
        int[] currentPower = {100, 120, 80, 90}; // Current power of each cabinet
        int[] predictedPower = {20, 30, 40, 50}; // Predicted power for next time step for each cabinet
        int totalPowerLimit = 300; // Total power limit for all cabinets

        int[] powerLimits = calculatePowerLimits(currentPower, predictedPower, totalPowerLimit);

        System.out.println("Next time step power limits for each cabinet:");
        System.out.println(Arrays.toString(powerLimits));
    }
}
