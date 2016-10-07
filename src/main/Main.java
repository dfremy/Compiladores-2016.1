package main;

import java.io.BufferedReader;
import java.io.FileReader;

import java_cup.runtime.Symbol;

import compiler.generated.Parser;
import compiler.generated.Scanner;

/**
 * This class is the main class of the compiler. Call the main method given a list of file
 * paths corresponding to the files to be compiled. At least one file's path is expected.
 * 
 * @author Jessika, Mayza, Tiaraju
 *
 */
public class Main {

	private static void showHelp() {
		System.out.println("Make sure you have Java installed");
		System.out.println("Run: java - jar javacompiler.jar filePath [filePath2, filePath3...]");
	}

	private static void doCompile(String filePath) {
		try {
			Scanner scanner = new Scanner(new BufferedReader(new FileReader(
					filePath)));
			Parser parser = new Parser(scanner);

			Symbol result = parser.parse();

			if (result.toString().equals("#0")) {
				System.out.println("The compilation process was successfully finished for file "+filePath);
			} else {
				System.out.println("Error compilating file: " + filePath);
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println("Error compilating file: " + filePath);
			System.out.println(e.getMessage());

		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			showHelp();
		} else {
			for (String path : args) {
				doCompile(path);
			}
		}

	}
}
