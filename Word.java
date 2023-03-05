package com.company;

class Word implements Comparable
{
    private String word, word_en, type;
    private String[] singular = new String[3];
    private String[] plural = new String[3];
    private Definition[] definitions = new Definition[100];

    String getWord() { return this.word; }
    void setWord(String word) { this.word = word; }

    String getWord_en() { return this.word_en; }
    void setWord_en(String word_en) { this.word_en = word_en; }

    String getType() { return this.type; }
    void setType(String type) { this.type = type; }

    String[] getSingular() { return this.singular;}
    void setSingular(String[] singular) { this.singular = singular; }

    String[] getPlural() { return this.plural; }
    void setPlural(String[] plural) {this.plural = plural; }

    Definition[] getDefinitions() { return this.definitions;}
    void setDefinitions(Definition[] definitions) { this.definitions = definitions; }

    void addDefinition(Definition definition)
    {
        Definition[] definitions1 = new Definition[this.definitions.length + 1];
        System.arraycopy(this.definitions,0,definitions1,0,this.definitions.length);
        definitions1[this.definitions.length] = new Definition();
        definitions1[this.definitions.length] = definition;
        this.definitions = definitions1;
    }
    void deleteDefinitionAtPosition(int i)
    {
        Definition[] definitions1 = new Definition[this.definitions.length - 1];
        System.arraycopy(this.definitions,0,definitions1,0, i);
        System.arraycopy(this.definitions,i+1,definitions1,i,this.definitions.length - 1 - i);
        this.definitions = definitions1;
    }

    public int compareTo(Object o) // we will sort an array of words using this
    {
        Word word = (Word)o;
        int l1 = this.word.length();
        int l2 = word.getWord().length();

        int i;
        for(i = 0 ; i < l1 && i < l2; ++i)
        {
            if (this.word.charAt(i) > word.getWord().charAt(i))
                return 1;
            if (this.word.charAt(i) < word.getWord().charAt(i))
                return -1;
        }
        if(i < l1)
            return 1;
        if(i < l2)
            return -1;
        return 0;
    }
}
