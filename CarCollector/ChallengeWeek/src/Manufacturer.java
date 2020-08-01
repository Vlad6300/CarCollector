import java.util.ArrayList;

public class Manufacturer {
    int id;
    String name;
    ArrayList<Car> carsProduced;
    Manufacturer(int id, String name, ArrayList<Car> carList){
        this.id=id;
        this.name=name;
        carsProduced = carList;
    }

    void show(){
        System.out.println("        \"ID\": \""+id+"\"");
        System.out.println("        \"Name\": \""+name+"\"");
        System.out.println("        \"Cars Produced\": [");
        for (Car c : carsProduced){
            System.out.print("\t");
            c.show();}
        System.out.println("]");
    }

    @Override
    public String toString() {
        String result = new String();
        result+=("        \"ID\": \""+id+"\"\n");
        result+=("        \"Name\": \""+name+"\"\n");
        result+=("        \"Cars Produced\": [\n");
        for (Car c : carsProduced){
            result+=c.toString();}
        result+=("        ]\n");
        return result;
    }
}
