package org.floric.runningdinner.util;

import java.util.ArrayList;

/**
 * Created by florian on 20.02.2016.
 */
public final class StatisticsUtil {

    private StatisticsUtil() {

    }

    public static ArrayList<int[]> getPermutations(int teamCount) {
        ArrayList<int[]> permutations = new ArrayList<>();

        if (teamCount < 0 && teamCount % 3 != 0) {
            throw new IllegalArgumentException("Teamcount for permutations must be multiple of 3!");
        }

        // call recursive sub function
        getPermutationsRec(permutations, new int[teamCount / 3], teamCount, 0, 0);

        return permutations;
    }

    private static void getPermutationsRec(ArrayList<int[]> permutations, int[] currentPermutation, int teamCount, int minValue, int currentIndex) {
        if (currentIndex < (teamCount / 3)) {
            for (int i = minValue; i < teamCount; i++) {
                currentPermutation[currentIndex] = i;
                getPermutationsRec(permutations, currentPermutation.clone(), teamCount, i + 1, currentIndex + 1);
            }
        } else {
            permutations.add(currentPermutation);
        }

    }
}
