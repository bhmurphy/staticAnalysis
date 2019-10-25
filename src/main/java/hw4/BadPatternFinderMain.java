package hw4;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Collection;
import java.util.Iterator;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;


public class BadPatternFinderMain {

    public static void main( String[] args ) throws FileNotFoundException {
        double startTime = System.currentTimeMillis();
        if(args.length == 0) {
            System.out.println("Please provide a directory to analyze");
            System.exit(1);
        }

        // Requires this boilerplate nonsense to do type checking
        // See page 48 of the textbook
        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        reflectionTypeSolver.setParent(reflectionTypeSolver);

        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(reflectionTypeSolver);

        // args[0] is the argument you pass in to tell which directory the files you're evaluating are in
        // We want to add all folders in the given path that are named "java" to the type solver
        Collection<File> directories;
        try {
            File passed_in = new File(args[0]);
            if(passed_in.isDirectory()) {
                directories = FileUtils.listFilesAndDirs(passed_in, new NotFileFilter(TrueFileFilter.INSTANCE),
                        DirectoryFileFilter.DIRECTORY);

                if (directories.size() == 0) {
                    System.out.println("The directory you passed does not have a java folder in its src directory, which is required for the JavaParser extension.");
                    System.exit(1);
                }

                for (File directory : directories) {
                    if (directory.getName().equals("java")) {
                        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(directory.getPath()));
                        combinedTypeSolver.add(javaParserTypeSolver);
                    }
                }

                JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
                StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

                // We want to iterate through all files given in the first argument of the method call
                Iterator fileIterator = FileUtils.iterateFiles(passed_in, new SuffixFileFilter(".java"),
                        TrueFileFilter.INSTANCE);

                // Check every file
                while (fileIterator.hasNext()) {
                    File to_check = (File) fileIterator.next();
                    CompilationUnit cu = StaticJavaParser.parse(to_check);
                    VoidVisitor<String> badPatternVisitor = new BadPatternFinder();
                    badPatternVisitor.visit(cu, to_check.getPath());
                }
            }
            else {
                JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
                StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
                CompilationUnit cu = StaticJavaParser.parse(passed_in);
                VoidVisitor<String> badPatternVisitor = new BadPatternFinder();
                badPatternVisitor.visit(cu, passed_in.getPath());
            }
            double time2 = System.currentTimeMillis();
            System.out.println("Time taken: " + ((time2 - startTime) / 1000) + " seconds");
        }
        catch(IllegalArgumentException e) {
            System.out.println(args[0] + " is not a valid directory. Please pass in the directory of your java project or a valid Java file name.");
            System.exit(1);
        }
        catch(FileNotFoundException e) {
            System.out.println(args[0] + " is not a valid file name. Please pass in the directory of your java project or a valid Java file name.");
            System.exit(1);
        }
        catch(ParseProblemException e) {
            System.out.println(args[0] + " is not a valid java file. Please pass in the directory of your java project or a valid Java file name.");
            System.exit(1);
        }

    }
}
