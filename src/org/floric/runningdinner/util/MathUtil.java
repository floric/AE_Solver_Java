package org.floric.runningdinner.util;

/** Mathematic util functions
 *
 * Created by Florian on 24.02.2016.
 */
public final class MathUtil {

    private MathUtil() {
    }

    public static <T> void printMatrix(T[][] m) {
        for (int x = 0; x < m.length; x++) {
            for (int y = 0; y < m[x].length; y++) {
                System.out.print(m[x][y]);
            }
            System.out.println();
        }
    }

}
