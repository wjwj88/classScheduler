package edcc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Scheduel {
    final static String[] universities = {"EdCC", "CWU", "EWU", "Gonzaga", "Heritage",
    		"Pacific Lutheran", "Seattle Pacific", "Seattle University",
    		"UW Bothell", "UW Seattle", "UW Tacoma", "WSU Pullman",
    		"WSU Tri-Cities", "WSU Vancouver", "WWU", "Whitworth"};
    final static String[] pbe = {"GEOL&101", "BIOL&211", "ENVS&101"};
	final static String englishfile = "src/English.txt";
	final static String mathfile = "src/mathClass.txt";
	final static String csfile = "src/CSclass.txt";
	final static String phyfile = "src/phys.txt";
	final static String chemfile = "src/chem.txt";
	final static String socialfile = "src/SocialSciences.txt";
	final static String humfile = "src/Humanities.txt";
	final static String[] cs1 = {"CS115", "CS131", "CS132", "CS133"};
	final static String[] cs2 = {"CS115", "CS141", "CS142", "CS143"};

    
	public static void main(String[] args) throws IOException {
		DiGraph english = readFile(englishfile);
		DiGraph math = readFile(mathfile);
		DiGraph cs = readFile(csfile);
		DiGraph phys = readFile(phyfile);
		DiGraph chem = readFile(chemfile);
		DiGraph social = readFile(socialfile);
		DiGraph hum = readFile(humfile);
		DiGraph[] dg = {english, math, cs, phys, hum, social};
		process(dg);
	}
	
	// prompts the user to enter class and university data, sorts
	// the schedule and prints the quarter schedule 
	public static void process(DiGraph[] dg){
		printDesci1();
		for(int i=0;i<universities.length;i++){
			System.out.println("\t"+(i+1)+" : "+universities[i]);	
		}
		int n = askUniversity();
		if(!isValidUniver(n)){
			n = 1;
		}
		System.out.println();
		System.out.println("Great! You have selected "+universities[n-1]);
		System.out.println();
		ArrayList<List<String>> res = createClassList(dg, n);

		String input = askEnglish();
		while(!isValid(input, dg[0])){
			input = askEnglish();
		}
		res.set(0, removeClass(res.get(0), input));
		input = askMath();
		while(!isValid(input, dg[1])){
			input = askMath();
		}
		res.set(1, removeClass(res.get(1), input));

		input = askSpeci();
		if(isValid(input, dg[4])){
			if((!res.get(4).contains(input))&&(!res.get(6).contains(input))){
				res.get(6).add(input);
			}
		}
		if(isValid(input, dg[5])){
			if((!res.get(5).contains(input))&&(!res.get(6).contains(input))){
				res.get(6).add(input);
			}
		}
		Set<String> set = new HashSet<>();
		LinkedList<ArrayList<String>> result = makeScheduel(res, set);

		for(int i=0;i<result.size();i++){
			if(!result.get(i).isEmpty()){
				System.out.println("quarter "+(i+1)+" : "+result.get(i));	
			}					
		}
	}
	
	// check if all the prerequisites of physics have been satisfied
	public static boolean checkPhys(Set<String> set){
		return (set.contains("MATH151"))&&(set.contains("ENG101"));
	}
	
	//check if the the user enter a valid numer to represent the university
	public static boolean isValidUniver(int n){
		if(n<1||n>16){
			return false;
	    }				
		return true;
	}
	
	// prompts the users for university information
	public static int askUniversity(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of which university you want to transfer to.");		
		System.out.println("All the numbers associate with universities are listed above.");
		System.out.println("If you enter an invalid number, then default number 1 (EdCC)");
		System.out.print("will be used:\t");
		String input = in.nextLine();
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return 1;
		}		
	}
	
	// prompts the users for specifical courses he/she likes to take
	public static String askSpeci(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter one Humanities or Social Sciences class that you want");
		System.out.println("to put into your schedule. It must be either Humanities or");
		System.out.println("Social Sciences class. If the input is invalid, then it won't");
		System.out.println("be taken into consideration. If unsure, just leave it");
		System.out.println("blank, and press enter:");
		System.out.print("\t");
		String input = in.nextLine();
		return input;
	}
	
	// prompts the users for math class
	public static String askMath(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your math level. It should be one of ");
		System.out.println("MATH087, MATH097, MATH141, MATH142, MATH151:");
		System.out.print("\t");
		String input = in.nextLine();
		return input;
	}
	
	// prompts the users for english class
	public static String askEnglish(){
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter your English placement test level. ");
		System.out.println("it should be either ENG099, ENG101 or just leave it blank.");
		System.out.print("\t");
		String input = in.nextLine();
		return input;
	}
	
	// check if the user input is valid string.
	// eg. ENG 101 is not accepted while ENG101 is OK.
	// case insensitive
	public static boolean isValid(String s, DiGraph g){
		String[] words = s.split("\\s+");
		if(words.length>1){
			return false;
		}
		if(!g.NodeList().contains(s.toUpperCase())){
			if(words.length==1){
				return false;
			}else{
				return true;
			}			
		}
		return true;
	}
	
	// print some introduction to this project
	public static void printDesci1(){
		System.out.println("This project helps students majoring in computer science");
		System.out.println("at EdCC make education plan and makes a quarter-based schedule.");
		System.out.println("It askes the user to enter his/her placement test level class");
		System.out.println("and which unuversity he/she wishes to transfer. Additionally, it");
		System.out.println("also allowes the user to enter one elective classes he/she really");
		System.out.println("likes to take, but only for Social Sciences and Humanities classes.");
		System.out.println("By default, this program doesn't show any chemistry classes except");
		System.out.println("you enter one when asked to.");
		System.out.println("The universities are listed as below:");
		System.out.println("Note that the number ");		
	}
	
	// store all the courses the users need to take for graduation 
	// based on which university he/she wants to transfer to
	public static ArrayList<List<String>> createClassList(DiGraph[] arr, int n){
		List<String> csRequ1 = new ArrayList<String>();
		List<String> csRequ2 = new ArrayList<String>();
		for(int i=0;i<cs1.length;i++){
			csRequ1.add(cs1[i]);
		}
		for(int i=0;i<cs2.length;i++){
			csRequ2.add(cs2[i]);
		}
		ArrayList<List<String>> result = new ArrayList<List<String>>();
		for(int i=0;i<4;i++){
			result.add(arr[i].topoSort());			
		}
		result.add(get3Cla(arr[4]));// hum
		result.add(get3Cla(arr[5]));// ss
		result.add(getSpre(n));// 6 special requirements
		if(n==4){
			String temp = "";
			for(String s : result.get(4)){
				if((!s.equals("PHIL&101"))&&(!s.equals("CMST&101"))){
					temp = s;
					break;
				}
			}
			result.get(4).clear();
			result.get(4).add(temp);
			result.get(4).add("PHIL&101");
			result.get(4).add("CMST&101");
		}
		if(n==12||n==13||n==14){
			if((!result.get(5).contains("ECON&201"))&&(!result.get(5).contains("ECON&202"))){
				result.get(5).set(0, "ECON&201");
			}
		}
		if(n==16){
			if(!result.get(4).contains("CMST&101")){
				result.get(4).set(0, "CMST&101");
			}
		}
		if(n==7||n==13){
			result.set(2, csRequ1);
		}
		if(n==2||n==10||n==11){
			result.set(2, csRequ2);
		}
		return result;		
	}
	// get special requirements for special university
	public static List<String> getSpre(int n){
		List<String> result = new ArrayList<String>();
		if(n==3){
			result.add("MATH272");
		}
		if((n>=6&&n<=8)||(n>=12&&n<=15)){
			result.add(getPbe());
		}
		if(n==9||n==11){
			result.add("MATH146");
		}
		return result;
	}
	
	// get a random courses for Physical, Biological and/or Earth 
	// Sciences with lab (GEOL& 101, BIOL& 211, or ENVS& 101)– 5 credits
	public static String getPbe(){
		Set<String> set = new HashSet();
		for(int i=0;i<pbe.length;i++){
			set.add(pbe[i]);
		}
		return getRandom(set);
	}
	
	// get 3 random classes
	public static List<String> get3Cla(DiGraph g){
		List<String> result = new ArrayList<String>();
		while(result.size()<3){
			String s = getRandom(g.NodeList());
			if(!result.contains(s)){
				result.add(s);
			}
		}
		return result;		
	}
	
	// read the courses data stored in the txt  
	// file and then make a graph 
    public static DiGraph readFile(String file) throws IOException {
    	DiGraph graph = new DiGraph();
        try {
        	File f = new File(file);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
            	String[] nodes = readLine.split("\\s+");
            	if(nodes.length==2){
            		graph.addEdge(nodes[0], nodes[1]);
            	}else{
            		graph.addNode(nodes[0]);
            	}            	
            }
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return graph;
    }
    
    // remove the unnecessary courses the user doesn't have to take
    // based on his/her placements test level
    public static List<String> removeClass(List<String> list, String node){//
    	if(list.contains(node.toUpperCase())){
    		int index = list.indexOf(node.toUpperCase());
    		list = list.subList(index, list.size());    		
    	}
    	return list;
    }
    
    // make quarter schedule based on the user's course information
    public static LinkedList<ArrayList<String>> makeScheduel(
    		                 ArrayList<List<String>> res, Set<String> set){//
    	LinkedList<ArrayList<String>> result = new LinkedList<ArrayList<String>>();
    	
    	while(!isDone(res)){
    		ArrayList<String> quarter = new ArrayList<String>();
    		if(!res.get(0).isEmpty()){
    			add2Quarter(0, quarter, res, set);
    		}else{
    			if(checkPhys(set)){
    				if(!res.get(3).isEmpty()){
    					add2Quarter(3, quarter, res, set);
    			    }else{
    			    	if(!res.get(4).isEmpty()){
    			    		add2Quarter(4, quarter, res, set);
    			    	}
    			    }
    		    }else{
			    	if(!res.get(4).isEmpty()){
			    		add2Quarter(4, quarter, res, set);
			    	}
    		    }
    		}
    		if(!res.get(1).isEmpty()){
    			add2Quarter(1, quarter, res, set);
    		}else{
    			if(!res.get(5).isEmpty()){
    				add2Quarter(5, quarter, res, set);
    			}
    		}
    		if(!res.get(2).isEmpty()){
    			add2Quarter(2, quarter, res, set);
    		}else{
    			if(!res.get(6).isEmpty()){
    				add2Quarter(6, quarter, res, set);
    			}
    		}
    		while(quarter.size()<3){
    			if(res.get(4).isEmpty()&&res.get(5).isEmpty()&&res.get(6).isEmpty()){
    				break;
    			}
    			if(!res.get(4).isEmpty()){
    				add2Quarter(4, quarter, res, set);
        			if(quarter.size()==3){
        				break;
        			}
    			}
    			if(!res.get(5).isEmpty()){
    				add2Quarter(5, quarter, res, set);
        			if(quarter.size()==3){
        				break;
        			}
    			}
    			if(!res.get(6).isEmpty()){
    				add2Quarter(6, quarter, res, set);
        			if(quarter.size()==3){
        				break;
        			}
    			}
    		}
    		result.add(quarter);
    	}
    	return result;
    }
    
    // add created courses to the current quarter
    public static void add2Quarter(int n, ArrayList<String> quarter,
    		                ArrayList<List<String>> res, Set<String> set){
		quarter.add(res.get(n).get(0));
		set.add(res.get(n).get(0));
		res.get(n).remove(0);
    }
    
    // check if all the required coursed are done
    public static boolean isDone(ArrayList<List<String>> res){
    	boolean result = true;
    	for(int i=0;i<res.size();i++){
    		if(!res.get(i).isEmpty()){
    			result = false;
    		}
    	}
    	return result;
    }
    
    
    // get a random course from the set containing all the courses
    public static String getRandom(Set<String> set){
    	List<String> asList = new ArrayList(set);
    	Collections.shuffle(asList);
    	return asList.get(0);    	
    }    
}
