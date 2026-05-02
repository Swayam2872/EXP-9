import java.sql.*;

public class RestaurantCRUD {

    static final String URL = "jdbc:mysql://localhost:3306/RestaurantDB";
    static final String USER = "root";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            insertRestaurants(con);
            insertMenuItems(con);

            System.out.println("\n--- Menu Items (Price <= 100) ---");
            selectPriceLessThan100(con);

            System.out.println("\n--- Items from Cafe Java ---");
            selectFromCafeJava(con);

            updatePrice(con);

            System.out.println("\n--- After Updating Price ---");
            selectAllMenu(con);

            deleteItems(con);

            System.out.println("\n--- After Deleting Items Starting with P ---");
            selectAllMenu(con);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INSERT 10 Restaurants
    static void insertRestaurants(Connection con) throws SQLException {
        String[] names = {"Cafe Java", "Food Hub", "Spice Villa", "Urban Bites", "Tasty Treat",
                          "Green Leaf", "BBQ Nation", "Pizza Point", "Burger King", "Snack Spot"};

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Restaurant(Name, Address) VALUES (?, ?)");

        for (int i = 0; i < 10; i++) {
            ps.setString(1, names[i]);
            ps.setString(2, "Address " + (i + 1));
            ps.executeUpdate();
        }
    }

    // INSERT 10 Menu Items
    static void insertMenuItems(Connection con) throws SQLException {
        String[] items = {"Pizza", "Pasta", "Burger", "Fries", "Sandwich",
                          "Noodles", "Paneer", "Soup", "Salad", "Coffee"};

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO MenuItem(Name, Price, ResId) VALUES (?, ?, ?)");

        for (int i = 0; i < 10; i++) {
            ps.setString(1, items[i]);
            ps.setInt(2, (i + 1) * 50); // Prices: 50,100,...500
            ps.setInt(3, (i % 3) + 1); // Link to restaurants
            ps.executeUpdate();
        }
    }

    // SELECT price <=100
    static void selectPriceLessThan100(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM MenuItem WHERE Price <= 100");

        printResult(rs);
    }

    // SELECT items from Cafe Java
    static void selectFromCafeJava(Connection con) throws SQLException {
        String query = "SELECT m.* FROM MenuItem m " +
                       "JOIN Restaurant r ON m.ResId = r.Id " +
                       "WHERE r.Name = 'Cafe Java'";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        printResult(rs);
    }

    // UPDATE price <=100 → 200
    static void updatePrice(Connection con) throws SQLException {
        Statement st = con.createStatement();
        int rows = st.executeUpdate(
                "UPDATE MenuItem SET Price = 200 WHERE Price <= 100");

        System.out.println("\nUpdated Rows: " + rows);
    }

    // DELETE name starts with P
    static void deleteItems(Connection con) throws SQLException {
        Statement st = con.createStatement();
        int rows = st.executeUpdate(
                "DELETE FROM MenuItem WHERE Name LIKE 'P%'");

        System.out.println("\nDeleted Rows: " + rows);
    }

    // SELECT ALL
    static void selectAllMenu(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM MenuItem");

        printResult(rs);
    }

    // PRINT ResultSet in Table Format
    static void printResult(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        // Print Header
        for (int i = 1; i <= columns; i++) {
            System.out.print(md.getColumnName(i) + "\t");
        }
        System.out.println();

        // Print Rows
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
    }
}