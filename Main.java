package com.company;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main
{
    public static void main(String[] args)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // creating an instance of a Gson object

        File directory = new File("input"); // opening the input directory

        File[] listOfFiles = directory.listFiles(); // getting the list of files

        Dictionary[] dictionaries = new Dictionary[100];
        int numberOfDictionaries = 0;

        if(listOfFiles != null)
        for (File currentFile:listOfFiles) // iterating through every file in the list
        {
            String language = currentFile.getName().substring(0,2); // extracting the dictionary language

            dictionaries[numberOfDictionaries++] = new Dictionary();

            StringBuilder s = new StringBuilder();

            try(BufferedReader br = new BufferedReader(new FileReader(currentFile)))
            {
                String line;
                s.append("{\n\"words\": "); // creating a string with the data from the dictionary in
                                            // json format
                while ((line = br.readLine()) != null)
                {
                    s.append(line);
                    s.append('\n');
                }
            }

            catch(FileNotFoundException ex2) { System.out.println("File Not Found"); } // handling exceptions
            catch(IOException ex1) { System.out.println("IO Exception"); }

            s.append("}"); // end of the dictionary object


            // saving the information in the newly created dictionary in the dictionaries array
            dictionaries[numberOfDictionaries - 1] = gson.fromJson(s.toString(),Dictionary.class);
            dictionaries[numberOfDictionaries - 1].setLanguage(language); // as well as its language
        }

        Administration administration = new Administration();
        administration.setDictionaries(dictionaries);
        administration.setNumberOfDictionaries(numberOfDictionaries);


        System.out.println("\n------------TASK2 TESTS------------\n");

        Word testWord_task1 = new Word(); // initializing a new word and adding it to the dictionary

        testWord_task1.setWord("papa");
        testWord_task1.setWord_en("dad");
        testWord_task1.setType("noun");
        String[] plural = {"papas"}, singular = {"papa"}, text = {"père, d'une manière plus familière",
        "comment les enfants appellent leurs pères"};
        testWord_task1.setPlural(plural);
        testWord_task1.setSingular(singular);
        Definition[] definitions = new Definition[1];
        definitions[0] = new Definition();
        definitions[0].setDict("Larousse");
        definitions[0].setDictType("definitions");
        definitions[0].setYear(2000);
        definitions[0].setText(text);
        testWord_task1.setDefinitions(definitions);

        System.out.println("number of words at the moment is " + dictionaries[0].getWords().length);
        boolean added = administration.addWord(testWord_task1,"fr");
        System.out.println(added);
        System.out.println("number of words increases to " + dictionaries[0].getWords().length);

        Word testWord2_task1 = new Word(); // initializing another word, but we already have it in the dictionary
        testWord2_task1.setWord("chat");
        testWord2_task1.setWord_en("cat");
        testWord2_task1.setType("noun");
        String[] plural2 = {"chats"}, singular2 = {"chat"}, text2 = {"a"};
        testWord2_task1.setPlural(plural2);
        testWord2_task1.setSingular(singular2);
        Definition[] definitions2 = new Definition[1];
        definitions2[0] = new Definition();
        definitions2[0].setDict("Larousse");
        definitions2[0].setDictType("synonyms");
        definitions2[0].setYear(2000);
        definitions2[0].setText(text2);
        testWord2_task1.setDefinitions(definitions2);

        added = administration.addWord(testWord2_task1,"fr");
        System.out.println(added);
        System.out.println("number of words stays at " + dictionaries[0].getWords().length);

        System.out.println("\n------------TASK3 TESTS------------\n");

        System.out.println("number of words at the moment is " + dictionaries[0].getWords().length);
        // deleting the word added at task 2
        boolean deleted = administration.removeWord("papa", "fr");
        System.out.println(deleted);
        System.out.println("number of words decreases to " + dictionaries[0].getWords().length);
        // trying to delete a word that doesn't exist
        deleted = administration.removeWord("court", "fr");
        System.out.println(deleted);
        System.out.println("number of words remains " + dictionaries[0].getWords().length);

        System.out.println("\n------------TASK4 TESTS------------\n");

        System.out.println("for the word chat in the first dictionary we have " +
                dictionaries[0].getWords()[0].getDefinitions().length + " definitions");

        Definition definition = new Definition();
        definition.setYear(2000);
        definition.setDict("Larousse2");
        definition.setDictType("synonyms");
        definition.setText(text);

        added = administration.addDefinitionForWord("chat","fr", definition);
        System.out.println(added);
        // we just added a definition from a new dictionary
        System.out.println("now we have " + dictionaries[0].getWords()[0].getDefinitions().length
         + " definitions");

        Definition definition2 = new Definition();
        definition2.setYear(2000);
        definition2.setDict("Larousse");
        definition2.setDictType("synonyms");
        definition2.setText(text);

        added = administration.addDefinitionForWord("chat","fr", definition2);
        System.out.println(added);
        // we've just tried to add a definition that already exists
        System.out.println("we still have " + dictionaries[0].getWords()[0].getDefinitions().length
                + " definitions");

        System.out.println("\n------------TASK5 TESTS------------\n");

        System.out.println("we have " + dictionaries[0].getWords()[0].getDefinitions().length
                + " definitions");
        deleted = administration.removeDefinition("chat","fr","Larousse2");
        System.out.println(deleted);
        // we've just deleted the definition added at task 4
        System.out.println("now we have " + dictionaries[0].getWords()[0].getDefinitions().length
                + " definitions");

        deleted = administration.removeDefinition("chat","fr", "Larousse3");
        System.out.println(deleted);
        // we've just tried to delete a nonexistent definition
        System.out.println("we still have " + dictionaries[0].getWords()[0].getDefinitions().length
                + " definitions");

        System.out.println("\n------------TASK6 TESTS------------\n");

        // we translate directly to english
        System.out.println(administration.translateWord("chat","fr","en"));
        // we translate to english first, and then to romanian
        System.out.println(administration.translateWord("chat","fr","ro"));

        System.out.println("\n------------TASK7 TESTS------------\n");

        System.out.println(administration.translateSentence("chat jeu manger", "fr", "en"));
        System.out.println(administration.translateSentence("pisică merge câine", "ro", "fr"));
        System.out.println(administration.translateSentence("pisică merge câine", "ro", "en"));

        System.out.println("\n------------TASK8 TESTS------------\n");

        System.out.println(administration.translateSentences("chat jeu","fr","en"));
        System.out.println(administration.translateSentences("chat chat","fr","ro"));
        System.out.println(administration.translateSentences("pisică","ro","fr"));

        System.out.println("\n------------TASK9 TESTS------------\n");

        System.out.println(administration.getDefinitionsForWord("câine","ro").get(0).getText()[0] + ", "
               + administration.getDefinitionsForWord("câine","ro").get(0).getText()[1]);
        System.out.println(administration.getDefinitionsForWord("câine","ro").get(1).getYear() + ", "
                + administration.getDefinitionsForWord("câine","ro").get(1).getText()[0]);

        // getting the definitions for dog, we can see the first texts are from the definition found in the dictionary
        // from 1929, which is actually third in the given file

        System.out.println("\n------------TASK10 TESTS------------\n");

        administration.exportDictionary("ro"); // this is the one that actually gets modified
        administration.exportDictionary("fr");

        System.out.println("check files in working folder");
    }
}

