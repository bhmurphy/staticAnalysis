package testpatterns;

public class testpattern3_2 {

    /* This is NOT detected by the static analysis tool
       This is mostly to closely match the stated pattern in the findbugs docs
       We can assume that if the developers made a function like this, they
       (confusingly) want to do something other than the normal toString()
       functionality */
    public String to_string() {
        return "Not a real toString";
    }

    /* Again, this is not detected as an error by our tool
       Perhaps the developer wants to make a class that
       converts several different class types in a library
       to strings. This is definitely bad practice, but not
       wrong when going off of the pattern in the findbugs docs */
    public String toString(Integer x) {
        return x.toString();
    }
}