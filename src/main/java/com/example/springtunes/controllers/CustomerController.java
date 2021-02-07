package com.example.springtunes.controllers;

import com.example.springtunes.data_access.CustomerRepository;
import com.example.springtunes.models.Country;
import com.example.springtunes.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @RequestMapping(value = "/customers/new", method = RequestMethod.POST)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        if (customerRepository.addCustomer(customer)) {
            Customer addedCustomer = customerRepository.getSpecificCustomer(customer.getCustomerId());
            return new ResponseEntity<>(addedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateSpecificCustomer(@PathVariable String id, @RequestBody Customer customer) {
        if (customerRepository.updateCustomer(id, customer)) {
            Customer updatedCustomer = customerRepository.getSpecificCustomer(id);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/customers/per-country", method = RequestMethod.GET)
    public List<Country> getCustomersPerCountry() {
        return customerRepository.getCustomersPerCountry();
    }

    @RequestMapping(value = "/customers/by-invoice-total", method = RequestMethod.GET)
    public List<String> getCustomersByInvoiceTotal() {
        return customerRepository.getCustomersByInvoiceTotal();
    }

    @RequestMapping(value = "/customers/{id}/popular/genre", method = RequestMethod.GET)
    public List<String> getMostPopularGenreForUser(@PathVariable String id) {
        return customerRepository.getMostPopularGenreForUser(id);
    }
}