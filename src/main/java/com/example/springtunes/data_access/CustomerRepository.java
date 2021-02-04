package com.example.springtunes.data_access;

import com.example.springtunes.models.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerRepository {
    // Setting up the connection object we need.
    private String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName, " +
                            "Country, PostalCode, Phone, Email FROM customer");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                customers.add( new Customer(
                        set.getString("CustomerId"),
                        set.getString("FirstName"),
                        set.getString("LastName"),
                        set.getString("Country"),
                        set.getString("PostalCode"),
                        set.getString("Phone"),
                        set.getString("Email")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return customers;
    }

    public Customer getSpecificCustomer(String id){
        Customer customer = null;
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName, " +
                            "Country, PostalCode, Phone, Email FROM customer WHERE CustomerId=?");
            prep.setString(1,id);
            ResultSet set = prep.executeQuery();
            while(set.next()){
                customer = new Customer(
                        set.getString("CustomerId"),
                        set.getString("FirstName"),
                        set.getString("LastName"),
                        set.getString("Country"),
                        set.getString("PostalCode"),
                        set.getString("Phone"),
                        set.getString("Email")
                );
            }
            System.out.println("Get specific went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---

        return customer;
    }

    public Boolean addCustomer(Customer customer){
        Boolean success = false;
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("INSERT INTO customer(CustomerId, FirstName, LastName," +
                            "Country, PostalCode, Phone, Email) VALUES(?,?,?,?,?,?,?)");
            prep.setString(1, customer.getCustomerId());
            prep.setString(2, customer.getFirstName());
            prep.setString(3, customer.getLastName());
            prep.setString(4, customer.getCountry());
            prep.setString(5, customer.getPostalCode());
            prep.setString(6, customer.getPhone());
            prep.setString(7, customer.getEmail());

            int result = prep.executeUpdate();
            success = (result != 0); // if res = 1; true

            System.out.println("Add went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return success;
    }

    public Boolean updateCustomer(Customer customer){
        Boolean success = false;
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("UPDATE customer SET CustomerId=?, FirstName=?, LastName=?, " +
                            "Country=?, PostalCode=?, Phone=?, Email=? WHERE CustomerId=?");
            prep.setString(1, customer.getCustomerId());
            prep.setString(2, customer.getFirstName());
            prep.setString(3, customer.getLastName());
            prep.setString(4, customer.getCountry());
            prep.setString(5, customer.getPostalCode());
            prep.setString(6, customer.getPhone());
            prep.setString(7, customer.getEmail());
            prep.setString(8, customer.getCustomerId());

            int result = prep.executeUpdate();
            success = (result != 0); // if res = 1; true

            System.out.println("Update went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return success;
    }

    public ArrayList<String> getCustomersPerCountry(){
        ArrayList<String> customersPerCountry = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Country, COUNT(*) AS CustomerCount FROM " +
                            "customer GROUP BY Country ORDER BY COUNT(*) DESC");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                customersPerCountry.add( new String(
                        set.getString("Country") + ": " +
                                set.getString("CustomerCount")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return customersPerCountry;
    }

    public ArrayList<String> getCustomersByInvoiceTotal(){
        ArrayList<String> customersByInvoiceTotal = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT FirstName, SUM(Total) AS TotalSum FROM " +
                            "invoice JOIN Customer ON Invoice.CustomerId = Customer.CustomerId GROUP BY Invoice.CustomerId ORDER BY TotalSum DESC");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                customersByInvoiceTotal.add( new String(
                        set.getString("FirstName") + ": " +
                                set.getString("TotalSum")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return customersByInvoiceTotal;
    }

    public String getMostPopularGenreForUser(String id){
        String mostPopularGenre = "";
        // ---
        try {
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT G.Name AS Name, COUNT(T.GenreId) AS GenreCount FROM Genre G JOIN " +
                                    "Track T ON G.GenreId = T.GenreId JOIN InvoiceLine IL on T.TrackId = IL.TrackId " +
                                    " JOIN Invoice I on IL.InvoiceId = I.InvoiceId JOIN Customer C on I.CustomerId = C.CustomerId " +
                                    " WHERE C.CustomerId = ? GROUP BY T.GenreId ORDER BY GenreCount DESC LIMIT 1");
            prep.setString(1, id);
            ResultSet set = prep.executeQuery();
            while(set.next()){
                mostPopularGenre = "Most popular genre: " + set.getString("Name");
            }
            System.out.println("Get all went well!");

        } catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return mostPopularGenre;
    }
}
