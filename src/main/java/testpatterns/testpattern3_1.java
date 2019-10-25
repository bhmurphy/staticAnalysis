package testpatterns;

// Basic test case showing that all different variations
// of miscapitalization of toString are found
public class testpattern3_1 {

    // We should report this
    public String tostring() {
        return "way far out";
    }

    // We should report this
    public String TOSTRING() {
        return "way far off";
    }

    // We should report this
    public String ToStRiNg() {
        return "way far away";
    }

}
