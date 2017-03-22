import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.LayoutStyle;

public class Huffman {
	/**
	 * FIELDS
	 */
	private Scanner sc;
	private PriorityQueue<Node> pq;
	private List<String> lst = new ArrayList<String>();
	private HashMap<Character, String> codes = new HashMap<>(); // 
	private String code;
	private String inString;
//	String code;
	private Node root;
	
	
	/**
	 * CONSTRUCTOR
	 */
	public Huffman(String fileName) throws FileNotFoundException, IOException{
		/**
		 * Creates and initialises a Huffman with the frequencies from the specified file. 
		 * This should read through the entire file and count the number of occurrences of each
		 *  character. It should then generate the appropriate 
		 * tree/codes based on these frequencies. You may assume that the file will contain
		 *  at least two distinct characters.
		 */
//		File file = new File(fileName);
		BufferedReader in = new BufferedReader(new FileReader(fileName));
//      sc = new Scanner(file);
		pq = new PriorityQueue<Node>();
		HashMap<Character,Integer> map = new HashMap<Character,Integer>();   
		ArrayList<Character> ls = new ArrayList<Character>();
      int i;
//		while (sc.hasNext()){
      while((i = in.read()) != -1) {
//			String s = sc.nextLine();
//			System.out.println(s);
//			for(int i = 0; i < s.length(); i++){
//				char c = s.charAt(i);
             char c = (char) i;
				 Integer val = map.get(new Character(c));
				 if(val != null){
				     map.put(c, new Integer(val + 1));
				   }else{
				     map.put(c,1);
				   }
				
//			}
         // why counting spaces? The newline in the file has been dropped by using nextLine()
         // instead use BufferedReader in. And then while((i = in.read()) != -1) { ... }
//			int spaces = s.length() - s.replace(" ", "").length();
//			map.put(' ' , spaces);
		}
		//System.out.println(map.values());
		//System.out.println(map.keySet());
		
		
		
		
		Node newNode;
		for(Character c: map.keySet()){
			newNode = new Node(c, map.get(c));
			pq.add(newNode);
		}
		int size = pq.size();
/*		
		//FOR TESTING ORDER============
				PriorityQueue<Node> pq2 = new PriorityQueue<Node>();

				for(int i = 0; i < size; i++){
					System.out.println("TESTING");
					Node tmp = pq.remove();
					System.out.println(tmp.val + " with a freq of " + tmp.freq);
					pq2.add(tmp);
				}
				//put it back in pq!!!
				for(int j = 0; j <size; j++)
					pq.add(pq2.remove());
				//END IF TESTING ORDER ================
*/		
		
		
		/**
		 * QUESTION: ASCII value for blank space. should the lowerst ASCII be removed first or the opposite?
		 * Ask about comparing ties.
		 */
		while(pq.size()>1){
			//System.out.println(pq.size() + " size");
			
			Node tmp = new Node(pq.remove(),pq.remove());
			//System.out.println(tmp.left.val + " and " + tmp.right.val);
			//requeue the node. its priority should put it somehwere in the middle for the example.
			//QUESTION:Is the new nodes potition determined by its smaller ASCII value???
			pq.add(tmp);
			//System.out.println(tmp.freq);
		}
		
		//Make it a node with two children as the other nodes
		if(pq.size()==1){
			//SNode tmp;
		}
//		StringBuilder sb = new StringBuilder();
		/**
		 * TO BE MOVED INTO COMPRESS METHOD ===========================
		 */
//		int size2 = pq.size();
//		Node tmp = pq.remove();
	
//		code = new String();
//		codes = new HashMap<Character, String>();
		//create method to navigate downtree and append 0 or 1 where necessary
//		tmp.getCodes(code,-1);

		root = pq.peek(); // the single last element is the root
      root.getCodes("", -1);  // what is count = -1 used for?

		//System.out.println("Array: " + sb.toString());
		//for(Character c: codes.keySet())
		//	System.out.println(c + " has a string of " + codes.get(c));
	}
	
	 
	
	/**
	 * METHODS
	 */
	public void compress(String infileName, String outfileName)
              throws FileNotFoundException,IOException{	
//		File file = new File(infileName);
//		Scanner sc = new Scanner(file);
//		FileOutputStream out = new FileOutputStream(outfileName);
		BufferedReader in = new BufferedReader(new FileReader(infileName));
		BufferedWriter bf = new BufferedWriter(new FileWriter(outfileName));
		
      int i;
//		while (sc.hasNext()){
      while((i = in.read()) != -1) {
             char c = (char) i;
//		
            String code = codes.get(c);
          
				bf.write(code);
				
//			}
//			int spaces = s.length() - s.replace(" ", "").length();
			//map.put(' ' , spaces);
		}
//		sc.close();
      in.close();
		bf.close();
		
	}
	
