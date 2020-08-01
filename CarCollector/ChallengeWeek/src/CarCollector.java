import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CarCollector {
    private Connection conn=null;

    String getManufacturer(int id){
        String mnf=null;
        try{
            ResultSet rs=null;
            Statement st;
            st = conn.createStatement();
            rs=st.executeQuery("SELECT name FROM manufacturer WHERE id="+id+";");
            rs.next();
            mnf = rs.getString(1);
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return mnf;
    }

    private String getOwner(int id){
        String owner=null;
        try {
            Statement st;
            st = conn.createStatement();
            ResultSet rs=null;
            rs = st.executeQuery("SELECT firstName,lastName FROM owner WHERE id=" + id + ";");
            rs.next();
            owner = rs.getString(1) +" "+ rs.getString(2);
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return owner;
    }

    private int createOwner(){                                                                                      //To be tested
        String fName, lName, post;
        String temp;
        Scanner in = new Scanner(System.in);
        System.out.println("Input first name: ");
        temp = in.nextLine();
        temp = temp.replaceAll("[^a-zA-Z ]", "");
        temp = temp.trim();
        fName = temp;
        System.out.println("Input last name: ");
        temp = in.nextLine();
        temp = temp.replaceAll("[^a-zA-Z ]", "");
        temp = temp.trim();
        lName = temp;
        System.out.println("Input post code: ");
        temp = in.nextLine();
        temp = temp.replaceAll("[^a-zA-Z0-9 ]", "");
        temp = temp.trim();
        post = temp;
        return insertOwner(fName, lName, post);
    }

    public int createOwnerGui(){
        String fName, lName, post;
        String temp;
        temp = JOptionPane.showInputDialog("Input your first name: ");
        temp = temp.replaceAll("[^a-zA-Z ]", "");
        fName = temp;

        temp = JOptionPane.showInputDialog("Input last name: ");
        temp = temp.replaceAll("[^a-zA-Z ]", "");
        temp = temp.trim();
        lName = temp;

        temp = JOptionPane.showInputDialog("Input post code: ");
        temp = temp.replaceAll("[^a-zA-Z0-9 ]", "");
        temp = temp.trim();
        post = temp;
        return insertOwner(fName, lName, post);
    }

    private int insertOwner(String fName, String lName, String post){
        try {
            if (fName.isEmpty() || lName.isEmpty() || post.isEmpty()) throw new Exception();
            Statement st = conn.createStatement();
            System.out.println();
            st.execute("INSERT INTO owner(firstName,lastName,postCode) VALUES (\"" + fName + "\",\"" + lName + "\",\"" + post + "\");");
            st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT id FROM owner WHERE firstName='" + fName + "' AND lastName='" + lName + "' AND postCode='" + post + "';");
            rs.next();
            return rs.getInt(1);        //returns id/
        }catch (Exception e){
            System.out.println("Invalid input! Action stopped");
        }
        return -4;
    }

    private boolean inputManufacturer(String manf){
        try {
            manf.toLowerCase();
            manf = manf.substring(0, 1).toUpperCase() + manf.substring(1);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM manufacturer WHERE name='"+manf+"';");
            if(!rs.next()){
                st=conn.createStatement();
                st.execute("INSERT INTO manufacturer(name) VALUES(\""+manf+"\");");
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private  int insertCar(Car t, int idOwner){
        try{
            ResultSet rs;
            Statement st = conn.createStatement();
            int idManuf;
            rs = st.executeQuery("SELECT * FROM manufacturer WHERE name='"+t.manufacturer+"'");
            rs.next();
            idManuf = rs.getInt(1);
            st.execute("INSERT INTO car(manufacturerID,ownerID,model,yearBuilt,price,plateNumber) VALUES("+idManuf+","+idOwner+",\""+t.model+"\","+t.yearBuilt+","+t.price+",\""+t.plateNr+"\");");
            st = conn.createStatement();
            rs = st.executeQuery("SELECT id FROM car WHERE plateNumber='"+t.plateNr+"'");
            rs.next();
            return rs.getInt(1);
        }catch (Exception e){
            System.out.println("SQL ERROR!!");
            return -1;
        }
    }


    private boolean connect(String database, String user, String password){   //Connect to the database. Returns whether the connection was successful.
        try
        {
            Class.forName("com.mysql.jdbc.Driver");//com.mysql.jdbc.Driver
            System.out.println("Driver loaded");
            conn = DriverManager.getConnection(database,user,password);
        }
        catch (ClassNotFoundException e){
            System.out.println("Driver not found");
            return false;
        }
        catch(SQLException e){
            System.out.println("Failed to connect to database");
            return false;
        }
        Statement trial;
        // try
        //{
        // trial = conn.createStatement();
        // trial.execute("USE ce29x_vd18143;");
        //}
        //catch (SQLException e){
        //   System.out.println("Failed to access database");
        //  return false;
        //}
        //try {
        //    trial.close();
        //}
        //catch (SQLException e){
        //    System.out.println("Couldn't close trial statement");
        // }
        return true;
    }

    CarCollector(){
        //"jdbc:mysql://cseemyweb.essex.ac.uk/ce29x_vd18143","vd18143","DZUGjUJIG5t3y"  or "jdbc:mysql://localhost/orderdb?useSSL=false","root","123456qwerty"
        this.connect("jdbc:mysql://localhost/car_collectors?useSSL=false","root","123456qwerty");
    }


    boolean disconnect(String database, String user, String password){  //Disconnects from the database. Returns whether the disconnection was successful
        try {
            Class.forName("com.mysql.jdbc.Driver");//om.mysql.jdbc.Driver
            System.out.println("Driver loaded");
            conn = DriverManager.getConnection(database,user,password);
            conn.close();
            return true;
            }catch (Exception e){
            System.out.println("Error in closing connection");
            return false;
        }
    } //DOESN'T WORK ?!?!?!?!?!?!?

    private void showCars(ResultSet rs){
        try {
            while (rs.next()) {
                String mnf = getManufacturer(rs.getInt(2));
                String owner = getOwner(rs.getInt(3));
                Car cr = new Car(rs, owner, mnf);
                cr.show();
            }
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
    }

    String showCarsString (ResultSet rs){
        String result= "";
        try {
            while (rs.next()) {
                String mnf = getManufacturer(rs.getInt(2));
                String owner = getOwner(rs.getInt(3));
                Car cr = new Car(rs, owner, mnf);
                result+=cr.toString();
                result+="\n";
            }
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
        return result;
    }

    ArrayList<Car> getCarArray(ResultSet rs){
        ArrayList<Car> carList = new ArrayList<>();
        try {
            while (rs.next()){
                carList.add(new Car(rs,getOwner(rs.getInt(3)),getManufacturer(rs.getInt(2))));
            }
        }catch (SQLException e){
            System.out.println("Error in forming array");
        }
        return carList;
    }

    void allCars(){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            showCars(st.executeQuery("select * from car;"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String allCarsString(){
        String result= new String();
        try{
            Statement st = conn.createStatement();
            result = showCarsString(st.executeQuery("select * from car;"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void year(int year){
        try{
            Statement st = conn.createStatement();
            System.out.println();
            showCars(st.executeQuery("select * from car WHERE yearBuilt="+Integer.toString(year)+";"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String yearString(int year){
        try{
            Statement st = conn.createStatement();
            System.out.println();
            String result = showCarsString(st.executeQuery("select * from car WHERE yearBuilt="+Integer.toString(year)+";"));
            st.close();
            return result;
        }catch (SQLException e){
            System.out.println("Error in statement");
            return "";
        }
    }

    void priceTo(int price){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            showCars(st.executeQuery("select * from car WHERE price<="+price+";"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    void priceFrom(int price){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            showCars(st.executeQuery("select * from car WHERE price>="+price+";"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String priceFromString(int price){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            result = showCarsString(st.executeQuery("select * from car WHERE price>="+price+";"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    String priceToString(int price){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            result=showCarsString(st.executeQuery("select * from car WHERE price<="+price+";"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void manufacturerId(int id){
        try{
            Statement st=conn.createStatement();
            System.out.println("[");
            System.out.println();
            showCars(st.executeQuery("select * from car where manufacturerID="+id+";"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String manufacturerIdString(int id){
        String result = new String();
        try{
            Statement st=conn.createStatement();
            result = showCarsString(st.executeQuery("select * from car where manufacturerID="+id+";"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void carId(int id){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            showCars(st.executeQuery("select * from car where id="+id+";"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String carIdString(int id){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            result = showCarsString(st.executeQuery("select * from car where id="+id+";"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void showUsers(ResultSet rs){
        try {
            User us;
            Statement st;
            ResultSet rsCars;
            System.out.println("[");
            while (rs.next()) {
                st = conn.createStatement();
                rsCars = st.executeQuery("SELECT * FROM car WHERE ownerID="+rs.getInt(1)+";");
                us = new User(rs, getCarArray(rsCars));
                us.show();
            }
            System.out.println("]");
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
    }

    String showUsersString(ResultSet rs){
        String result = new String();
        try {
            User us;
            Statement st;
            ResultSet rsCars;
            while (rs.next()) {
                st = conn.createStatement();
                rsCars = st.executeQuery("SELECT * FROM car WHERE ownerID="+rs.getInt(1)+";");
                us = new User(rs, getCarArray(rsCars));
                result += us.toString();
                result += "\n";
            }
            System.out.println("]");
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
        return result;
    }

    ArrayList<User> getUserArray(ResultSet rs){
        ArrayList<User> userList = new ArrayList<>();
        try {
            ResultSet rsCar;
            Statement st;
            while (rs.next()){
                st=conn.createStatement();
                rsCar = st.executeQuery("SELECT * FROM car WHERE ownerID="+rs.getInt(1)+";");
                userList.add(new User(rs,getCarArray(rsCar)));
            }
        }catch (SQLException e){
            System.out.println("Error in forming array");
        }
        return userList;
    }

    void allUsers(){
        try{
            Statement st = conn.createStatement();
            showUsers(st.executeQuery("select * from owner;"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String allUsersString(){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            result = showUsersString(st.executeQuery("select * from owner;"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void hasCarCount(int count){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            ArrayList<User> list = getUserArray(st.executeQuery("select * from owner;"));
            for(User user:list){
                if(user.carList.size()==count){
                    user.show();
                }
            }
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String hasCarCountString(int count){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            ArrayList<User> list = getUserArray(st.executeQuery("select * from owner;"));
            for(User user:list){
                if(user.carList.size()==count){
                    result+=user.toString();
                    result+="\n";
                }
            }
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void showManufacturers(ResultSet rs){
        try {
            Manufacturer manuf;
            Statement st;
            ResultSet rsCars;
            System.out.println("[");
            while (rs.next()) {
                st = conn.createStatement();
                rsCars = st.executeQuery("SELECT * FROM car WHERE manufacturerID="+rs.getInt(1)+";");
                manuf = new Manufacturer(rs.getInt(1),rs.getString(2), getCarArray(rsCars));
                manuf.show();
            }
            System.out.println("]");
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
    }

    String showManufacturersString(ResultSet rs){
        String result = new String ();
        try {
            Manufacturer manuf;
            Statement st;
            ResultSet rsCars;
            while (rs.next()) {
                st = conn.createStatement();
                rsCars = st.executeQuery("SELECT * FROM car WHERE manufacturerID="+rs.getInt(1)+";");
                manuf = new Manufacturer(rs.getInt(1),rs.getString(2), getCarArray(rsCars));
                result+=manuf.toString();
                result+="\n\n";
            }
        }catch (SQLException e){
            System.out.println("Error in result set");
        }
        return result;
    }

    void allManufacturers(){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            showManufacturers(st.executeQuery("select * from manufacturer;"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String allManufacturersString(){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            result+= showManufacturersString(st.executeQuery("select * from manufacturer;"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    void manufacturerName(String name){
        try{
            Statement st = conn.createStatement();
            System.out.println("[");
            System.out.println();
            showManufacturers(st.executeQuery("select * from manufacturer WHERE name ='"+name+"';"));
            System.out.println("]");
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement " + e);
        }
    }

    String manufacturerNameString(String name){
        String result = new String();
        try{
            Statement st = conn.createStatement();
            result += showManufacturersString(st.executeQuery("select * from manufacturer WHERE name ='"+name+"';"));
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement " + e);
        }
        return result;
    }

    void manufacturerYear(int year){
        try{
            ResultSet rsManf;
            ResultSet rsCars;
            Manufacturer mf;
            Statement st = conn.createStatement();
            System.out.println();
            rsManf = st.executeQuery("select * from manufacturer;");
            while (rsManf.next()){
                st=conn.createStatement();
                rsCars=st.executeQuery("SELECT * FROM car WHERE yearBuilt='"+year+"' AND manufacturerID = '"+rsManf.getInt(1)+"';");
                if(rsCars.next()) {
                    rsCars.previous();
                    mf = new Manufacturer(rsManf.getInt(1), rsManf.getString(2), getCarArray(rsCars));
                    mf.show();
                }
            }
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
    }

    String manufacturerYearString(int year){
        String result = new String();
        try{
            ResultSet rsManf;
            ResultSet rsCars;
            Manufacturer mf;
            Statement st = conn.createStatement();
            rsManf = st.executeQuery("select * from manufacturer;");
            while (rsManf.next()){
                st=conn.createStatement();
                rsCars=st.executeQuery("SELECT * FROM car WHERE yearBuilt='"+year+"' AND manufacturerID = '"+rsManf.getInt(1)+"';");
                if(rsCars.next()) {
                    rsCars.previous();
                    mf = new Manufacturer(rsManf.getInt(1), rsManf.getString(2), getCarArray(rsCars));
                    result += mf.toString();
                }
            }
            st.close();
        }catch (SQLException e){
            System.out.println("Error in statement");
        }
        return result;
    }

    int createCarInput(){
        int carId =-1;
        try{
            String manuf,model, plate;
            int year=-1,price=-1,idOwner=-1;
            Scanner in = new Scanner(System.in);
            String temp;
            System.out.print("Input model: ");
            temp = in.nextLine();
            temp = temp.replaceAll("[^a-zA-Z0-9 ]","");
            temp = temp.trim();
            model=temp;
            System.out.println();
            System.out.print("Input year built: ");
            temp = in.nextLine();
            temp = temp.replaceAll("[^0-9 ]","");
            temp = temp.trim();
            year = Integer.parseInt(temp);
            System.out.println();
            System.out.print("Input price: ");
            temp = in.nextLine();
            temp = temp.replaceAll("[^0-9 ]","");
            temp = temp.trim();
            price = Integer.parseInt(temp);
            System.out.println();

            System.out.print("Input plate number: ");
            temp = in.nextLine();
            temp = temp.replaceAll("[^a-zA-Z0-9 ]", "");
            temp = temp.trim();
            plate = temp;


            System.out.println();
            System.out.println("Do you already have an account? Y OR N:");
            temp = in.nextLine();

            temp = temp.toLowerCase();
            temp.trim();
            if(temp.equals("y")){
                System.out.println("Input owner id: ");
                idOwner = Integer.parseInt(in.nextLine());
            }
            else if(temp.equals("n")){
                System.out.println();
                idOwner=createOwner();
                if(idOwner<0) throw new Exception();
            }else{
                System.out.println("Unclear input!");
                throw new Exception();
            }
            System.out.println();
            System.out.print("Input manufacturer name: ");
            manuf = in.nextLine();
            manuf = manuf.replaceAll("[^a-zA-Z ]", "");
            if (!inputManufacturer(manuf)) throw new Exception();

            carId = passCarInput(model, plate, year, price, idOwner, manuf);
        }catch(Exception e){
            System.out.println("Error in creating car. Action stopped!");
        }
        return carId;
    }

    int createCarInputGui(){
        int carId =-1;
        try{
            String manuf,model, plate;
            int year=-1,price=-1,idOwner=-1;
            String temp;
            int response = JOptionPane.showConfirmDialog(null,"Do you already have an account?");
            if(response==0){
                idOwner=Integer.parseInt(JOptionPane.showInputDialog("Input owner id:"));
            }
            else if(response==1){
                System.out.println();
                idOwner=createOwnerGui();
            }else{
                System.out.println("Unclear input!");
                throw new Exception();
            }

            temp = JOptionPane.showInputDialog("Input the model of the car: ");
            temp = temp.replaceAll("[^a-zA-Z0-9 ]","");
            temp = temp.trim();
            model=temp;

            temp = JOptionPane.showInputDialog("Input the year the car was built: ");
            temp = temp.replaceAll("[^0-9 ]","");
            temp = temp.trim();
            year = Integer.parseInt(temp);

            temp = JOptionPane.showInputDialog("Input the year the price of the car: ");
            temp = temp.replaceAll("[^0-9 ]","");
            temp = temp.trim();
            price = Integer.parseInt(temp);

            temp = JOptionPane.showInputDialog("Input the car's plate number: ");
            temp = temp.replaceAll("[^a-zA-Z0-9 ]", "");
            temp = temp.trim();
            plate = temp;


            manuf = JOptionPane.showInputDialog("Input the name of the car's manufacturer: ");
            manuf = manuf.replaceAll("[^a-zA-Z ]", "");
            if (!inputManufacturer(manuf)) throw new Exception();
            carId = passCarInput(model, plate, year, price, idOwner, manuf);
        }catch(Exception e){
            System.out.println("Error in creating car. Action stopped!");
            return -5;
        }
        return carId;
    }

    private int passCarInput(String model, String plate, int year, int price, int idOwner, String manuf){
        int carIdNr = -1;
        try {
            if (manuf.isEmpty() || model.isEmpty() || plate.isEmpty() || year < 0 || price < 0) throw new Exception();
            carIdNr = insertCar(new Car(manuf, model, plate, getOwner(idOwner), price, year), idOwner);
            System.out.println("Car inserted in database");
        }catch (Exception e){
            System.out.println("INVALID INPUT FOR DATABASE!");
        }
        return carIdNr;
    }
}