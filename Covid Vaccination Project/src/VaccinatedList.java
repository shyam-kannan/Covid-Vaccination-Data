
public class VaccinatedList {
    private int id = 0;
    private String lastName = "";
    private String firstName = "";
    private String vaccine = "";
    private String date = "";
    private String location = "";

    public VaccinatedList(int id, String lastName, String firstName, String vaccine, String date, String location){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.vaccine = vaccine;
        this.date = date;
        this.location = location;
    }

    public String getLastName(){
        return lastName;
    }

    public int getId(){
        return id;
    }
    
    public String getFirstName(){
        return firstName;
    }

    public String getVaccine(){
        return vaccine;
    }

    public String getDate(){
        return date;
    }

    public String getLocation(){
        return location;
    }
}