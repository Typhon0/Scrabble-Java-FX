package com.scrabble.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by alexr on 06/10/2017.
 */
public class Dictionnaire {

    //region Variables

    ArrayList<String> dico;

    //endregion

    //region Constructor
    public Dictionnaire() {
        try {
            dico = new ArrayList<>();
            Scanner sc = new Scanner(new File("src/com/scrabble/ressources/dico_Scrabble.txt"));
            while (sc.hasNextLine()) {
                dico.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Functions
    public boolean motExistant(String mot) {
        return dico.contains(mot);
    }

    public boolean motExistant(String prefixe, String suffixe, int index, int length) {
        for (String mot : this.dico) {
            if (mot.length() == length) {
                if (mot.substring(0, index).equalsIgnoreCase(prefixe) && mot.substring(index + 1, mot.length()).equalsIgnoreCase(suffixe)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public ArrayList<String> motsPossibles(ArrayList<Integer> index, ArrayList<Character> lettre)
    {
    	ArrayList<String> mots = new ArrayList<String>();
    	
    	for(String mot : dico)
    	{
    		boolean correct = true;

    			for(int i=0; i<index.size(); i++)
    			{
    				if(i>=mot.length() || mot.charAt(index.get(i))!=lettre.get(i))
    				{
    					correct = false;
    				}
    			}
    			if(correct && !(mots.contains(mot)))
    			{
    				mots.add(mot);
    			}
    	}


    	return mots;
    }

    //endregion

    //region Getters Setters
    public ArrayList<String> getDico() {
        return this.dico;
    }

    //endregion

}
