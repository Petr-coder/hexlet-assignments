package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping()
    public ResponseEntity<List<Product>> get(@RequestParam Optional<Integer> min, @RequestParam Optional<Integer> max) {
        int minValue = min.orElse(Integer.MIN_VALUE);
        int maxValue = max.orElse(Integer.MAX_VALUE);

        List<Product> productList = productRepository.findByPriceBetweenOrderByPriceAsc(minValue, maxValue);

        return ResponseEntity.ok().body(productList);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}