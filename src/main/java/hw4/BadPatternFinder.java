package hw4;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class BadPatternFinder extends VoidVisitorAdapter<String> {

    // String equality checker
    @Override
    public void visit(BinaryExpr be, String filename) {
        super.visit(be, filename);
        BinaryExpr.Operator operator = be.getOperator();


        if(operator.equals(BinaryExpr.Operator.EQUALS) || operator.equals(BinaryExpr.Operator.NOT_EQUALS)) {
            if (be.getLeft().calculateResolvedType().describe().equals("java.lang.String") && be.getRight().calculateResolvedType().describe().equals("java.lang.String")) {
                System.out.println("Error: Strings compared using " + operator.asString()  + " instead of equals()");
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Error found in: " + filename);
                System.out.println("Tests for string comparsion should not use == or !=. The equals() should be used instead.");
                if(be.getRange().isPresent()) {
                    System.out.println("Pattern found at: " + be.getRange().get().begin + ": " + be.toString());
                }
                System.out.println("-----------------------------------------------------------------\n");
            }
        }
    }

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
                    System.out.println("Error: Checking for equality to null using .equals()");
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
                // Only care if toString is part of a class/interface of some kind
                // These two cases should cover that
                if (md.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {
                    ClassOrInterfaceDeclaration classDecl = (ClassOrInterfaceDeclaration) md.getParentNode().get();
                    String className;
                    if(classDecl.isInterface())
                        className = "Interface " + classDecl.getNameAsString();
                    else
                        className = "Class " + classDecl.getNameAsString();
                    printToStringViolation(filename, methodName,className, md);
                }
                // This would be true if we were creating an anonymous class
                else if(md.getParentNode().get() instanceof ObjectCreationExpr) {
                    String className = "Anonymous class declaration";
                    printToStringViolation(filename, methodName,className, md);
                }
            }
        }
    }

    public void printToStringViolation(String filename, String methodName, String className, MethodDeclaration nd) {
        System.out.println("Error: " + className + " defines " + methodName + "(); should it be toString()?");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Error found in: " + filename);
        System.out.println("This class defines a method called " + methodName + "(). This method does not override the" +
                "toString() method in java.lang.Object, which is probably what was intended.");
        if(nd.getRange().isPresent()) {
            System.out.println("Pattern found at: " + nd.getRange().get().begin + ": " + nd.getDeclarationAsString());
        }
        System.out.println("-----------------------------------------------------------------\n");
    }
}
