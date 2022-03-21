/**
 * Assignment: Computer Architecture Project 1 - MIPS Disassembler 
 * Class: CS 472
 * Due Date: Feb. 17. 2022 
 * Author: Shou-Tzu, Han 
 * Description: Create a program that convert the machine code(hex) into R or I format or Branch(assembly language).
 * 
 * Algorithms:
 * 
 *   0. input the instruction and current address
 * 	 1. program counter(current address) + 4 (the address of the next instruction)
 *   2. Use unsigned right shift operator to get MIPS fields.
 *   3. Check the format, if format R(op=0), print function(add, sub, or, slt) and register operands(rs, rt, rd)
 *                        if format I(op=1), print function(lw, sw), register operands(rs, rt) and constant/address
 *                        otherwise is branch instruction, print function(beq, bne), register operands(rs, rt) and branch address(shifted offset field left by 2)
 *   4. Return the address to let the next input instruction used.
 *                       
 */

public class MIPSDisassembler_472_1 {

	public int Disassembler(int instruction, int currentAddress) {
		

        // Set the position of each MIPS fields       
		int opCodeCheck = 0xFC000000;
		int rsCheck = 0x3E00000;
		int rtCheck = 0x1F0000;
		int rdCheck = 0xF800;
		int functCheck = 0x3F;
		int offSetCheck = 0xFFFF;

		int opCode = (instruction & opCodeCheck) >>> 26; // shift the desired position to get the opcode
		int rs;
		int rt;
		int rd;
		int funct;
		short OffSet;

		System.out.print(String.format("0x%05X", currentAddress) + " ");
		
		// compute PC + 4 (the address of the next instruction)
		currentAddress += 0b0100;

		if ((opCode) == 0) { // R format

			rs = (instruction & rsCheck) >>> 21;
			rt = (instruction & rtCheck) >>> 16;
			rd = (instruction & rdCheck) >>> 11;
			funct = instruction & functCheck;
            
			// Find the corresponding function
			if (funct == 0x20) {
				System.out.print("add");
			} else if (funct == 0x22) {
				System.out.print("sub");
			} else if (funct == 0x24) {
				System.out.print("and");
			} else if (funct == 0x25) {
				System.out.print("or");
			} else if (funct == 0x2A) {
				System.out.print("slt");
			} else {
				System.out.println("Out of Range");
			}

			System.out.println(" $" + rd + ", $" + rs + ", $" + rt);
		}

		else { // I format

			rs = (instruction & rsCheck) >>> 21;
			rt = (instruction & rtCheck) >>> 16;
			OffSet = (short) ((instruction & offSetCheck));

			if (opCode == 0x23) { // load word
				System.out.println("lw  $" + rt + ", " + OffSet + "($" + rs + ")");
			} else if (opCode == 0x2B) { // store word
				System.out.println("sw  $" + rt + ", " + OffSet + "($" + rs + ")");
			} else {// branch instruction

				int branchAddress = currentAddress + (OffSet << 2);//  off set field is shifted left 2 bits 

				if (opCode == 0x4) {
					System.out.print("beq");
				}
				if (opCode == 0x5) {
					System.out.print("bne");
				}
				System.out.println(
						" $" + rs + ", $" + rt + ", address " + String.format("0x%05X", branchAddress));
			}
		}

		return currentAddress;
	}

	public static void main(String[] args) {

		MIPSDisassembler_472_1 da = new MIPSDisassembler_472_1();
        
		// given MIPS instructions
		int instructions[] = { 0x032BA020, 0x8CE90014, 0x12A90003, 0x022DA822, 0xADB30020, 0x02697824, 0xAE8FFFF4,
				0x018C6020, 0x02A4A825, 0x158FFFF7, 0x8ECDFFF0 };
		
		// the first instruction begin at 0x9A040
		int currentAddress = 0x9A040;
        
		// use for loop to process each instruction and update the current address
		for (int instruction : instructions) {
			currentAddress = da.Disassembler(instruction, currentAddress);
		}

	}

}
