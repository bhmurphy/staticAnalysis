package testpatterns;

public interface testpattern3_3 {
    // We'll detect this error in interfaces as well, in case
    // many classes attempt to implement it and follow the
    // incorrect naming convention
    String tostring();
}


// We should get the same error if we define a toString in an
// anonymous class
class testpattern3_A {
    public void test() {

        // Anonymous class declaration here
        testpattern3_3 foo = new testpattern3_3() {
            public String tostring() {
                return "Hello";
            }
        };
    }
}
