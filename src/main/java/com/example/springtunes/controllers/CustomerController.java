package com.example.springtunes.controllers;

import com.example.springtunes.data_access.CustomerRepository;
import com.example.springtunes.models.Country;
import com.example.springtunes.models.Customer;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.getAllCustomers();
        return allCustomers;
    }

    @RequestMapping(value = "/customers/new", method = RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer, BindingResult error) {
        customerRepository.addCustomer(customer);
        return customer;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public Customer updateSpecificCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        customerRepository.updateCustomer(customer);
        return customer;
    }

    @RequestMapping(value = "/customers/per-country", method = RequestMethod.GET)
    public List<Country> getCustomersPerCountry() {
        List<Country> customersPerCountry = customerRepository.getCustomersPerCountry();
        return customersPerCountry;
    }

    @RequestMapping(value = "/customers/by-invoice-total", method = RequestMethod.GET)
    public List<String> getCustomersByInvoiceTotal() {
        List<String> customersByInvoiceTotal = customerRepository.getCustomersByInvoiceTotal();
        return customersByInvoiceTotal;
    }

    @RequestMapping(value = "/customers/{id}/popular/genre", method = RequestMethod.GET)
    public List<String> getMostPopularGenreForUser(@PathVariable String id) {
        List<String> mostPopularGenre = customerRepository.getMostPopularGenreForUser(id);
        return mostPopularGenre;
    }
}