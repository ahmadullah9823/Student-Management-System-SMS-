package universitycgpa.model;
public abstract class Person {
    private String name;
    private String id;
    private static int totalPersons = 0;  
    public Person() {
        this("Unknown", "0000000");
    }

    public Person(String name, String id) {
        this.name = name;   
        this.id = id;
        totalPersons++;    
    }
    public String getName(){ 
        return name; 
    }
    public void setName(String name){ 
        this.name = name;
    }
    public String getId(){
        return id; 
    }
    public void setId(String id){ 
        this.id = id;
    }
    public static int getTotalPersons(){
        return totalPersons; 
    }
    public abstract void showInfo();
}
