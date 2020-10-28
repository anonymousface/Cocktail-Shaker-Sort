package sort;

import java.util.Scanner;

public class Shaker_Sort {
static Scanner sc = new Scanner(System.in);

	public static void shakerSort(int[] array) {
		boolean swapped;
		for (int i = 0; i < array.length / 2; i++) {
			swapped = false;
			int j;
			for (j = i; j < array.length - i - 1; j++) {
				if (array[j] < array[j + 1]) {
					int tmp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = tmp;
					swapped = true;
				}
			}
			for (j = array.length - 2 - i; j > i; j--) {
				if (array[j] > array[j - 1]) {
					int tmp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = tmp;
					swapped = true;
				}
			}
			if (!swapped) break;
		}
	}
}