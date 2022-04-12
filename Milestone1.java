// project description: https://docs.google.com/document/d/1ZEOO4xghB8z3svFESZ9bEfvWGwzqXBxg3NSBFpfGYPw/edit
import java.util.HashMap;

class Milestone1 {

  public HashMap<String, Integer> variableList = new HashMap<>();

  // static is me being lazy, fix later
  public HashMap<String,Integer> ParseFunctionHeader(String header) {
    String[] splitString = header.split(" "); 

    HashMap<String, Integer> vars = new HashMap<>();


    // start at 1 to avoid catching start of line int - ew tho
    for (int i = 1; i < splitString.length; i++) {
      //System.out.println(splitString[i]);
      if (splitString[i].equals("int")) { 
        // For milestone 1:
        System.out.printf("%s ", splitString[i+1]);
        
        // we should have certainty that splitString[i+1] exists but maybe check anyway?
        vars.put(splitString[i + 1], 0); // val will change in milestone 2
      }
    }
    System.out.println();
    return vars; 
  }

  public void parseLine(String line) {

    String[] splitString = line.split(" ");
    if (!splitString[0].equals("int")) {
      return;
    }

    variableList.put(splitString[1], 0);
    System.out.print(" \n" + splitString[1]+ " ");
    
    for (int i = 2 ; i < splitString.length; i++) {
        if (splitString[i-1].equals(",") )
        {
          variableList.put(splitString[i], 0);
          System.out.print(splitString[i]+ " "); // for milestone 1 only
        }
    }
 
  }

  public static void main(String[] args) {

    Milestone1 m = new Milestone1();

    String test0 = "int fun0 ( ) {";
    String test1 = "int fun1 ( int a ) {";
    String test2 = "int fun2 ( int dog , int cat ) {";
    String test3 = "int fun3 ( int x , int y , int z ) {";

    // sigh
    m.ParseFunctionHeader(test0);
    m.ParseFunctionHeader(test1);
    m.ParseFunctionHeader(test2);
    m.ParseFunctionHeader(test3);

    String test4 = "int x ;";
    String test5 = "int j , k ;";
    String test6 = "int y = 2 ;";
    String test7 = "int a = x + 5 ;";
    String test8 = "int b = a + y + 8 ;";
    String test9 = "int c = a + x , d , e = b ;";

    //running tests
    m.parseLine(test4);
    m.parseLine(test5);
    m.parseLine(test6);
    m.parseLine(test7);
    m.parseLine(test8);
    m.parseLine(test9);    
  }
}