	public void decompress(String infileName, String outfileName)
              throws FileNotFoundException,IOException{
	//	ArrayList<String> words = new ArrayList<String>();
		File file = new File(infileName);
		//Scanner sc = new Scanner(file);
//		FileOutputStream out = new FileOutputStream(outfileName);
		BufferedReader in = new BufferedReader(new FileReader(infileName));
		BufferedWriter bf = new BufferedWriter(new FileWriter(outfileName));
        //System.out.print(1);

      // create a long input string
      String inStr = new String();
      int c;
      //long startTime = System.nanoTime(); // start timer
      Node node = root;
      code = new String();
      StringBuilder sb = new StringBuilder();
      while((c = in.read()) != -1) {
    	  //uses Char c to navigate down the tree and append the letter===================
			if (node.left == null|| node.right == null ){ //if a leaf node
				//inStr += node.val;
				sb.append(node.val);
				node = root;
			}
			 if(node.left !=null || node.right !=null){ // if not a leaf node
				if(c == '0'){
					node = node.left;
				}
				else if(c == '1'){
					node = node.right;
				}
			}
		}
      //inStr += node.val;
      sb.append(node.val);
      in.close();
     // long endTime = System.nanoTime(); // end timer 
      //long duration = (endTime - startTime)/1000000; // duration in milliseconds
    //  System.out.println(duration);
    
      
     /**
         while(inString.length() > 0) {
            root.getCharacter(inString); //sorts through input string and traverses tree to make string
//				bf.write(codes.get(c));
			}
         //System.out.println(code);
         //for(String s : words) {
			bf.write(code);
	//	}
      **/
	  bf.write(sb.toString());
      bf.close();
	}
	
	public String toString(){
		String sb = new String();
		ArrayList<String> al = new ArrayList<String>();
		root.getString(sb, al);
		sb+="|";
		for(String s: al)
			sb += s;
		sb+="|";
		return sb;
	}
	
	
	/**
	 * PRIVATE NODE INNER CLASS
	 */
	private class Node implements Comparable<Node>{
		public Node left;
		public Node right;
		public int freq;
		public char val;
		public Node(char val, int freq){
			this.val = val;
			this.freq = freq;
			this.left = null;
			this.right = null;
		}
		public Node(Node left, Node right){
			this.freq = left.freq + right.freq;
			//QUESTION:DO I SET A VAL TO DETERMINE WHO COMES FIRST IN FREQ TIE?
			this.val = lowerVal(left,right);
			this.left = left;
			this.right = right;
			
		}
		//IMPLEMENT
		public char lowerVal(Node n1, Node n2){
			if(n1.val < n2.val)
				return n1.val;
			else if( n1.val > n2.val)
				return n2.val;
			else{
				//NOT SURE WHAT TO PUT HERE. PLEASE RMEMEBER TO FIX LATER. PLEASE.
				return n1.val;
			}
			
		}
		//Deleted addFreq method that did: freq++;
		public int compareTo(Node other) {
			if(freq > other.freq)
				return 1;
			else if(freq < other.freq)
				return -1;
			else{
				//testing 
				if((int)val > (int)other.val){
					return 1;
				}
				else{
					return -1;
				}
			}
				
		}		
		public void getCodes(String sb, int count) {
			if(this.left != null && this.right!=null){
			//	System.out.println(val);
				left.getCodes(sb+"0", count);
				right.getCodes(sb+ "1", count);
			}
			else{
				//System.out.println(val + "has no children");
			//	System.out.println(sb);
				codes.put(val, sb);
			}
			
		}
		
		public void getCharacter(String sb) {		
//         System.out.println("getCharacter: " + sb);
         if(this.left == null && this.right == null) {   // leave node
				//count.add(String.valueOf(val));
				//System.out.println(val);
				code += String.valueOf(val);
				inString = sb;
//				System.out.println("Found: " + val);
            //return sb;
         }
         else if(sb.charAt(0) == '0'){
				//sb = sb.substring(1);
				left.getCharacter(sb.substring(1));
			}
			else if(sb.charAt(0) == '1'){
				//sb = sb.substring(1);
				right.getCharacter(sb.substring(1));
			}
         //return sb;
      }
      
		public void getWords(String sb, ArrayList<String> count) {
				
//         System.out.println("getWords: " + sb);
         if(this.left == null && this.right == null) {   // leave node
				count.add(String.valueOf(val));
//				System.out.println("Found: " + val);
				if(sb.length() > 0){
				//	System.out.println(sb);
					root.getWords(sb, count);
				}
         }
//         if(this.left != null || this.right!=null){
				if(sb.charAt(0) == '0'){
					sb = sb.substring(1);
					left.getWords(sb, count);
				}
				 if(sb.charAt(0) == '1'){
					sb = sb.substring(1);
					right.getWords(sb, count);
				}
//			}
			
         /*
			else{
				count.add(String.valueOf(val));
				//System.out.println(count);
				if(sb.length()> 0){
				//	System.out.println(sb);
					root.getWords(sb, count);
				}
				else{
					System.out.println("SB length = " + sb.length() + " method should end here");
					//return;
				}
			
			}
         */
		}
		
		public void getString(String sb, ArrayList<String> count) {
			if(this.left != null && this.right!=null){
				//System.out.println(val + " value");
				left.getString(sb, count);
				right.getString(sb, count);
			}
			else{
				//System.out.println(val + "has no children");
			//	System.out.println(sb);
				count.add(String.valueOf(val));
				//System.out.println(count);
			}
			
		}
		
		
	}
}
	

