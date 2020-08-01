
import java.sql.*;
import java.util.ArrayList;

public class Car {
    int yearBuilt;
    int price;
    int id;
    String model;
    String manufacturer;
    String owner;
    String plateNr;

    Car(ResultSet res,String own,String manuf){
        try{
        id=res.getInt(1);
        model=res.getString(4);
        yearBuilt=res.getInt(5);
        price=res.getInt(6);
        plateNr=res.getString(7);
        owner=own;
        manufacturer=manuf;
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
    }

    Car(String mnf, String model, String plate, String own, int price, int year){
        manufacturer=mnf;
        this.model=model;
        plateNr=plate;
        owner = own;
        this.price = price;
        yearBuilt = year;

    }       //To be tested

    void show(){
        System.out.println("    {");
        System.out.println("        \"ID\": \""+id+"\"");
        System.out.println("        \"Manufacturer\": \""+manufacturer+"\"");
        System.out.println("        \"Model\": \""+model+"\"");
        System.out.println("        \"Owner\": \""+owner+"\"");
        System.out.println("        \"Year it was built\": \""+yearBuilt+"\"");
        System.out.println("        \"Price when bought\": \""+price+"\"");
        System.out.println("        \"Plate Number\": \""+plateNr+"\"");
        System.out.println("    }\n");
    }

    public String toString(){
        String out=new String();
        out+=("\"ID\": \""+id+"\"\n");
        out+=("\"Manufacturer\": \""+manufacturer+"\"\n");
        out+=("\"Model\": \""+model+"\"\n");
        out+=("\"Owner\": \""+owner+"\"\n");
        out+=("\"Year it was built\": \""+yearBuilt+"\"\n");
        out+=("\"Price when bought\": \""+price+"\"\n");
        out+=("\"Plate Number\": \""+plateNr+"\"\n");
        return out;
    }

}
