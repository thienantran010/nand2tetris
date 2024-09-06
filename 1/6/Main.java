import assembler.Assembler;
import java.util.Scanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the assembly file to assemble: ");

        String fileName = scanner.nextLine();
        Assembler assembler = new Assembler(fileName);
        assembler.assemble();
    }
}
