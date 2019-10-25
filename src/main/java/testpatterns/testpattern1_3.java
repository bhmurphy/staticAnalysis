package testpatterns;

// Test case to show that calls to .equals() do not
// trip the bad string comparison detector
public class testpattern1_3 {
    public static void main(String[] args) {
        String sample = "hello";
        String hey = "hey";
        int y = 10;
        // We should not report this
        if (sample.equals(hello()))
            System.out.println("Yep");
        // We should not report this
        if (sample.equals(hey))
            System.out.println("NO");

    }
    public static String hello(){
        return "hello";
    }
}