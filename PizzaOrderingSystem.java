import java.sql.*;
import java.util.Scanner;

class PizzaOrderingSystem
{
    static Connection con;

    static void connect() throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:C:/SqlLite/PizzaOrder.db");
    }

    static void addOrder(Scanner sc) throws Exception
    {
        String sql = "INSERT INTO PizzaOrder VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Order ID: ");
        ps.setInt(1, sc.nextInt());
        sc.nextLine();

        System.out.print("Customer Name: ");
        ps.setString(2, sc.nextLine());

        System.out.print("Pizza Type: ");
        ps.setString(3, sc.nextLine());

        System.out.print("Quantity: ");
        ps.setInt(4, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Order placed successfully.\n");
        ps.close();
    }

    static void viewOrders() throws Exception
    {
        String sql = "SELECT * FROM PizzaOrder";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\nID  Name   Pizza   Qty");
        System.out.println("-------------------------");

        while (rs.next())
        {
            System.out.println(
                rs.getInt(1) + "   " +
                rs.getString(2) + "   " +
                rs.getString(3) + "   " +
                rs.getInt(4)
            );
        }

        rs.close();
        ps.close();
    }

    static void searchOrder(Scanner sc) throws Exception
    {
        String sql = "SELECT * FROM PizzaOrder WHERE OrderId=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Enter Order ID: ");
        ps.setInt(1, sc.nextInt());

        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            System.out.println(
                rs.getInt(1) + " " +
                rs.getString(2) + " " +
                rs.getString(3) + " " +
                rs.getInt(4)
            );
        }
        else
        {
            System.out.println("Order not found.");
        }

        rs.close();
        ps.close();
    }

    static void updatePizza(Scanner sc) throws Exception
    {
        String sql = "UPDATE PizzaOrder SET PizzaType=? WHERE OrderId=?";
        PreparedStatement ps = con.prepareStatement(sql);

        sc.nextLine();
        System.out.print("New Pizza Type: ");
        ps.setString(1, sc.nextLine());

        System.out.print("Order ID: ");
        ps.setInt(2, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Pizza updated.\n");
        ps.close();
    }

    static void deleteOrder(Scanner sc) throws Exception
    {
        String sql = "DELETE FROM PizzaOrder WHERE OrderId=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Order ID: ");
        ps.setInt(1, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Order deleted.\n");
        ps.close();
    }

    public static void main(String[] args) throws Exception
    {
        connect();
        Scanner sc = new Scanner(System.in);
        int choice;

        do
        {
            System.out.println("\n--- Pizza Ordering System ---");
            System.out.println("1. Place Order");
            System.out.println("2. View Orders");
            System.out.println("3. Search Order");
            System.out.println("4. Update Pizza");
            System.out.println("5. Delete Order");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();

            switch (choice)
            {
                case 1: addOrder(sc); break;
                case 2: viewOrders(); break;
                case 3: searchOrder(sc); break;
                case 4: updatePizza(sc); break;
                case 5: deleteOrder(sc); break;
                case 6: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice");
            }
        }
        while (choice != 6);

        con.close();
        sc.close();
    }
}
