package testpatterns;

// Tests to show that != is reported as well
// the int comparison is not reported
public class testpattern1_2 {
    public static void main(String[] args) {
        String sample = "hello";
        int y = 10;
        // We should report this
        if (sample != hello())
            System.out.println("Yep");
        // We should NOT report this
        if (y == 12)
            System.out.println("NO");

    }
    public static String hello(){
        return "hello";
    }
}