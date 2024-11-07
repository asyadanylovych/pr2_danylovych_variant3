import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        // Логіка для запуску потоків і асинхронної обробки масиву
        long start = System.currentTimeMillis(); //поточний час в мілісекундах

        // Діапазон чисел і розмір масиву
        int rangeStart = 0;
        int rangeEnd = 1000;
        int arraySize = 50; // розмір масиву від 40 до 60

        // Створення та заповнення масиву випадковими числами
        int[] numbers = ArrayProcessor.generateRandomArray(arraySize, rangeStart, rangeEnd);

        // Розбиття масиву на частини
        int partSize = numbers.length / 4;  //  довжина кожної частини
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // використовується 4 потоки для обробки

        // Використовуємо CopyOnWriteArrayList для безпечного додавання результатів
        CopyOnWriteArrayList<Double> averages = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 4; i++) {
            int startIdx = i * partSize;
            // щоб кожен потік обробляв свою частину масиву, ми обчислюємо,
            // з якого індексу повинна починатися кожна частина
            int endIdx = (i == 3) ? numbers.length : (i + 1) * partSize; //Обчислення останнього індексу endIdx
            //створюється копія частини масиву для кожного потоку.
            final int[] part = Arrays.copyOfRange(numbers, startIdx, endIdx);

            // Створення асинхронного завдання для кожної частини масиву
            Callable<Double> task = () -> {
                double avg = ArrayProcessor.calculateAverage(part);
                averages.add(avg);  // Додаємо середнє значення до CopyOnWriteArrayList
                return avg;
            };
            executorService.submit(task);
        }

        // Чекаємо на завершення всіх потоків
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        // Обчислення середнього значення всього масиву з результатів
        double totalSum = 0;
        for (double avg : averages) {
            totalSum += avg;
        }

        double overallAverage = totalSum / averages.size();

        // Виведення результатів
        System.out.println("Overall average: " + overallAverage);
        System.out.println("Execution Time = " + (System.currentTimeMillis() - start));
    }
}
