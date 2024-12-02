package conditions;

import java.util.ArrayList;

import helpers.GeneralHelpers;

public class Cases {
    
    public static void checkAllCases(ArrayList<String> splittedQuery){


        if(splittedQuery.get(0).equalsIgnoreCase("list")){

            GeneralHelpers.listAbles();

        }else if(splittedQuery.get(0).equalsIgnoreCase("create")){

            GeneralHelpers.createTable(splittedQuery);

        }else if(splittedQuery.get(0).equalsIgnoreCase("clear")){

            GeneralHelpers.clearConsole();

        }




    }




}
