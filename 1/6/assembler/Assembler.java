package assembler;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Assembler {
    private final Parser parser;
    private final Code code;
    private final SymbolTable table;
    private final BufferedWriter writer;
    private int nextRAMaddress = 16;

    public Assembler(String fileName) throws IOException {
        // arguments will be in form Name.asm, should be inside Asm folder
        String currentWorkingDir = System.getProperty("user.dir");
        String readFilePath = currentWorkingDir + File.separator + "Asm" + File.separator + fileName;
        String writeFilePath = currentWorkingDir + File.separator + "Hack" + File.separator + fileName;


        parser = new Parser(readFilePath);
        code = new Code();
        table = new SymbolTable();
        writer = new BufferedWriter(new FileWriter(writeFilePath));
    }

    public void assemble() throws IOException {
        firstPass();
        secondPass();
    }

    private void handleCCommand() throws IOException {
        String destBinary = code.dest(parser.dest());
        // compBinary includes A Bit
        String compBinary = code.comp(parser.comp());
        String jumpBinary = code.jump(parser.jump());

        String machineCode = "111" + compBinary + destBinary + jumpBinary;
        writer.write(machineCode);
        writer.newLine();
    }

    private void handleACommand() throws IOException {
        String symbol = parser.symbol();
        String machineCode = "";
        try {
            machineCode = getBinaryForACommand(symbol);
        }

        // if it wasn't a numeric address
        catch (NumberFormatException exception) {
            if (table.contains(symbol)) {
                int address = table.getAddress(symbol);
                machineCode = getBinaryForACommand(Integer.toString(address));
            }

            else {
                machineCode = getBinaryForACommand(Integer.toString(nextRAMaddress));
                table.addEntry(symbol, nextRAMaddress++);
            }
        }

        finally {
            writer.write(machineCode);
            writer.newLine();
        }
    }

    private String getBinaryForACommand(String symbol) throws NumberFormatException {
        return "0" + code.toBinary(symbol);
    }

    private void firstPass() throws IOException {
        int lineNum = 0;
        while (parser.hasMoreCommands()) {
            boolean isCommand = !(parser.lineType() == Parser.Line.COMMENT_OR_WHITESPACE);

            boolean isLCommand = parser.lineType() == Parser.Line.L_COMMAND;
            if (isLCommand) {
                table.addEntry(parser.label(), lineNum);
            }
            else if (isCommand){
                lineNum++;
            }

            parser.advance();
        }
        parser.reset();
    }

    private void secondPass() throws IOException {
        while (parser.hasMoreCommands()) {
            Parser.Line command = parser.lineType();

            if (command == Parser.Line.C_COMMAND) {
                handleCCommand();

            } else if (command == Parser.Line.A_COMMAND) {
                handleACommand();

            }

            parser.advance();
        }

        writer.close();
    }
}
