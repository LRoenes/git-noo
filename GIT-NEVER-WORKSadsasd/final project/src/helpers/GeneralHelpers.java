package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import model.Tokenizer;

public class GeneralHelpers {
    
    static Scanner myScanner = new Scanner(System.in);

    public static ArrayList<String> getUserInput(){

        myScanner.useDelimiter(";");
        String userInput = myScanner.next();
        ArrayList<String> splitedUserInput = Tokenizer.tokenizeUserInput(userInput);
         return splitedUserInput;

    }

    //create tables

    public static void createTable(ArrayList<String> splittedQuery){

        if (splittedQuery.size() < 5) {
            
            System.out.println("Llave, escribe bien mlp");
            return;

        }
        
        String fileName = splittedQuery.get(2) + ".csv";
        String path = "src/myTables/";
        File directory = new File(path);

        try {
            
            File myTable = new File(directory+"\\"+fileName);
            new File(myTable.getParent()).mkdirs();
            if (myTable.createNewFile()) {

                String headers = "";

                for(int i = 3; i<splittedQuery.size(); i = i+2){


                        if (i == splittedQuery.size() - 2) {
                            
                            headers = headers + splittedQuery.get(i);

                            
                        }else{

                            headers = headers + splittedQuery.get(i) + ",";

                        }



                }

                FileWriter writer = new FileWriter(myTable);
                writer.write(headers);
                writer.close();
                //To do: Create properties

                System.out.println("Your table has been created succesfully!");
                getUserInput();
                
            }else{

                System.out.println("An error has ocurred while creating your table");
                getUserInput();


            }

          
        
        } catch (Exception e) {
            e.printStackTrace();
        }

                
        

        //trabajar aqui
        
    }

