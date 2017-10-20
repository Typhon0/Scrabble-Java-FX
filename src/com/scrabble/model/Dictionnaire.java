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
            Scanner sc = new Scanner(new File("com/scrabble/ressources/dico_Scrabble.txt"));
            while (sc.hasNextLine()){
                dico.add(sc.nextLine());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean motExistant(String mot){
        return dico.contains(mot);
    }

}
