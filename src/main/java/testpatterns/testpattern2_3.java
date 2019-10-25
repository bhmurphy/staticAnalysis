package testpatterns;

// Test case for when the .equals() method compares to null
// Exploring patterns other than only an if statement of a boolean
// We should report all calls to .equals in this method
public class testpattern2_3 {
    private int x;

    public boolean test() {
        testpattern2_A a = new testpattern2_A();
        testpattern2_B b = new testpattern2_B();

        // Use in boolean assignment
        boolean z = a.equals(null);

        // Test in while loop
        while(!a.equals(null)) {
            if(this.x < 5)
                a = null;
            this.x--;
        }

        // Test in for loop
        for(int i=0; b.equals(null); i++) {
            if(i * this.x == 1000)
                b = null;
        }

        // Test in ternary conditional operator
        return (this.x < 5 ? b:b).equals(null);
    }

}