package testpatterns;

// Test case to show that we report inappropriate usage of ==
// on strings
public class testpattern1_1 {
    public static void main(String[] args) {
        String sample = "hello";
        String hey = "hey";
        int y = 10;
        // We should report this
        if (sample == hello())
            System.out.println("Yep");
        // We should report this
        if (hey == sample)
            System.out.println("NO");

    }
    public static String hello(){
        return "hello";
    }
}
