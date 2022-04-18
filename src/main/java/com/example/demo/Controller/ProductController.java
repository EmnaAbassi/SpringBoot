package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.RessourceNotFoundException;
import com.example.demo.Repo.ProductRepository;
import com.example.demo.model.Product;


@RestController
@RequestMapping("/api/v1/")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	//get all products
	@GetMapping("/products")
	public List<Product> gelAllproducts(){
		return productRepository.findAll();
	}
	
	//create products
	@PostMapping("/products")
	public Product createproducts(@RequestBody Product p) {
		return productRepository.save(p);
	}
	
	//get products by id
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getproductsById(@PathVariable int id) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new RessourceNotFoundException("product not exist with id " +id));
	
		return ResponseEntity.ok(p);
	}
	
	
	//UPDATE products 
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateproducts(@PathVariable int id, @RequestBody Product ProductDetails){
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new RessourceNotFoundException("product not exist with id " +id));
		
		
		p.setName(ProductDetails.getName());
		p.setPrice(ProductDetails.getPrice());
		
		Product updatedArticle = productRepository.save(p);
		return ResponseEntity.ok(updatedArticle);
		
	}
	
	//Delete products
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteproducts (@PathVariable int id){
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new RessourceNotFoundException("product not exist with id " +id));
		
		productRepository.delete(p);
		//on utilse le map ici pour retourner un mesge de type boolean apres la suppression de notre en tite et qui va retourner deleted successfully
		Map<String, Boolean> response = new HashMap<>();
		
		response.put("Your entity is deleted :)", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}	

}
