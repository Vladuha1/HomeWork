package model.family.human;

//TODO
// Exceptions

import model.family.Alivable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Human implements Serializable, Comparable<Human>, Alivable<Human> {
    private int id;
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Gender gender;
    private Human mother, father;
    private Set<Human> children;

    public Human(int id, String name, LocalDate birthDate, Gender gender){
        this.id = id;
        children = new HashSet<>();
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public HashSet<Human> getParents(){
        HashSet<Human> set = new HashSet<>();
        if (mother != null)
            set.add( mother);
        if (father != null)
            set.add( father);
        return set;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        LocalDate endDate;
        if (deathDate == null){
            endDate = LocalDate.now();
        } else endDate = deathDate;
        Period period = Period.between(birthDate, endDate);
        return period.getYears();
    }

    public Human getMother(){
        if (mother != null)
            return (Human) mother;
        return null;
    }

    public Human getFather(){
        if (father != null)
            return father;
        return null;
    }

    public Human getChildByName(String name){
        for (Human child : children){
            if(child.getName().equals(name)){
                return (Human) child;
            }
        }
        return null;
    }

    public boolean setParent(Human parent){
        if (parent.getAge() > this.getAge()) {
            parent.addChild(this);
            if (parent.gender == Gender.Female) {
                this.mother = parent;
                return true;
            } else {
                this.father = parent;
                return true;
            }
        }
        return false;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public boolean addChild(Human child){
        return children.add(child);
    }

    public Gender getGender() {
        return gender;
    }

    public Set<Human> getChildren(){
        return children;
    }

    @Override
    public String toString() {
        return "#" + id + ": " + name + " " + getAge() + " y.o. " + gender.toString();

    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals(this.getClass().getName()))
            return false;

        Human human = (Human) obj;
        if (this.hashCode() != human.hashCode())
            return false;
        return this.id == human.id && this.name.equals(human.getName()) && this.birthDate.isEqual(human.birthDate)
                && this.children.equals(human.children);
//        return this.id == human.id;
    }

    private int size(){
        if (children == null){
            return 0;
        } else return children.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, mother, father, this.size());
    }

    @Override
    public int compareTo(Human o) {
        return this.id - o.getId();
    }

}