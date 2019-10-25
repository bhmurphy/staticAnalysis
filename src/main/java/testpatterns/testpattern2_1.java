package testpatterns;

// Simple test case that finds when .equals() method is being used with any classes
public class testpattern2_1 {

    // Test for the most common general case
    public void test1() {
        String x = "foo";
        if(x.equals(null))
            System.out.println("We did a bad thing.");
    }

    // Even if the variable is initialized to null, this is still bad practice
    // (It'll also cause an NPE)
    public void test2() {
        testpattern2_A a = null;
        if(a.equals(null)) {
            System.out.println("We did an even worse thing.");
        }
    }

    public void test3() {
        String hey = "Hey";
        String you = "You";
        if(hey.equals(you)) {
            System.out.println("This should not cause an error.");
        }
    }
}

class testpattern2_A {
    int x;
    int y;
}