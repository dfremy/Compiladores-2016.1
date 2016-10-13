package main;

import compiler.generated.Parser;
import compiler.generated.Scanner;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainTest {
	
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
		}
		
		System.out.println("The compilation process was successfully finished!");
	}
}
