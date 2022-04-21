import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.text.TableView;
import java.util.*;

public class Milestone2 {

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
    public static void main(String[] args) {

        Milestone2 m2 = new Milestone2();
        String test0[] = m2.readFile("sample.code");
        m2.CreateSymbolTable(test0);
    }
}
