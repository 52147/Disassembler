# Assignment: Computer Architecture Project 1 - MIPS Disassembler 
Class: CS 472
Due Date: Feb. 17. 2022 
Author: Shou-Tzu, Han 
Description: Create a program that convert the machine code(hex) into R or I format or Branch(assembly language).

Algorithms:

 0. input the instruction and current address
 1. program counter(current address) + 4 (the address of the next instruction)
 2. Use unsigned right shift operator to get MIPS fields.
 3. Check the format, if format R(op=0), print function(add, sub, or, slt) and register operands(rs, rt, rd).
If format I(op=1), print function(lw, sw), register operands(rs, rt) and constant/address.
Otherwise is branch instruction, print function(beq, bne), register operands(rs, rt) and branch address(shifted offset field left by 2).
 4. Return the address to let the next input instruction used.
