package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "")
    public List<Product> index() {
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    /*
    GET /products/{id} — просмотр конкретного товара
    PUT /products/{id} — обновление данных товара
     */

    // BEGIN
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " +
                "with id " + id + " not found."));

        return product;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product data) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " +
                "with id " + id + " not found."));

        product.setTitle(data.getTitle());
        product.setPrice(data.getPrice());

        return product;
    }

    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}