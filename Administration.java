package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class Administration {

    private Dictionary[] dictionaries = new Dictionary[100];
    private int numberOfDictionaries;

    void setDictionaries(Dictionary[] dictionaries) {
        this.dictionaries = dictionaries;
    }

    void setNumberOfDictionaries(int numberOfDictionaries) {
        this.numberOfDictionaries = numberOfDictionaries;
    }

    boolean addWord(Word word, String language)
    {
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary we're looking for
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // the word already exists
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word.getWord()))
                            return false;

                }
                this.dictionaries[i].addWord(word); // we add a new word
                return true;
            }
        }
        return false; // logically we never get here, unless there is no dictionary for the language provided
    }

    boolean removeWord(String word, String language)
    {
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary we're looking for
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // the word exists
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word))
                        {
                            this.dictionaries[i].deleteWordAtPosition(j);
                            return true; // we deleted the word
                        }
                }
                return false; // we get here if we didn't find the word
            }
        }
        return false; // logically we never get here, unless there is no dictionary for the language provided
    }

    boolean addDefinitionForWord(String word, String language, Definition definition)
    {
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary we're looking for
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // the word exists
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word))
                            for( int k = 0; k < this.dictionaries[i].getWords()[j].getDefinitions().length ; ++k)
                            {
                                Definition definition1 = this.dictionaries[i].getWords()[j].getDefinitions()[k];
                                if(definition1.getDict().equals(definition.getDict()))
                                    return false; // the definition already exists
                            }
                            this.dictionaries[i].getWords()[j].addDefinition(definition);
                            return true; // we added the definition
                }
                return false; // logically we never get here, unless the word given does not exist
            }
        }
        return false; // logically we never get here, unless there is no dictionary for the language provided
    }

    boolean removeDefinition(String word, String language, String dictionary)
    {
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary we're looking for
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // the word exists
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word))
                            for( int k = 0; k < this.dictionaries[i].getWords()[j].getDefinitions().length ; ++k)
                            {
                                Definition definition1 = this.dictionaries[i].getWords()[j].getDefinitions()[k];
                                if(definition1.getDict().equals(dictionary))
                                {
                                    this.dictionaries[i].getWords()[j].deleteDefinitionAtPosition(k);
                                    return true;
                                }
                            }
                    return false; // we get here if there is no definition to remove
                }
                return false; // logically we never get here, unless the word given does not exist
            }
        }
        return false; // logically we never get here, unless there is no dictionary for the language provided
    }

    String translateWord(String word, String fromLanguage, String toLanguage)
    {
        String word_en = "";
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(fromLanguage)) // we've found the source dictionary
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // we found the word
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word))
                        {
                            word_en = this.dictionaries[i].getWords()[j].getWord_en();
                            break;
                        }

                }
            }
            if(word_en.length() > 0) // we can now look for the second dictionary
                break;
        }

        if(toLanguage.equals("en"))
            return word_en;

        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(toLanguage)) // we've found the second dictionary
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // we found the word
                        if (this.dictionaries[i].getWords()[j].getWord_en().equals(word_en))
                            return this.dictionaries[i].getWords()[j].getWord();
                }
            }
        }
        return word; // logically we never get here, unless we can't find a translation
    }

    String translateSentence(String sentence, String fromLanguage, String toLanguage)
    {
        String[] words = sentence.split(" "); // we split the sentence into words
        StringBuilder s = new StringBuilder();

        for(String word:words)
        {
            String translatedWord = translateWord(word, fromLanguage, toLanguage); // use the previous method
            if(translatedWord == null) // we keep the word untranslated if we can't find a translation
                translatedWord = word;
            s.append(translatedWord);
            s.append(" ");
        }
        s.delete(s.length()-1, s.length()); // we delete the last character since it's a space
        return s.toString();
    }

    String[] getSynonyms(String word, String language)
    {
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                {
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // we found the word
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word))
                        {
                            Word word1 = this.dictionaries[i].getWords()[j];
                            for(int k = 0; k < word1.getDefinitions().length ;++k)
                                if(word1.getDefinitions()[k].getDictType().equals("synonyms"))
                                    return word1.getDefinitions()[k].getText();

                            return null; // we get here if we don't have a synonym dictionary
                        }
                }
                return null; // we get here if the word doesn't exist in the dictionary
            }
        }
        return null; // we get here if the dictionary doesn't exist
    }

    ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage)
    {
        String[] words = sentence.split(" "); // we split the sentence into words ,[]
        StringBuilder[] s = new StringBuilder[3];
        s[0] = new StringBuilder();
        s[1] = new StringBuilder();
        s[2] = new StringBuilder();

        int ways = 1;

        for(String word:words)
        {
            String translatedWord = translateWord(word, fromLanguage, toLanguage);
            if(ways < 3) // we still need to find synonyms to generate more translations
            {
                String[] synonyms = getSynonyms(translatedWord, toLanguage);
                if(synonyms == null) // we don't have a synonym
                {
                    s[0].append(translatedWord).append(" ");
                    s[1].append(translatedWord).append(" ");
                    s[2].append(translatedWord).append(" ");
                }
                else if(synonyms.length == 1) // we have one synonym
                {
                    ways = ways * 2;
                    s[0].append(translatedWord).append(" ");
                    s[1].append(synonyms[0]).append(" ");
                    if(ways == 4)
                        s[2].append(translatedWord).append(" ");
                    else
                        s[2].append(synonyms[0]).append(" ");
                }
                else // we have 2 or more synonyms, which automatically gets us 3 options
                {
                    ways = ways * 3;
                    s[0].append(translatedWord).append(" ");
                    s[1].append(synonyms[0]).append(" ");
                    s[2].append(synonyms[1]).append(" ");
                }
            }
          else
            {
                s[0].append(translatedWord).append(" ");
                s[1].append(translatedWord).append(" ");
                s[2].append(translatedWord).append(" ");
            }
        }

        s[0].delete(s[0].length()-1, s[0].length());
        s[1].delete(s[1].length()-1, s[1].length()); // deleting the last space at the end
        s[2].delete(s[2].length()-1, s[2].length());


        ArrayList<String> sentences = new ArrayList<>();
        if(ways >= 1)
            sentences.add(s[0].toString());
        if(ways >= 2)
            sentences.add(s[1].toString());
        if(ways >= 3)
            sentences.add(s[2].toString());

        return sentences;
    }

    ArrayList<Definition> getDefinitionsForWord(String word, String language)
    {
        ArrayList<Definition> definitions = new ArrayList<>();

        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary
            {
                for (int j = 0; j < this.dictionaries[i].getWords().length; ++j)
                    if (this.dictionaries[i].getWords()[j] != null) // we haven't gone through all the words
                        // we found the word
                        if (this.dictionaries[i].getWords()[j].getWord().equals(word))
                        {
                            Word word1 = this.dictionaries[i].getWords()[j];
                            Definition[] definitions1 = word1.getDefinitions();

                            Collections.addAll(definitions, definitions1);

                            Collections.sort(definitions);
                            return definitions;
                        }
                return null; // we get here if we don't find the word
            }
        }
        return null; // we get here if we don't find the dictionary
    }

    void exportDictionary(String language)
    {
        for (int i = 0; i < this.numberOfDictionaries; ++i)
        {
            if (dictionaries[i].getLanguage().equals(language)) // we've found the dictionary
            {
                Word[] words = dictionaries[i].getWords();
                ArrayList<Word> words1 = new ArrayList<>();

                for (int j = 0 ; j < words.length; ++j ) // for each word we will sort its definitions
                {
                    Definition[] definitions = words[j].getDefinitions();
                    ArrayList<Definition> definitions1 = new ArrayList<>();
                    Collections.addAll(definitions1, definitions);
                    Collections.sort(definitions1);

                    for (int k = 0; k < definitions.length ; ++k) // put the sorted definitions back
                        definitions[k] = definitions1.get(k);
                }

                Collections.addAll(words1, words);

                Collections.sort(words1); // sorting the words alphabetically

                Word[] words2 = new Word[words.length];
                for(int j = 0 ; j < words.length ; ++j)
                    words2[j] = words1.get(j);

                Dictionary dictionary = new Dictionary();
                dictionary.setWords(words2); // put sorted words into new dictionary
                dictionary.setLanguage(language);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                String exported_dictionary = gson.toJson(dictionary); // transform dictionary into Json text

                String fileName = language + "_exported_dict.json";
                new File(fileName); // open the file

                try
                {
                    FileWriter myWriter = new FileWriter(fileName);
                    myWriter.write(exported_dictionary); // write the Json to file
                    myWriter.close();
                }
                catch (IOException ex) { System.out.println("Couldn't write to file " + fileName); }
            }
        }
    }
}
