package week9;

//Claire Francis, Week9Program2, October 30, 2025

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Week9Program3 {

	static class faces {
        String name;
        double[] measures = new double[6]; // A, B, C, D, E, F
        double[] ratios = new double[15];  // computed ratios

        faces(String name, double[] m) {
            this.name = name;
            this.measures = m;
            computeRatios();
        }

        void computeRatios() {
            int index = 0;
            // Compute all 15 ratios
            for (int i = 0; i < measures.length; i++) {
                for (int j = i + 1; j < measures.length; j++) {
                    ratios[index++] = measures[i] / measures[j];
                }
            }
        }
    }

	private double[] ratios;
	private String name;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<faces> knownFaces = new ArrayList<>();

        // Step 1: Read the 5 known faces from a text file
        try {
            Scanner fileScanner = new Scanner(new File("C:\\Users\\clair\\Documents\\Week9FacesCode.txt"));
            while (fileScanner.hasNext()) {
                String name = fileScanner.next();
                double[] m = new double[6];
                for (int i = 0; i < 6; i++) {
                    if (fileScanner.hasNextDouble()) {
                        m[i] = fileScanner.nextDouble();
                    } else {
                        System.out.println("Error: invalid file format.");
                        return;
                    }
                }
                knownFaces.add(new faces(name, m));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: faces.txt not found!");
            return;
        }

        // Step 2: Get the mystery face measurements from user
        System.out.println("Enter the six measurements (top of head to bottom of chin, left ear to right ear, \ncenter point between the eyes and top of head, center of left eye to center of right eye, \nnose from top to bottom, bottom of chin to middle of mouth) for the mystery picture, in millimeters:");
        double[] unknownMeasures = new double[6];
        for (int i = 0; i < 6; i++) {
            char label = (char) ('A' + i);
            System.out.print(label + ": ");
            unknownMeasures[i] = input.nextDouble();
        }

        faces mystery = new faces("Mystery", unknownMeasures);

        // Step 3: Compute sum of squares % difference with each known face
        double minDifference = Double.MAX_VALUE;
        String bestMatch = "";

        for (faces known : knownFaces) {
            double diffSum = 0.0;
            for (int i = 0; i < 15; i++) {
                double r1 = known.ratios[i];
                double s1 = mystery.ratios[i];
                diffSum += Math.pow((s1 - r1) / r1, 2);
            }

            System.out.printf("Difference with %s: %.6f%n", known.name, diffSum);

            if (diffSum < minDifference) {
                minDifference = diffSum;
                bestMatch = known.name;
            }
        }

        // Step 4: Output result
        System.out.println("\nThe mystery picture most closely matches: " + bestMatch);
    }
}


