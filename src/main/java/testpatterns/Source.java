package testpatterns;

public class Source {
    public static void main(String[] args) {
        String sample = "foo bar";
        boolean x = Source.tostring() == "wow";
    }

    public static String tostring(){
        Source a = new Source();
        if(a == null)
            return "cool";
        else
            return "far out";
    }
}
