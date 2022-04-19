import javax.swing.text.TableView;
import java.util.*;

public class Milestone2 {
    
    public void generateTable(String codeText)
    {
        String[] splitString = codeText.split("\n");
        
        Milestone1 m1 = new Milestone1(); 

        m1.ParseFunctionHeader(splitString[0]);
        for (int i=1 ; i<splitString.length ; i++)
        {
            m1.parseLine(splitString[i]);
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

        String test0 = "int fun ( int a , int b ) {\nint x = 7 ;\nint y = a + b ;\nx = a + 4 ;\nint t = x + b ;\nreturn x + t ;\n}";
        Milestone2 m2 = new Milestone2();
        m2.generateTable(test0);
    }
}
