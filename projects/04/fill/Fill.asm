// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

// Put your code here.

(RUN_LOOP)
@SCREEN
D=A
@i
M=D

@8192
D=D+A
@n
M=D

@KBD
D=M
@WHITE
D;JEQ

(BLACK)
@i
D=M
@n
D=D-M
@RUN_LOOP
D;JEQ
@i
A=M
M=-1
@i
M=M+1
@BLACK
0;JMP

(WHITE)
@i
D=M
@n
D=D-M
@RUN_LOOP
D;JEQ
@i
A=M
M=0
@i
M=M+1
@WHITE
0;JMP

(INF)
@INF
0;JMP
