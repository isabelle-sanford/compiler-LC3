import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.text.TableView;


public class Milestone3 {

    
    private Milestone1 m1 = new Milestone1();
    private Milestone2 m2 = new Milestone2();
    String [] codeLines; 

    public Milestone3(String filename)
    {
        codeLines = readFile(filename);
        m2.CreateSymbolTable(codeLines, m1);
    }

    /**
     * function that read files 
     * @param filename
     * @return array of strings
     *          each string is a single code line
     */

    public String[] readFile(String filename)
    {
        ArrayList<String> rslt = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename));) {
            while (br.ready()) {
                br.read();
                String l = br.readLine();
                rslt.add(l);
            }
        } catch (FileNotFoundException fnf) {
            System.err.println("Could not open the file" + fnf);
        } catch (IOException ioe) {
            System.err.println("Reading problem" + ioe);
        }

        if (rslt==null || rslt.size()==0) //checks if array list is empty 
                return null; // if empty return null
            String ret[]= new String[rslt.size()]; //array declared 
            for (int i=0; i<rslt.size(); i++) // 
            {
                ret[i]=rslt.get(i);
            }
            return ret;
    }

    /**
     * function that sorts code lines into what case the fit best
     */
    public void sort()
    {
        for (int i=1 ; i<codeLines.length ; i++)
        {
            boolean containsRet = false;
            boolean containsInt = false;
            boolean containsEq = false;

            String [] splitString = codeLines[i].split(" ");

            for (int j=0 ; j<splitString.length ; j++){
                if (splitString[j].equals("int"))
                    containsInt = true; 

                if (splitString[j].equals("return"))
                    containsRet = true;
                
                if (splitString[j].equals("="))
                    containsEq = true;
            }

            if (containsInt && containsEq)  {
                System.out.println("\n;;"+codeLines[i]);
                generateIntializing(codeLines[i]);
            }
            else if (containsRet)
            {
                System.out.println("\n;;"+codeLines[i]);
                generateReturn(codeLines[i]);
            } 
            else if (containsEq)
            {
                System.out.println("\n;;"+codeLines[i]);
                generateAssignment(codeLines[i]);
            } 
        }
    }

    public void generateSingle(String storeValue){
        if (m2.SymbolTable.containsKey(storeValue) ) //if storeValue is a variable
        {   
            int offset = m2.SymbolTable.get(storeValue);
            System.out.println("LDR R0, FP, "+offset+"; read "+storeValue);
        }
        else //if it is a literal
        {
            System.out.println("AND R0, R0, 0; clear R0");
            int number = Integer.parseInt(storeValue);
            while ( number > 0 )
            {
                if (number <= 15 )
                {
                    System.out.println("ADD R0, R0, " + number+"; add "+number+" to R0");
                    break;
                }
                else
                {
                    number = number - 15; 
                    System.out.println("ADD, R0, R0, 15; add 15 to R0");
                }
            }
        }

    }

    /**
     * function that generates assemble code 
     * for code lines containing equations
     * @param line
     * @param start
     * @param end
     * 
     */

    public void generateEquation(String line, int start, int end)
    {
        //start is first varibale/literal to be added
        //end is index of either , or ;

        String [] splitLine = line.split(" ");
        int operators = 0;
        int operand;

        for (int i = start; i < end; i++){
            if (splitLine[i].equals("+") ) 
                operators++;
        }
        operand = operators + 1;

        if (operators > 0 ) {
            for (int i = start ; i<end && operand > 0 ; ){

                //first operand  
                if (m2.SymbolTable.containsKey(splitLine[i])) //if operand is a variable
                {
                    int offset = m2.SymbolTable.get(splitLine[i]);
                    System.out.println("LDR R0, FP, "+offset+"; read "+splitLine[i]);
                }
                else //it is a number
                {
                    System.out.println("AND R0, R0, 0; clear R0");
                    int number = Integer.parseInt(splitLine[i]);
                    while ( number > 0 )
                    {
                        if (number <= 15 )
                        {
                            System.out.println("ADD, R0, R0, " + number+"; add "+number+" to R0");
                            break;
                        }
                        else
                        {
                            number = number - 15; 
                            System.out.println("ADD, R0, R0, 15; add 15 to R0");
                        }
                    }
                }
            

            operand--; 
            if(operand <= 0){ 
                System.out.println("ADD R2, R2, R1 ; put sum in R2");
                break;
            }
            i=i+2;

            //second operand
            if (m2.SymbolTable.containsKey(splitLine[i])) //if operand is a variable
            {
                int offset = m2.SymbolTable.get(splitLine[i]);
                System.out.println("LDR R1, FP, "+offset+"; read "+splitLine[i]);

            }
            else //it is a number
            {
                System.out.println("AND R1, R1, 0; clear R1");
                int number = Integer.parseInt(splitLine[i]);
                while ( number > 0 )
                {
                    if (number <= 15 )
                    {
                        System.out.println("ADD R1, R1, " + number+"; add "+number+" to R1");
                        break;
                    }
                    else
                    {
                        number = number - 15; 
                        System.out.println("ADD, R1, R1, 15; add 15 to R1");
                    }
                }
            }
            System.out.println("ADD R2, RO, R1 ; put sum in R2");

            operand--; 
            i= i+2;
            }

        }
        return;
    }

    /**
     * generates assembly code for 
     * @param line
     */

    private void IntializingUtill(String line, String [] splitString, int i_start, int i_end)
    {
        boolean flag = true; 
        for (int i = i_start; i < i_end ; i++){
            if (splitString[i].equals("=")){
                flag = false; 
            }
        }
        
        if (flag) return;

        int idx = 0;
        for (int i = i_start; i <=i_end; i++)
        {
            if (splitString[i].equals("int") || splitString[i].equals(",") )
                idx = i; 
            if (idx == i_end) break;
            if (splitString[i].equals("+")){ //belongs to case 2
                int start = idx + 3; 
                int end = i_end; 
                //System.out.println("1");
                generateEquation(line, start, end);
                int offset = m2.SymbolTable.get(splitString[idx+1]);
                System.out.println("STR R2, FP, "+offset+"; write "+splitString[idx+1]);
                return;
            }
        }
        
        //System.out.println("2 "+splitString[i_start+3]);
        String storeValue = splitString[i_start+3];
        String variable = splitString[i_start+1];
        generateSingle(storeValue);
        int offset2 = m2.SymbolTable.get(variable);
        System.out.println("STR R0, FP, "+offset2+"; write "+variable);
        return;

    }
    public void generateIntializing (String line)
    {
        String [] splitString = line.split(" ");
        int var_num = 1; 
        for (int i = 0; i < splitString.length; i++) {
            if (splitString[i].equals(","))  
                var_num++; 
        }
          
        for (int i=0 ; i < splitString.length && var_num > 0 ; i++){
            int start = 0, end = 0;
            if (splitString[i].equals("int") || splitString[i].equals(",") )
            {
                start = i;
                for (int j=start+1; j<splitString.length ; j++){
                    if (splitString[j].equals(";") || splitString[j].equals(",")) {
                        end = j;
                        //System.out.println("start:"+start+" end:"+end);
                        IntializingUtill(line, splitString, start, end);
                        var_num--;
                        i = i+2;
                    }
                }
            }
        }

                
    }
    
    public void generateReturn (String line)
    {
        String [] splitString = line.split(" ");

        //in the case it looks like this: return x + t 
        int idx = 0;
        for (int i= 0 ; i<splitString.length ; i++)
        { 
            if (splitString[i].equals("return"))
                idx = i; 
            if (splitString[i].equals("+")){
                int start = idx + 1; 
                int end = splitString.length-1; 
                generateEquation(line, start, end);
                System.out.println("STR R2, FP, 3; write RV");
                return; 
            }
        }

        String retValue = splitString[idx+1]; 
        generateSingle(retValue);
        System.out.println("STR R0, FP, 3; write RV");

    }

    public void generateAssignment (String line)
    {
        String [] splitString = line.split(" ");

        int idx = 0;
        for (int i= 0 ; i<splitString.length ; i++){ 
            if (splitString[i].equals("=")){
                idx = i; 
            }
            if (splitString[i].equals("+")){
                String varibale = splitString[idx-1];
                int start = idx + 1; 
                int end = splitString.length-1; 
                generateEquation(line, start, end);
                int offset = m2.SymbolTable.get(varibale);
                System.out.println("STR R2, FP, "+offset+"; write "+varibale);
                return; 
            }
        }

        String storeValue = splitString[idx+1];
        String variable = splitString[idx-1];
        generateSingle(storeValue);
        int offset2 = m2.SymbolTable.get(variable);
        System.out.println("STR R0, FP, "+offset2+"; write "+variable);
        return;
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

        Milestone3 m3 = new Milestone3(filename);
        System.out.println("\n");
        m3.sort();
    }
}
