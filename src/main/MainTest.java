package tests;

import compiler.generated.Parser;
import compiler.generated.Scanner;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParserTest {
	
	public static void main(String[] args) throws IOException {
		String filePath = "examples/SemanticTest.java";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}
		Parser parser = new Parser(scanner);
		Symbol s = null;
		try {
			s = parser.parse();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Done!");
		System.out.println(s);
	}

}
