package com.example.easylearnsce.Class;

import java.util.ArrayList;

public class AllCourses {
    private ArrayList<Course> structural_Engineering;
    private ArrayList<Course> mechanical_Engineering;
    private ArrayList<Course> electrical_Engineering;
    private ArrayList<Course> software_Engineering;
    private ArrayList<Course> industrial_Engineering;
    private ArrayList<Course> chemical_Engineering;
    private ArrayList<Course> programing_computer;
    private ArrayList<Course> pre_Engineering;
    public AllCourses() {
        structural_Engineering = new ArrayList<>();
        mechanical_Engineering = new ArrayList<>();
        electrical_Engineering = new ArrayList<>();
        software_Engineering = new ArrayList<>();
        industrial_Engineering = new ArrayList<>();
        chemical_Engineering = new ArrayList<>();
        programing_computer = new ArrayList<>();
        pre_Engineering = new ArrayList<>();
        setStructural_Engineering();
        setMechanical_Engineering();
        setElectrical_Engineering();
        setSoftware_Engineering();
        setIndustrial_Engineering();
        setChemical_Engineering();
        setPre_Engineering();
    }
    private void setPre_Engineering() {
        pre_Engineering.add(new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה"));
    }

    private void setChemical_Engineering() {
        chemical_Engineering.add(new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה"));
    }

    private void setIndustrial_Engineering() {
        industrial_Engineering.add(new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה"));
    }

    private void setSoftware_Engineering() {
        software_Engineering.add(new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("אלגוריתמים 2","עבד אלכרים אבו עפאש","רביעית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("נושאים מתקדמים בקריפטוגרפיה","אלונה קוציי","רביעית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("פרויקט גמר שלב א'","הדס חסידים","רביעית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("בסיסי נתונים","נטליה וונטיק","שלישית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("מבוא לקומפיליה","אלכסנדר צ'ורקין","שלישית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("מבוא לתקשורת מחשבים","אלונה קוציי","שלישית","א'","הנדסת תוכנה"));
        software_Engineering.add(new Course("אבטחת תוכנה","מיכאל קיפרברג","שלישית","ב'","הנדסת תוכנה"));
        software_Engineering.add(new Course("מערכות הפעלה","אירינה רבייב","שלישית","ב'","הנדסת תוכנה"));
        software_Engineering.add(new Course("רשתות תקשורת מחשבים","אלונה קוציי","שלישית","ב'","הנדסת תוכנה"));

    }

    private void setElectrical_Engineering() {
        //electrical_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }

    private void setMechanical_Engineering() {
      //  mechanical_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }

    private void setStructural_Engineering() {
       // structural_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }
    private void setPrograming_computer() {
       // programing_computer[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }

    public ArrayList<Course> getStructural_Engineering() { return structural_Engineering; }
    public ArrayList<Course> getMechanical_Engineering() { return mechanical_Engineering; }
    public ArrayList<Course> getElectrical_Engineering() { return electrical_Engineering; }
    public ArrayList<Course> getSoftware_Engineering() { return software_Engineering; }
    public ArrayList<Course> getIndustrial_Engineering() { return industrial_Engineering; }
    public ArrayList<Course> getChemical_Engineering() { return chemical_Engineering; }
    public ArrayList<Course> getPrograming_computer() { return programing_computer; }
    public ArrayList<Course> getPre_Engineering() { return pre_Engineering; }
}
