
import compiler.generated.Parser;
import compiler.generated.Scanner;
import java_cup.runtime.*;

import java.io.FileReader;

public class Main {

    public static void main(String[] args) {
        String prog = "src/TestDefault.txt";

        try{
            Scanner scanner = new Scanner(new FileReader(prog));
            System.out.println(">> Successful Lexical Analysis");
            Parser p = new Parser(scanner);
            System.out.println(">> Passou aqui");
            Symbol s = p.parse();
            System.out.println(">> Successful Sintatic Analysis");

            if (s.toString().equals("#0"))
                System.out.println("> SUCCESSFULL COMPILATION");
            else
                System.out.println(s);

        }catch(Exception e){
            System.err.println("Processo de Compilacao Falhou!");
            System.err.println(e.getMessage());
        }
    }

}