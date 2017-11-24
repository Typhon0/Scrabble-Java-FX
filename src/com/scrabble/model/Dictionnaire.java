package com.scrabble.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by alexr on 06/10/2017.
 */
public class Dictionnaire {
    ArrayList<String> dico;
    public Dictionnaire(){
        try {
            dico = new ArrayList<>();
            Scanner sc = new Scanner(new File("src/com/scrabble/ressources/dico_Scrabble.txt"));
            while (sc.hasNextLine()){
                dico.add(sc.nextLine());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> getDico(){
    	return this.dico;
    }

    public boolean motExistant(String mot){
        return dico.contains(mot);
    }
    
    public boolean motExistant(String prefixe, String suffixe, int index, int length)
    {
    	for(String mot : this.dico)
    	{
    		if(mot.length() == length)
    		{
        		System.out.println(mot.substring(0, index));
        		System.out.println(mot.substring(index+1, mot.length()));
        		if(mot.substring(0, index).equalsIgnoreCase(prefixe) && mot.substring(index+1, mot.length()).equalsIgnoreCase(suffixe))
        		{
        			return true;
        		}
    		}
    	}
    	
    	return false;
    }

}
