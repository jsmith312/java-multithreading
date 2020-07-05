public class RaceCondition {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start(); // run task
        decrementingThread.start();

        decrementingThread.join();
        incrementingThread.join(); // wait to finish

        System.out.println("we currently have " + inventoryCounter.getItems() + " items");
    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    /*
        shared object used by multiple threads
        items++ and items-- are not atomic operations
    * */
    public static class InventoryCounter {
        private int items = 0;

        Object lock = new Object();

        public void increment() {
            synchronized (this.lock) {
                items++;
            }
        }

        public void decrement() {
            synchronized (lock) {
                items--;
            }
        }

        public int getItems() {
            synchronized (lock) {
                return this.items;
            }
        }
    }
}
