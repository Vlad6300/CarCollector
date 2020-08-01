import javax.naming.ldap.LdapName;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    int id;
    String fName;
    String lName;
    String postCode;
    ArrayList<Car> carList;

    User(ResultSet rs, ArrayList<Car> cars){
        carList = cars;
        try{
        id = rs.getInt(1);
        fName=rs.getString(2);
        lName=rs.getString(3);
        postCode=rs.getString(4);
        }catch (SQLException e){
            System.out.println("ERROR IN CREATING USER OBJECT!!");
        }
    }

    void show(){
        System.out.println("        \"ID\": \""+id+"\"");
        System.out.println("        \"First Name\": \""+fName+"\"");
        System.out.println("        \"Last Name\": \""+ lName +"\"");
        System.out.println("        \"Post Code\": \""+postCode+"\"");
        System.out.println("        \"Cars\": [");
        for (Car c : carList){
            System.out.print("\t");
            c.show();}
        System.out.println("]");
    }

    @Override
    public String toString() {
        String out = new String();
        out+=("        \"ID\": \""+id+"\"\n");
        out+=("        \"First Name\": \""+fName+"\"\n");
        out+=("        \"Last Name\": \""+ lName +"\"\n");
        out+=("        \"Post Code\": \""+postCode+"\"\n");
        out+=("        \"Cars\": [\n");
        for (Car c : carList){
            out+=("\t\t");
            out+=c.toString();
        }
        out+=("        ]\n");
        return out;
    }
}
