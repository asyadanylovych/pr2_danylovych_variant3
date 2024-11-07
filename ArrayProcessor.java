import java.util.Random;

public class ArrayProcessor {

    // Генерація випадкового масиву
    public static int[] generateRandomArray(int size, int rangeStart, int rangeEnd) {
        Random random = new Random(); //об'єкт для генерації випадкових чисел
        int[] array = new int[size]; // масив цілих чисел розміру size
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
            //генерується випадкове число в межах від rangeStart до rangeEnd
        }
        return array;
    }

    // Обчислення середнього значення частини масиву
    public static double calculateAverage(int[] part) {
        int sum = 0;
        for (int num : part) {
            sum += num;
        }
        return (double) sum / part.length;
    }
}
