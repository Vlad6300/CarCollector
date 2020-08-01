import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JFrame {
    CarCollector coll;
    Display(){
        setTitle("Car Collector");
        GridLayout grdLayout = new GridLayout(8,3);
        grdLayout.setHgap(5);
        grdLayout.setVgap(5);
        JPanel buttons = new JPanel();
        buttons.setLayout(grdLayout);
        coll = new CarCollector();
        setSize(600,800);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton carYear = new JButton("Cars by Year");
        JButton priceFrom = new JButton("Price starting at");
        JButton priceTo = new JButton("Price under");
        JButton manufacturerId = new JButton("Cars by Manufacturer");
        JButton allCar  = new JButton("All Cars");
        JButton carId = new JButton("Car by ID");
        JButton allUsers = new JButton("All Users");
        JButton userNrCars = new JButton("User with X cars");
        JButton allManuf = new JButton("All manufacturers");
        JButton nameManuf = new JButton("Manufacturer by name");
        JButton yearManuf = new JButton("Manufacturer by year");
        JButton insertCar = new JButton("Insert car");

        allCar.addActionListener(new buttonAction(this, 5));
        allUsers.addActionListener(new buttonAction(this,7));
        carId.addActionListener(new buttonAction(this,6));
        carYear.addActionListener(new buttonAction(this,1));
        priceFrom.addActionListener(new buttonAction(this,2));
        priceTo.addActionListener(new buttonAction(this,3));
        manufacturerId.addActionListener(new buttonAction(this,4));
        userNrCars.addActionListener(new buttonAction(this,8));
        allManuf.addActionListener(new buttonAction(this,9));
        nameManuf.addActionListener(new buttonAction(this,10));
        yearManuf.addActionListener(new buttonAction(this, 11));
        insertCar.addActionListener(new buttonAction(this,12));

        buttons.add(allCar);
        buttons.add(allUsers);
        buttons.add(allManuf);

        buttons.add(carId);
        buttons.add(userNrCars);
        buttons.add(nameManuf);

        buttons.add(carYear);
        buttons.add(new JPanel());
        buttons.add(yearManuf);

        buttons.add(priceFrom);
        buttons.add(new JPanel());
        buttons.add(new JPanel());

        buttons.add(priceTo);
        buttons.add(new JPanel());
        buttons.add(new JPanel());

        buttons.add(manufacturerId);
        buttons.add(new JPanel());
        buttons.add(new JPanel());

        buttons.add(insertCar);


        add(buttons);
        setVisible(true);
    }
}

class buttonAction implements ActionListener {
    Display d;
    int act;

    buttonAction(Display dis, int action) {
        d = dis;
        act = action;
    }

    // 1 - Year built; 2 - Price from; 3 - Price to; 4 - Manufacturer ID; 5 - All cars;
    // 6 - Car by ID; 7 - All users; 8 - User with X cars; 9 - All manufacturers; 10 - Manufacturer by name;
    // 11 - Manufacturer by year; 12 - Insert car;

    @Override
    public void actionPerformed(ActionEvent e) {
        String message;
        switch (act) {
            case 1:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input year: ").replaceAll("[^0-9]",""));
                    message = "Cars built in year " + i + "\n\n" + d.coll.yearString(i);
                    JOptionPane.showMessageDialog(d, message, "Cars built in year " + i, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID YEAR INPUT", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;

            case 2:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input price starting range: ").replaceAll("[^0-9]",""));
                    message = "Cars starting from: " + i + "\n\n" + d.coll.priceFromString(i);
                    JOptionPane.showMessageDialog(d, message, "Cars starting from " + i + " money", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID PRICE INPUT", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;

            case 3:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input price cap: ").replaceAll("[^0-9]",""));
                    message = "Cars below " + i + " money: \n\n" + d.coll.priceToString(i);
                    JOptionPane.showMessageDialog(d, message, "Cars below " + i + " money", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID PRICE INPUT", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;

            case 4:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input Manufacturer ID: ").replaceAll("[^0-9 ]",""));
                    message = "Cars made by " + d.coll.getManufacturer(i) + ": \n\n" + d.coll.manufacturerIdString(i);
                    JOptionPane.showMessageDialog(d, message, "Cars below " + i + " money", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID ID", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;

            case 5:
                message = "All cars: \n\n" + d.coll.allCarsString();
                JOptionPane.showMessageDialog(d, message, "All cars", JOptionPane.INFORMATION_MESSAGE);
                break;

            case 6:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input car ID: ").replaceAll("[^0-9 ]",""));
                    message ="\n" + d.coll.carIdString(i);
                    JOptionPane.showMessageDialog(d, message, "Cars with ID: " + i, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID ID", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;

            case 7:
                message = "All users: \n\n" + d.coll.allUsersString();
                JOptionPane.showMessageDialog(d, message, "All users", JOptionPane.INFORMATION_MESSAGE);
                break;

            case 8:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input car count(or X): ").replaceAll("[^0-9 ]",""));
                    message ="Users with "+i+" cars: \n\n" + d.coll.hasCarCountString(i);
                    JOptionPane.showMessageDialog(d, message, "Cars with ID: " + i, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID ID", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;

            case 9:
                message = "All manufacturers: \n\n" + d.coll.allManufacturersString();
                JOptionPane.showMessageDialog(d, message, "All manufacturers", JOptionPane.INFORMATION_MESSAGE);
                break;

            case 10:
                try {
                    String i = JOptionPane.showInputDialog("Input manufacturer name: ").trim().replaceAll("[^a-zA-Z ]","");
                    message ="Information about manufacturer: "+i+"\n\n"+d.coll.manufacturerNameString(i);
                    JOptionPane.showMessageDialog(d, message, "Information about " + i, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID NAME", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;
            case 11:
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Input year: ").replaceAll("[^0-9]",""));
                    message ="Information about year: "+i+"\n\n"+d.coll.manufacturerYearString(i);
                    JOptionPane.showMessageDialog(d, message, "Information about year" + i, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(d, "INVALID YEAR", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    System.out.println(exc);
                }
                break;
            case 12:
                int i = d.coll.createCarInputGui();
                if(i>0){
                    JOptionPane.showConfirmDialog(d, "Car has been added to the database", "Confirmation",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
        }
    }
}

