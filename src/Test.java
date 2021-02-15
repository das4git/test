public class Test {

    public static void main(String[] args) throws Exception {

        thread(new NonPersistentMode());
        thread(new PersistentMode());
        thread(new TransactionalMode());
        thread(new ClientAcknowledgeMode());
        thread(new DupsOkAcknowledgeMode());

        PluginMode example = new PluginMode();
        System.out.println("Starting the Embedded Broker ");
        try {
            example.before();
            example.run();
            example.after();
        } catch (Exception e) {
            System.out.println("Caught" + e.getMessage());
        }
        System.out.println("Finished the Embedded Broker ");


    }

    public static void thread(Runnable runnable) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.start();
    }
}
