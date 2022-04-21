import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.text.TableView;
import java.util.*;

public class Milestone2 {

    public boolean checkLegal (String codeLine){

        String[] splitLine = codeLine.split(" ");
        if (splitString.length < 4 ) return true;

        if (splitLine[3].equals("int") ) //it is declartion statement
        {
            if (!splitLine[length - 1].equals(";")) // line doesnt contain semi-colon at the end
            {
                return false;
            }
            for (int i = 5; i < splitLine.length ; i++){ //check variables
                if (splitLine[i-2].equals("int") || splitLine[i-2].equals(",")  )//then index is variable
                {
                    if (!splitLine[i+2].equals("=") || !splitLine[i+2].equals(";") || !splitLine[i+2].equals(",") )//wrong declaration
                        return false;

                        if (!splitLine[i+2].equals("=")){ //ensure proper assignment that ends with ; or ,
                            //for (int j = i+2; j < codeLine.length ; i++)
                            //{
                                
                            //}
                        }
                }
            }
            return true;
        }
        else if (codeLine[3].equals("return") ) //is return statement
        {

        }
        else //is assignment statement
        {

        }

    
    }

    public String[] readFile(String filename)
    {
        ArrayList<String> rslt = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename));) {
            while (br.ready()) {
                br.read();
                String l = br.readLine();
                //System.out.println(l);
                rslt.add(l);
            }
            System.out.println("Lines all read ");
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
                //System.out.println(ret[i]);
            }
            return ret;
    }
    
    public void CreateSymbolTable(String [] codeText)
    {
        //String[] splitString = codeText.split("\n");
        
        Milestone1 m1 = new Milestone1(); 

        m1.ParseFunctionHeader(codeText[0]);
        for (int i=1 ; i<codeText.length ; i++)
        {
            m1.parseLine(codeText[i]);
        }

        int Tablesize = m1.variableList.size() + m1.parameterList.size();

        System.out.println("\n\n S : O \n-------");

        // Looping through the HashMap
        // Using for-each loop
        int offset = 4; 
        for (Map.Entry mapElement : m1.parameterList.entrySet()) {
            
            String key = (String)mapElement.getKey();

            System.out.println(" "+key + " : " + offset);
            offset++; 
        }

        offset = 0; 
        for (Map.Entry mapElement : m1.variableList.entrySet()) {
            
            String key = (String)mapElement.getKey();

            System.out.println(" "+key + " : " + offset);
            offset--;
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
        String test0[] = m2.readFile(filenames;
        m2.CreateSymbolTable(test0);
    }
}