    //clear


    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }




    public static void closeProgram(){

        myScanner.close();
        
    }

    //list tables

    public static void listAbles(){

        String path = "src/myTables/";
        File directory = new File(path);

        File[] fileDirectory = directory.listFiles();
        

       for (int i = 0; i < fileDirectory.length; i++) {

            System.out.println(fileDirectory[i].getName());
        
       }

    }


    //insert first

    public static void insertInfo(ArrayList<String> query){
        

        String nameofTable = query.get(2);
        String path = "src/myTables/";
        File directory = new File(path);
        File[] fileDirectory = directory.listFiles();
        File sourceFile = new File(path + nameofTable + ".csv");
        
     

        for (int i = 0; i < fileDirectory.length; i++) {
 
             if(fileDirectory[i].getName().equalsIgnoreCase(nameofTable + ".csv")){  
                
                try{  

                    BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
                    String firstLine = reader.readLine();
                    System.out.println(firstLine);
                    reader.close();


                    String[] columns = firstLine.split(",");
                    String[] scrambledColumns = new String[columns.length];
                    String[] emptyArray = new String[columns.length];


                    for(int j = 0; j<query.size();j++){

                        if(query.get(j).equalsIgnoreCase(nameofTable)){ 
                            
                            for(int m = j; m<query.size();m++){ 
                                
                                if(query.get(m).equalsIgnoreCase("value")){ 

                                    List<String> subList = query.subList(j+1,m);
                                    scrambledColumns = subList.toArray(new String[0]);
                                    
                                }
                            }
                        }
            
                     } 

                     String[] UserString = OrganizeInsertedInfo(query);

                     for (int k = 0; k < columns.length; k++) {
                        for (int n = 0; n < columns.length; n ++) {
                            if (scrambledColumns[n].equalsIgnoreCase(columns[k])) {

                                emptyArray[k] = UserString[n]; 
                               
                            }
                        }
                     }
                     
                     String result = "";
        
                    
                    for (int counter = 0; counter < emptyArray.length; counter++) {
                       
                        result += emptyArray[counter]; 
                    
                          if (counter < emptyArray.length - 1) {
                    
                             result += ", "; 
                    
                            } 
                   }
                     

                FileWriter writer = new FileWriter("src/myTables/" + nameofTable + ".csv", true);
                writer.write("\n");
                writer.write(result);
                writer.close();
                    
                }catch(IOException e){
                    e.printStackTrace();

                }
}
           
        }
 
    }

    //insert two

    public static String[] OrganizeInsertedInfo(ArrayList<String> query){

        for(int i = 0; i<query.size();i++){

            if(query.get(i).equalsIgnoreCase("value")){ 

                List<String> subList = query.subList(i+1,query.size());
                String[] ListToArray = subList.toArray(new String[0]);

                return ListToArray;

            }

         }

         return new String[0];

     }

    //drop tables

    public static void dropTable(ArrayList<String> query){


            String nameofTable = query.get(2);
            String path = "src/myTables/";
            File directory = new File(path);
            File[] fileDirectory = directory.listFiles();
            File deleteFile = new File(path + nameofTable + ".csv");

            for (int i = 0; i < fileDirectory.length; i++) {

                if(fileDirectory[i].getName().equalsIgnoreCase(nameofTable + ".csv")){ 

                    
                    deleteFile.delete();


                } 


            }

        


    }

    //delete info command

    public static void deleteInfo(ArrayList<String> query) {

        String nameofTable = query.get(2);
        String UserDelete = query.get(3);
        String path = "src/myTables/";
        File directory = new File(path);
        File[] fileDirectory = directory.listFiles();
        File sourceFile = new File(path + nameofTable + ".csv");
        int indexOfArray = 0;
    
        for (int i = 0; i < fileDirectory.length; i++) {
    
            if (fileDirectory[i].getName().equalsIgnoreCase(nameofTable + ".csv")) {
    
                try {
    
                    Scanner scanner = new Scanner(sourceFile);
                    String headline = scanner.nextLine();
                    System.out.println(headline);
                    String[] Columns = headline.split(",");
                    int AmountofColumns = Columns.length;
                    List<String>[] ColumnArray = new List[AmountofColumns];
    
                    for (int j = 0; j < AmountofColumns; j++) {
                        ColumnArray[j] = new ArrayList<>();
                    }
    
                    while (scanner.hasNextLine()) {
    
                        String lines = scanner.nextLine();
                        System.out.println(lines);
                        String[] rowData = lines.split(",");
    
                        if (rowData.length == AmountofColumns) {
    
                            for (int x = 0; x < AmountofColumns; x++) {
                                ColumnArray[x].add(rowData[x].trim());
                            }
    
                        } else {
    
                            System.out.println("Skipping invalid line");
    
                        }
    
                    }
    
                    for (int m = 0; m < ColumnArray.length; m++) {
    
                        for (int l = 0; l < Columns.length; l++) {
    
                            if (query.get(4).equalsIgnoreCase(Columns[l])) {
    
                                indexOfArray = l;
    
                            }
    
                        }
    
                        System.out.println(ColumnArray[indexOfArray]);
    
                        for (int u = 0; u < ColumnArray[indexOfArray].size(); u++) {
    
                            if (ColumnArray[indexOfArray].get(u).equalsIgnoreCase(query.get(5))) {
    
                                System.out.println("Found item you want to delete");
    
                                for (int o = 0; o < ColumnArray.length; o++) {
    
                                    ColumnArray[o].remove(u);
                                    System.out.println(ColumnArray[o]);
    
                                }
                                break;
                            }
                        }
                    }
    
                    scanner.close();
    
                  
                    StringBuilder updatedContent = new StringBuilder();
    
                  
                    for (int j = 0; j < Columns.length; j++) {
                        updatedContent.append(Columns[j]);
                        if (j < Columns.length - 1) {
                            updatedContent.append(",");
                        }
                    }
                    updatedContent.append("\n");
    
                    
                    int numberOfRows = ColumnArray[0].size();
                    for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
                        for (int colIndex = 0; colIndex < ColumnArray.length; colIndex++) {
                            updatedContent.append(ColumnArray[colIndex].get(rowIndex));
                            if (colIndex < ColumnArray.length - 1) {
                                updatedContent.append(",");
                            }
                        }
                        updatedContent.append("\n");
                    }
                    
               
                    try (FileWriter writer = new FileWriter(sourceFile)) {
                        writer.write(updatedContent.toString());
                        System.out.println("File updated successfully.");
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                    }
    
                } catch (Exception e) {
                    System.err.println("Error processing file: " + e.getMessage());
                }
    
            }
        }
    }
        
           
       
    
    }