package com.example.springtunes.data_access;

import com.example.springtunes.models.Country;
import com.example.springtunes.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This class serves as the encapsulation of database interactions for customers API in order to
// to keep the logic out of the CustomerController as much as possible.
public class CustomerRepository {
    private final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;
    Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    private Customer createCustomer(ResultSet set) throws SQLException {
        return new Customer(
                set.getString("CustomerId"),
                set.getString("FirstName"),
                set.getString("LastName"),
                set.getString("Country"),
                set.getString("PostalCode"),
                set.getString("Phone"),
                set.getString("Email")
        );
    }

    private void closeConnection() {
        try {
            conn.close();
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName, " +
                            "Country, PostalCode, Phone, Email FROM Customer");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                customers.add(createCustomer(set));
            }
            logger.info("Get all customers went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return customers;
    }

    public boolean addCustomer(Customer customer) {
        boolean isSuccess = false;
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("INSERT INTO Customer(CustomerId, FirstName, LastName," +
                            "Country, PostalCode, Phone, Email) VALUES(?,?,?,?,?,?,?)");
            prep.setString(1, customer.getCustomerId());
            prep.setString(2, customer.getFirstName());
            prep.setString(3, customer.getLastName());
            prep.setString(4, customer.getCountry());
            prep.setString(5, customer.getPostalCode());
            prep.setString(6, customer.getPhone());
            prep.setString(7, customer.getEmail());

            int result = prep.executeUpdate();
            isSuccess = (result != 0); // if res = 1; true

            logger.info("Add customer went well!");
        } catch(Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return isSuccess;
    }

    public Customer getSpecificCustomer(String id) {
        Customer customer = null;
        if (id == null) {
            id = getLastId();
        }
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName, " +
                            "Country, PostalCode, Phone, Email FROM Customer WHERE CustomerId=?");
            prep.setString(1, id);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                customer = createCustomer(set);
            }
            logger.info("Get specific customer went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return customer;
    }

    // This method is used for a situation where a new customer is added without specifying the customerId.
    private String getLastId() {
        String lastId = null;
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId FROM Customer " +
                            "ORDER BY CustomerId DESC LIMIT 1");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                lastId = set.getString("CustomerId");
            }
            logger.info("Get last id went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return lastId;
    }

    public boolean updateCustomer(String id, Customer customer) {
        boolean isSuccess = false;
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("UPDATE Customer SET FirstName=?, LastName=?, " +
                            "Country=?, PostalCode=?, Phone=?, Email=? WHERE CustomerId=?");
            prep.setString(1, customer.getFirstName());
            prep.setString(2, customer.getLastName());
            prep.setString(3, customer.getCountry());
            prep.setString(4, customer.getPostalCode());
            prep.setString(5, customer.getPhone());
            prep.setString(6, customer.getEmail());
            prep.setString(7, id);

            int result = prep.executeUpdate();
            isSuccess = (result != 0); // if res = 1; true

            logger.info("Update customer went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return isSuccess;
    }

    public ArrayList<Country> getCustomersPerCountry(){
        ArrayList<Country> customersPerCountry = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Country, COUNT(*) AS CustomerCount " +
                            "FROM Customer GROUP BY Country ORDER BY COUNT(*) DESC");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                customersPerCountry.add( new Country(
                    set.getString("Country"),
                    set.getString("CustomerCount")
                ));
            }
            logger.info("Get customers per country went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return customersPerCountry;
    }

    public ArrayList<String> getCustomersByInvoiceTotal(){
        ArrayList<String> customersByInvoiceTotal = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT FirstName, LastName, SUM(Total) AS TotalSum " +
                            "FROM Invoice JOIN Customer ON Invoice.CustomerId = Customer.CustomerId " +
                            "GROUP BY Invoice.CustomerId ORDER BY TotalSum DESC");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                customersByInvoiceTotal.add(set.getString("FirstName") + " " +
                        set.getString("LastName") + ": " + set.getString("TotalSum"));
            }
            logger.info("Get customers by invoice total went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return customersByInvoiceTotal;
    }

    // Return type is a list because in case of a tie, all the genres with the max listening count will be selected
    public List<String> getMostPopularGenreForUser(String id){
        List<String> mostPopularGenre = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("WITH CountQuery AS (" +
                            "SELECT G.Name AS Name, COUNT(T.GenreId) AS GenreCount " +
                            "FROM Genre G JOIN Track T ON G.GenreId = T.GenreId  " +
                            "JOIN InvoiceLine IL ON T.TrackId = IL.TrackId " +
                            "JOIN Invoice I ON IL.InvoiceId = I.InvoiceId " +
                            "JOIN Customer C ON I.CustomerId = C.CustomerId " +
                            "WHERE C.CustomerId = ? GROUP BY T.GenreId) " +
                            "SELECT Name, GenreCount FROM CountQuery " +
                            "WHERE (SELECT MAX(GenreCount) FROM CountQuery) = GenreCount");
            prep.setString(1, id);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                mostPopularGenre.add("Genre most listened to: " + set.getString("Name") +
                        ", times listened to: " + set.getString("GenreCount"));
            }
            logger.info("Get most popular genre(s) went well!");
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return mostPopularGenre;
    }
}
