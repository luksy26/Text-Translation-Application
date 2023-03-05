package com.company;

class Dictionary
{
    private Word[] words = new Word[100];
    private String language;

    String getLanguage() { return this.language; }
    void setLanguage(String language) { this.language = language;}

    Word[] getWords() { return this.words; }
    void setWords(Word[] words) { this.words = words; }
    void addWord(Word word)
    {
        Word[] words1 = new Word[this.words.length + 1];
        System.arraycopy(this.words,0,words1,0,this.words.length);
        words1[this.words.length] = new Word();
        words1[this.words.length] = word;
        this.words = words1;
    }
    void deleteWordAtPosition(int i)
    {
        Word[] words1 = new Word[this.words.length - 1];
        System.arraycopy(this.words,0,words1,0, i);
        System.arraycopy(this.words,i+1,words1,i,this.words.length - 1 - i);
        this.words = words1;
    }
}
