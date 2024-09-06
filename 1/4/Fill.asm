// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

//// Replace this comment with your code.

(PROBE)
    @KBD
    D=M
    @SET_BLACK
    D;JGT

(SET_WHITE)
    @color
    M=0
    @SET_SCREEN
    0;JMP

(SET_BLACK)
    @color
    M=-1

(SET_SCREEN)
    @SCREEN
    D=A
    @current_word
    M=D

(SET_SCREEN_LOOP)
    @color
    D=M
    @current_word
    A=M
    M=D

    @current_word
    M=M+1
    D=M

    @24576
    D=D-A
    @SET_SCREEN_LOOP
    D;JLT

@PROBE
0;JMP