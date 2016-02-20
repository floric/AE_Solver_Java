package org.floric.runningdinner.util;

import java.util.ArrayList;

/**
 * Created by florian on 20.02.2016.
 */
public final class Statistics {

    private Statistics() {

    }

    public static ArrayList<int[]> getCookPermutations(int teamCount) {
        ArrayList<int[]> permutations = new ArrayList<>();

        // call recursive sub function
        getCookPermutationsRec(permutations, (new int[teamCount / 3]), teamCount, 0, 0);

        return permutations;
    }

    private static void getCookPermutationsRec(ArrayList<int[]> permutations, int[] currentPermutation, int teamCount, int minValue, int currentIndex) {
        if(currentIndex < (teamCount / 3)) {
            for(int i = minValue; i < teamCount; i++) {
                currentPermutation[currentIndex] = i;
                getCookPermutationsRec(permutations, currentPermutation.clone(), teamCount, i + 1, currentIndex + 1);
            }
        } else {
            permutations.add(currentPermutation);
        }

    }
}
