public class DataRace {
    public static void main(String[] args) throws InterruptedException {
        SharedClass sharedClass = new SharedClass();
        Thread incThread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread checkThread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        incThread.start();
        checkThread.start();
    }

    public static class SharedClass {
        public volatile int x;
        public volatile int y;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                System.out.println("Data race was detected.");
            }
        }
    }
}
