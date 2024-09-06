// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

//// Replace this comment with your code.

// product = 0

// @RETURN
// r0||r1; JLE

// LOOP:
//  product += r0
//  R1 -= 1
//  @LOOP
//  times; JGT

// RETURN:
//  r2 = product

// END:
// JMP

@R0
D=M
@RETURN
D;JEQ

@R1
D=M
@RETURN
D;JEQ

// product = 0
@0
D=A
@product
M=D

(LOOP)
    // product += addThis
    @R0
    D=M
    @product
    M=D+M

    // times -= 1
    @R1
    M=M-1
    D=M

    // if times > 0, loop
    @LOOP
    D;JGT


(RETURN)
    // r2 = product
    @product
    D=M
    @R2
    M=D

(END)
    @END
    0;JMP