package hw4;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class BadPatternFinder extends VoidVisitorAdapter<String> {

    // Equals null checker
    // https://pmd.github.io/latest/pmd_rules_java_errorprone.html#equalsnull
    @Override
    public void visit(MethodCallExpr me, String filename) {
        super.visit(me, filename);
        // Is the method name .equals?
        if(me.getName().toString().equals("equals")) {
            // This is mostly just to make sure the static analysis doesn't crash
            // We want to make sure we're sending arguments to .equals
            if(me.getArguments().size() > 0) {
                // Check if we're comparing our caller with null
                if(me.getArguments().get(0) instanceof NullLiteralExpr) {
                    System.out.println("Checking for equality to null using .equals()");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Error found in: " + filename);
                    System.out.println("Tests for null should not use the equals() method. The ‘==’ operator should be used instead.");
                    if(me.getRange().isPresent()) {
                        System.out.println("Pattern found at: " + me.getRange().get().begin + ": " + me.toString());
                    }
                    System.out.println("-----------------------------------------------------------------\n");
                }
            }
        }
    }

    // Class defines tostring(); should it be toString()? Checker from findBugs
    // Printed error message essentially ripped off from the following:
    // http://findbugs.sourceforge.net/bugDescriptions.html#NM_LCASE_TOSTRING
    @Override
    public void visit(MethodDeclaration md, String filename) {
        super.visit(md, filename);
        String methodName = md.getNameAsString();

        if(methodName.equalsIgnoreCase("tostring") && !methodName.equals("toString")) {
            if (md.getParentNode().isPresent()) {
                if (md.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {
                    ClassOrInterfaceDeclaration classDecl = (ClassOrInterfaceDeclaration) md.getParentNode().get();
                    String className = "Class " + classDecl.getNameAsString();
                    System.out.println(className + " defines " + methodName + "; should it be toString()?");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Error found in: " + filename);
                    System.out.println("This class defines a method called tostring(). This method does not override the" +
                            "toString() method in java.lang.Object, which is probably what was intended.");
                    if(md.getRange().isPresent()) {
                        System.out.println("Pattern found at: " + md.getRange().get().begin + ": " + md.getDeclarationAsString());
                    }
                    System.out.println("-----------------------------------------------------------------\n");
                }
            }
        }
    }
}
