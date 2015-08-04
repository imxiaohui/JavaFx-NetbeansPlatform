package javafxprops;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Person Bean
 * @author ernesto
 */
public class Person {
    
    private StringProperty name = new SimpleStringProperty(this,"name","");
    private IntegerProperty age = new SimpleIntegerProperty(this, "age", 0);
    

    public StringProperty nameProperty() {
        return name;
    }

    public String getName(){
        return this.name.get();
    }
    
    public void setName(String name) {
        this.name.set(name); 
    }

    
    public IntegerProperty ageProperty() {
        return age;
    }

    public Integer getAge(){
        return this.age.get();
    }
    
    public void setAge(Integer age) {
        this.age.set(age);
    }
    
    
}
