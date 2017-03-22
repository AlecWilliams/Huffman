import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class HuffmanTests {

	@Test
	public void BasicMethods_test() throws FileNotFoundException, IOException {
		Huffman hf = new  Huffman("textfile.txt");
		hf.compress("textfile.txt", "testresult.txt");
		hf.decompress("testresult.txt", "newtextfile.txt");
		Scanner sc1 = new Scanner(new File("textfile.txt"));
		Scanner sc2 = new Scanner(new File("newtextfile.txt"));
		String input = sc1.nextLine();
		String output = sc2.nextLine();
		assertTrue(input.equals(output));
	}
	@Test(expected=FileNotFoundException.class)
	public void NoFileException_test() throws FileNotFoundException, IOException {
		Huffman hf = new  Huffman("nofile.txt");
	}
	
	@Test(expected=FileNotFoundException.class)
	public void FileException_test() throws FileNotFoundException, IOException {
		Huffman hf = new  Huffman("txtfile.txt");
		hf.compress("nope.txt", "outfile.txt");
	}
	
	

}
