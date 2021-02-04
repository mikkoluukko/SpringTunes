package com.example.springtunes.controllers;

import com.example.springtunes.data_access.CustomerRepository;
import com.example.springtunes.models.Customer;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.getAllCustomers();
        return allCustomers;
    }

//    @RequestMapping(value = "/customers/{id}")
//    public String getSpecificCustomer(@PathVariable String id, Model model){
//        model.addAttribute("customer", crep.getSpecificCustomer(id));
//        return "view-customer";
//    }
//
//    @RequestMapping(value = "/add-customers", method = RequestMethod.GET)
//    public String addCustomer(Model model){
//        model.addAttribute("customer", new Customer());
//        return "add-customers";
//    }
//
    @RequestMapping(value = "/customers/new", method = RequestMethod.POST)
    public String addCustomer(@RequestBody Customer customer, BindingResult error) {
        Boolean success = customerRepository.addCustomer(customer);
        return customer.getFirstName();
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public String updateSpecificCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        Boolean success = customerRepository.updateCustomer(customer);
        return customer.getFirstName();
    }

    @RequestMapping(value = "/customers/per-country", method = RequestMethod.GET)
    public List<String> getCustomersPerCountry() {
        List<String> customersPerCountry = customerRepository.getCustomersPerCountry();
        return customersPerCountry;
    }

    @RequestMapping(value = "/customers/by-invoice-total", method = RequestMethod.GET)
    public List<String> getCustomersByInvoiceTotal() {
        List<String> customersByInvoiceTotal = customerRepository.getCustomersByInvoiceTotal();
        return customersByInvoiceTotal;
    }

    @RequestMapping(value = "/customers/{id}/most-popular-genre", method = RequestMethod.GET)
    public String getMostPopularGenreForUser(@PathVariable String id) {
        String mostPopularGenre = customerRepository.getMostPopularGenreForUser(id);
        return mostPopularGenre;
    }
}
