import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoining {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);

        List<FactorialThread> factorialThreads = new ArrayList<>();
        for (Long num : inputNumbers) {
            factorialThreads.add(new FactorialThread(num));
        }

        for (Thread thread : factorialThreads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread : factorialThreads) {
            thread.join(2000); // wait at most 2 seconds for each computation
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = factorialThreads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tmp = BigInteger.ONE;
            for (long i = n; n > 0; i--) {
                tmp = tmp.multiply(new BigInteger(Long.toString(i)));
            }
            return tmp;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
