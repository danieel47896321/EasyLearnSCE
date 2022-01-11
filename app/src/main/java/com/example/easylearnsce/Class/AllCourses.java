package com.example.easylearnsce.Class;

public class AllCourses {
    private Course structural_Engineering[];
    private Course mechanical_Engineering[];
    private Course electrical_Engineering[];
    private Course software_Engineering[];
    private Course industrial_Engineering[];
    private Course chemical_Engineering[];
    private Course programing_computer[];
    private Course pre_Engineering[];
    public AllCourses() {
        structural_Engineering = new Course[1];
        mechanical_Engineering = new Course[1];
        electrical_Engineering = new Course[1];
        software_Engineering = new Course[10];
        industrial_Engineering = new Course[1];
        chemical_Engineering = new Course[1];
        programing_computer = new Course[1];
        pre_Engineering = new Course[1];
        setStructural_Engineering();
        setMechanical_Engineering();
        setElectrical_Engineering();
        setSoftware_Engineering();
        setIndustrial_Engineering();
        setChemical_Engineering();
        setPre_Engineering();
    }
    private void setPre_Engineering() {
        pre_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");
    }

    private void setChemical_Engineering() {
        chemical_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");
    }

    private void setIndustrial_Engineering() {
        industrial_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");
    }

    private void setSoftware_Engineering() {
        software_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");
        software_Engineering[1] = new Course("אלגוריתמים 2","עבד אלכרים אבו עפאש","רביעית","א'","הנדסת תוכנה");
        software_Engineering[2] = new Course("נושאים מתקדמים בקריפטוגרפיה","אלונה קוציי","רביעית","א'","הנדסת תוכנה");
        software_Engineering[3] = new Course("פרויקט גמר שלב א'","הדס חסידים","רביעית","א'","הנדסת תוכנה");
        software_Engineering[4] = new Course("בסיסי נתונים","נטליה וונטיק","שלישית","א'","הנדסת תוכנה");
        software_Engineering[5] = new Course("מבוא לקומפיליה","אלכסנדר צ'ורקין","שלישית","א'","הנדסת תוכנה");
        software_Engineering[6] = new Course("מבוא לתקשורת מחשבים","אלונה קוציי","שלישית","א'","הנדסת תוכנה");
        software_Engineering[7] = new Course("אבטחת תוכנה","מיכאל קיפרברג","שלישית","ב'","הנדסת תוכנה");
        software_Engineering[8] = new Course("מערכות הפעלה","אירינה רבייב","שלישית","ב'","הנדסת תוכנה");
        software_Engineering[9] = new Course("רשתות תקשורת מחשבים","אלונה קוציי","שלישית","ב'","הנדסת תוכנה");

    }

    private void setElectrical_Engineering() {
        electrical_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }

    private void setMechanical_Engineering() {
        mechanical_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }

    private void setStructural_Engineering() {
        structural_Engineering[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }
    private void setPrograming_computer() {
        programing_computer[0] = new Course("בטיחות תוכנה","מיכאל קיפרברג","רביעית","א'","הנדסת תוכנה");

    }

    public Course[] getStructural_Engineering() { return structural_Engineering; }
    public Course[] getMechanical_Engineering() { return mechanical_Engineering; }
    public Course[] getElectrical_Engineering() { return electrical_Engineering; }
    public Course[] getSoftware_Engineering() { return software_Engineering; }
    public Course[] getIndustrial_Engineering() { return industrial_Engineering; }
    public Course[] getChemical_Engineering() { return chemical_Engineering; }
    public Course[] getPrograming_computer() { return programing_computer; }
    public Course[] getPre_Engineering() { return pre_Engineering; }
}
