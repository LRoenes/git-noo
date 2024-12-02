package model;

import java.util.ArrayList;

public class Tokenizer {
    
   public static ArrayList<String> tokenizeUserInput(String command){

    ArrayList<String> tokens = new ArrayList<>();   
    String regex = "\\s+|,|\\(|\\)";

    for(String token: command.split(regex)){
        
        if (!token.isEmpty()) { // Verifica si el token no está vacío
            tokens.add(token);
        }


    }

    return tokens; 

   } 


}
