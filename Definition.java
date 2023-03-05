package com.company;

class Definition implements Comparable
{
    private String dict, dictType;
    private int year;
    private String[] text = new String[100];

    String getDict() { return this.dict; }
    void setDict(String dict) { this.dict = dict; }

    String getDictType() { return this.dictType; }
    void setDictType(String dictType) { this.dictType = dictType; }

    int getYear() { return this.year; }
    void setYear(int year) { this.year = year; }

    String[] getText() { return this.text; }
    void setText(String[] text) { this.text = text; }

    public int compareTo(Object o) // we will sort an array of definitions using this
    {
        Definition definition = (Definition)o;
        return this.year - definition.year;
    }
}
