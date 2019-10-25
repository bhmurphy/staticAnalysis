package testpatterns;

public class testpattern2_2 {

    private testpattern2_B aInstance;

    public void test1() {
        // We should do this with variables received through function calls as well
        if(toString().equals(null)) {
            System.out.println("We did a bad thing.");
        }
        if(getB().equals(null)) {
            System.out.println("We did a bad thing. Same as above.");
        }
    }

    public void test2() {
        testpattern2_D d_instance = new testpattern2_D();
        if(d_instance.getCInstance().getBInstance().getAInstance().equals(null)) {
            System.out.println("We did a bad and confusing thing");
        }
    }

    public String toString() {
        return "An instance of testpattern2_2";
    }

    public testpattern2_B getB() {
        return this.aInstance;
    }
}

class testpattern2_B {
    private testpattern2_A a_instance;

    public testpattern2_A getAInstance() {
        return a_instance;
    }
}

class testpattern2_C {
    private testpattern2_B b_instance;

    public testpattern2_B getBInstance() {
        return b_instance;
    }
}

class testpattern2_D {
    private testpattern2_C c_instance;

    public testpattern2_C getCInstance() {
        return c_instance;
    }
}
