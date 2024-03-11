package org.emploidutemps.code;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
public class Parser {
    public static JSONArray Parse(String path) throws IOException {
        JSONArray cours = new JSONArray();
        JSONObject cour = new JSONObject();

        File file = new File(path);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if(line.contains("END:VEVENT")) {
                cours.add(cour);
                cour = new JSONObject();
            }
            else if(line.contains("UID:")) {
                cour.put("UID", line.substring(4, line.length()));
            }
            else if(line.contains("DTSTART")) {
                if(line.contains("VALUE=")) cour.put("DTSTART", line.substring(19, line.length()));
                else cour.put("DTSTART", line.substring(8, line.length()));
            }
            else if(line.contains("DTEND")) {
                if(line.contains("VALUE=")) cour.put("DTEND", line.substring(17, line.length()));
                else cour.put("DTEND", line.substring(6, line.length()));
            }
            else if(line.contains("DESCRIPTION")) {
                while(!line.contains("Type")) {
                    line = line + reader.nextLine();
                }
                line = line.substring(24, line.length());
                String[] lines = line.split(Pattern.quote("\\n"));
                for(int i = 0; i < lines.length; i++) {
                    String[] tmp = lines[i].split(" : ");
                    if(tmp.length < 2) {
                        line = line+reader.nextLine();
                        lines = line.split(Pattern.quote("\\n"));
                        for(int y = 0; i < lines.length; i++) {
                            tmp = lines[i].split(" : ");
                            if(tmp[0].equals("Matière")) cour.put("Matiere", tmp[1]);
                            else if(tmp[0].contains("Enseignant")) cour.put("Enseignant", tmp[1]);
                            else if(tmp[0].contains("Promotion")) cour.put("Promotion", tmp[1]);
                            else if(tmp[0].contains("Salle")) cour.put("Salle", tmp[1]);
                            else if(tmp[0].contains("Type")) cour.put("Type", tmp[1]);
                            else if(tmp[0].contains("TD")) cour.put("TD", tmp[1]);
                            else if(tmp[0].contains("M émo")) cour.put("Memo", tmp[1]);
                        }
                    } else {
                        if(tmp[0].equals("Matière")) cour.put("Matiere", tmp[1]);
                        else if(tmp[0].contains("Enseignant")) cour.put("Enseignant", tmp[1]);
                        else if(tmp[0].contains("Promotion")) cour.put("Promotion", tmp[1]);
                        else if(tmp[0].contains("Salle")) cour.put("Salle", tmp[1]);
                        else if(tmp[0].contains("Type")) cour.put("Type", tmp[1]);
                        else if(tmp[0].contains("TD")) cour.put("TD", tmp[1]);
                        else if(tmp[0].contains("M émo")) cour.put("Memo", tmp[1]);
                    }
                }
            }
        }
        reader.close();
        return cours;
    }

    public static void main(String[] args) throws IOException {
        JSONArray test = Parse("C:\\Users\\Seren\\Desktop\\Cours_Univ\\Prototypage_interface\\emploi-du-temps\\EDT\\Enseignant\\EDT-Cecillon-Noe.txt");
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }
    }
}
