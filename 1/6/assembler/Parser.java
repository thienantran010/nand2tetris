package assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    public String filePath;
    private BufferedReader reader;
    private String currentLine;

    // Constructor to initialize the file reader
    public Parser(String filePath) throws IOException {
        this.filePath = filePath;
        reader = new BufferedReader(new FileReader(filePath));
        advance();  // Read the first line
    }

    // Check if there are more lines to process
    public boolean hasMoreCommands() {
        return currentLine != null;
    }

    // Reads the next line in the file
    public void advance() throws IOException {
        currentLine = reader.readLine();

        if (currentLine == null) {
            return;
        }

        currentLine = currentLine.trim();
    }

    public Line lineType() {
        boolean isACommand = currentLine.startsWith("@");
        boolean isLabel = currentLine.startsWith("(") && currentLine.endsWith(")");

        if (isACommand) {
            return Line.A_COMMAND;
        }

        if (isLabel) {
            return Line.L_COMMAND;
        }

        if (isComment() || isWhitespace()) {
            return Line.COMMENT_OR_WHITESPACE;
        }

        return Line.C_COMMAND;
    }

    private boolean isComment() {
        return currentLine.startsWith("//");
    }

    private boolean isWhitespace() {
        return currentLine.isEmpty();
    }

    public String symbol() {
        if (lineType() == Line.A_COMMAND) {
            return currentLine.substring(1);
        }

        return "";
    }

    // Methods to extract fields from C commands
    public String dest() {
        if (lineType() == Line.C_COMMAND) {
            int equalsIndex = currentLine.indexOf('=');
            if (equalsIndex != -1) {
                return currentLine.substring(0, equalsIndex);
            }
        }

        return "";
    }

    public String comp() {
        if (lineType() == Line.C_COMMAND) {
            int equalsIndex = currentLine.indexOf('=');
            int semicolonIndex = currentLine.indexOf(';');
            boolean equalsExists = equalsIndex != -1;
            boolean semicolonExists = semicolonIndex != -1;

            if (equalsExists && semicolonExists) {
                return currentLine.substring(equalsIndex + 1, semicolonIndex);
            }

            if (equalsExists) {
                return currentLine.substring(equalsIndex + 1);
            }

            if (semicolonExists) {
                return currentLine.substring(0, semicolonIndex);
            }

            // if we get here, the C-Command is malformed (we assume it isn't though)
            return "";

        }

        return "";
    }

    public String jump() {
        if (lineType() == Line.C_COMMAND) {
            int semiColonIndex = currentLine.indexOf(';');
            if (semiColonIndex != -1) {
                return currentLine.substring(semiColonIndex + 1);
            }
        }

        return "";
    }

    public String label() {
        if (lineType() == Line.L_COMMAND) {
            int start = currentLine.indexOf("(") + 1;
            int end = currentLine.indexOf(")");
            return currentLine.substring(start, end);
        }

        return "";
    }

    public void reset() throws IOException{
        reader.close();
        reader = new BufferedReader(new FileReader(filePath));
        advance();  // Read the first line
    }

    // Enum to represent what the line is
    public enum Line {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND,
        COMMENT_OR_WHITESPACE
    }
}
