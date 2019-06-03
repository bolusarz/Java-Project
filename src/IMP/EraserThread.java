package IMP;

public class EraserThread extends Thread{
    private volatile boolean stop;

    EraserThread(String prompt) {
        System.out.println(prompt);
    }

    /**
     * Begin masking...display asterisks (*)
     */
    public void run() {
        int priority = Thread.currentThread().getPriority();
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        try {
            stop = true;
            while (stop) {
                char echochar = '*';
                System.out.print("\010" + echochar);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } finally {
            Thread.currentThread().setPriority(priority);
        }
    }

    /**
     * Instruct the thread to stop masking
     */
    void stopMasking() {
        this.stop = false;
    }
}
