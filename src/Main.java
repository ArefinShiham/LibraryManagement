public class Main {
    public static void main(String[] args) {
        try {

            System.out.println("Hello world!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}