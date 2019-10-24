package hw4;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Iterator;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;


public class BadPatternFinderMain
{
    public static void main( String[] args ) throws FileNotFoundException
    {
        // Requires this boilerplate nonsense to do type checking
        // See page 48 of the textbook
        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        // args[0] is the argument you pass in to tell which directory the files you're evaluating are in
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(args[0]));
        reflectionTypeSolver.setParent(reflectionTypeSolver);
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(reflectionTypeSolver);
        combinedTypeSolver.add(javaParserTypeSolver);
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        // We want to iterate through all files given in the first argument of the method call
        Iterator fileIterator = FileUtils.iterateFiles(new File(args[0]), new SuffixFileFilter(".java"),
                TrueFileFilter.INSTANCE);

        while(fileIterator.hasNext()) {
            File to_check = (File) fileIterator.next();
            CompilationUnit cu = StaticJavaParser.parse(to_check);
            VoidVisitor<String> badPatternVisitor = new BadPatternFinder();
            badPatternVisitor.visit(cu, to_check.getPath());
        }
    }
}
