package hw4;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class BadPatternFinder extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodCallExpr md, Void arg) {
        super.visit(md, arg);
        System.out.println(md.getArguments());
    }


}
