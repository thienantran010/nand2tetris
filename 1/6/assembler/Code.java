package assembler;

import java.util.HashMap;

public class Code {
    private final HashMap<String, String> destinationsMap = new HashMap<>();
    private final HashMap<String, String> computationsMap = new HashMap<>();
    private final HashMap<String, String> jumpsMap = new HashMap<>();

    public Code() {
        // when destinations is null
        destinationsMap.put("", "000");

        destinationsMap.put("M", "001");
        destinationsMap.put("D", "010");
        destinationsMap.put("MD", "011");
        destinationsMap.put("A", "100");
        destinationsMap.put("AM", "101");
        destinationsMap.put("AD", "110");
        destinationsMap.put("AMD", "111");

        computationsMap.put("0",   "0101010");
        computationsMap.put("1",   "0111111");
        computationsMap.put("-1",  "0111010");
        computationsMap.put("D",   "0001100");
        computationsMap.put("A",   "0110000");
        computationsMap.put("!D",  "0001101");
        computationsMap.put("!A",  "0110001");
        computationsMap.put("-D",  "0001111");
        computationsMap.put("-A",  "0110011");
        computationsMap.put("D+1", "0011111");
        computationsMap.put("A+1", "0110111");
        computationsMap.put("D-1", "0001110");
        computationsMap.put("A-1", "0110010");
        computationsMap.put("D+A", "0000010");
        computationsMap.put("D-A", "0010011");
        computationsMap.put("A-D", "0000117");
        computationsMap.put("D&A", "0000000");
        computationsMap.put("D|A", "0010101");

        computationsMap.put("M",   "1110000");
        computationsMap.put("!M",  "1110001");
        computationsMap.put("-M",  "1110011");
        computationsMap.put("M+1", "1110111");
        computationsMap.put("M-1", "1110010");
        computationsMap.put("D+M", "1000010");
        computationsMap.put("D-M", "1010011");
        computationsMap.put("M-D", "1000111");
        computationsMap.put("D&M", "1000000");
        computationsMap.put("D|M", "1010101");


        // when jumps is null
        jumpsMap.put("", "000");

        jumpsMap.put("JGT", "001");
        jumpsMap.put("JEQ", "010");
        jumpsMap.put("JGE", "011");
        jumpsMap.put("JLT", "100");
        jumpsMap.put("JNE", "101");
        jumpsMap.put("JLE", "110");
        jumpsMap.put("JMP", "111");
    }

    // includes A bit
    public String comp(String compAssembly) {
        return computationsMap.get(compAssembly);
    }

    public String dest(String destAssembly) {
        return destinationsMap.get(destAssembly);
    }

    public String jump(String jumpAssembly) {
        return jumpsMap.get(jumpAssembly);
    }

    public String toBinary(String symbol) {
        int decimalNum = Integer.parseInt(symbol);
        return String.format("%15s", Integer.toBinaryString(decimalNum)).replace(' ', '0');
    }
}
