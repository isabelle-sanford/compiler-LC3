import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.text.TableView;
import java.util.*;

public class Milestone2 {

    public HashMap<String, Integer> SymbolTable = new HashMap<String, Integer>();

    /**
     * function checks if string is integer or not
     * @param number
     * @return
     */
    public boolean isInt (String number){
        int intValue;
		
        //System.out.println(String.format("Parsing string: \"%s\"", number));
        if(number == null || number.equals("")) {
            //System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }
        
        try {
            intValue = Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            //System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    /**
     * function that checks if the code lines are legal 
     * @param codeLine
     * @param m
     * @return
     */
    
    public boolean checkLegal (String codeLine, Milestone1 m){

        String[] splitLine = codeLine.split(" ");

        if (!splitLine[splitLine.length - 1].equals(";")) // line doesnt contain semi-colon at the end
        {
            return false;
        }
        
        int idx=0; // to check if assignment statement is assignment statment
        for (int i = 0; i < splitLine.length ; i++)
        {
          if (splitLine[i].equals("int"))
            idx =  i; 
        }

        if (splitLine[idx].equals("int") ) //it is declartion statement
        {
            //verify lenght 
            for (int i = idx ; i < splitLine.length ; i++){ //check variables
                if (splitLine[i].equals("int") || splitLine[i].equals(",")  )//then next index is variable
                {
                    if (splitLine.length < i+2 ) return false; 

                    if ( !m.variableList.containsKey(splitLine[i+1]) )
                        if (!m.parameterList.containsKey(splitLine[i+1]) )
                        {
                            return false;
                        }
                    //verify lenght
                    if (!splitLine[i+2].equals("=") && !splitLine[i+2].equals(";") && !splitLine[i+2].equals(",") )//wrong declaration
                        return false;

                    if (splitLine[i+2].equals("=")) //ensure proper assignment that ends with ; or ,
                    {                        
            
                        for (int j = i+3; j < splitLine.length ; j=j+2)
                        {
                            //verify lenght
                            
                            //has to be declared varible or constant
                            if (!isInt(splitLine[j])  )  
                                if ( !m.variableList.containsKey(splitLine[j]) )
                                    if (!m.parameterList.containsKey(splitLine[j]) )
                                    {
                                        return false;
                                    }
                            
                            if (splitLine.length < j+1 ) return false; 

                            if ( !splitLine[j+1].equals(";") && !splitLine[j+1].equals(",")) 
                            {
                                //has to be operator 
                                if (!splitLine[j+1].equals("+"))
                                {
                                    return false; 
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        else if (splitLine[3].equals("return") ) //is return statement
        {
            return true;
        }
        else //is assignment statement
        {
            return true;
        }

    
    }
    
    /**
     * function that creates a symbol table
     * @param codeText
     * @param m1
     */
    public void CreateSymbolTable(String [] codeText,  Milestone1 m1 )
    {        
        m1.ParseFunctionHeader(codeText[0]);
        for (int i=1 ; i<codeText.length ; i++)
        {
            m1.parseLine(codeText[i]);
        }
   
        for (Map.Entry mapElement : m1.parameterList.entrySet()) {
            
            String key = (String)mapElement.getKey();
            int offset = m1.parameterList.get(key);
            SymbolTable.put(key, offset);
 
        }

        for (Map.Entry mapElement : m1.variableList.entrySet()) {
            
            String key = (String)mapElement.getKey();
            int offset = m1.variableList.get(key);
            SymbolTable.put(key, offset);
            //System.out.println(" "+key + " : " + offset);

        }
    }

    static String[] myDefaults = {"sample.code"};
    public static void main(String[] args) {

        String filename;
        if (args.length != 0)
        {
            filename = args[0];
        }
        else
        {
            filename = myDefaults[0];
        }

        Milestone2 m2 = new Milestone2();
        Milestone1 m1 = new Milestone1();

        m1.parameterList.put("x", 0);
        m1.variableList.put("y", 0);
        m1.variableList.put("z", 0);
        m1.variableList.put("j", 0);
        m1.variableList.put("k", 0);
        m1.variableList.put("a", 0);
        m1.variableList.put("b", 0);
        
        String [] test = new String[11];

        test[0] = "int x ;";
        test[1] = "int j , k ;";
        test[2] = "int y = 2 ;";
        test[3] = "int b = a + y + 8 ;";
        test[4] = "int c = a + x , d , e = b ;";
        test[5] = "int x ";
        test[6] = "int j  k ;";
        test[7] = "int y = 2 + ;";
        test[8] = "int a = + 5 ;";
        test[9] = "int = a + y + 8 ;";
        test[10] = "int c = a + x d , e = b ;";

        for (int i = 0; i < test.length; i++) {
            if (!m2.checkLegal(test[i], m1))
            System.out.println("Line Illegal: " + test[i]);
            else
            System.out.println("Line legal: " + test[i]);
        }        

    }
}
