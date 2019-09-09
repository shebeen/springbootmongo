package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.model.Product;
import com.app.repositories.ProductRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = { "/product" })
public class ProductController {
	@Autowired
	ProductRepository productRepo;

	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<Void> createUser(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating User " + product.getProductName());
		productRepo.save(product);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	@PutMapping(value = "/update/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> updateUser(@PathVariable("id") String id, @RequestBody Product productUpdate) {
		System.out.println("Update hit");
		Optional<Product> product = productRepo.findById(id);
		if (product == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		productRepo.save(productUpdate);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getUserById(@PathVariable("id") long id) {
		System.out.println("Fetching User with id " + id);
		Product product = productRepo.findById(id);
		if (product == null) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>((Product) product, HttpStatus.OK);
	}

	@GetMapping(value = "/all", headers = "Accept=application/json")
	public List<Product> getAllUser() {
		List<Product> products = productRepo.findAll();
		return products;
	}

}
