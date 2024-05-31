package EjerciciosFin;

public class ShutdownPC {
    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("shutdown /s /t 1");
        } catch (Exception e) {
            System.out.println("Algo paso.");
        }
    }
}
