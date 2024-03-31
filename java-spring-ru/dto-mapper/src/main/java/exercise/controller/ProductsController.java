package exercise.controller;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    /*     Создайте в контроллере обработчики, которые будут обрабатывать следующие запросы:

    GET /products – вывод списка всех товаров
    GET /products/{id} – просмотр конкретного товара
    POST /products – создание нового товара
    PUT /products/{id} – редактирование товара     */

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @GetMapping(path = "")
    public List<ProductDTO> getAll() {
        var products = productRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        var productDTO = productMapper.map(product);

        return productDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO productData) {
        var product = toEntity(productData);
        productRepository.save(product);
        var productDto = productMapper.map(product);
        return productDto;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@RequestBody ProductUpdateDTO productUpdateData, @PathVariable Long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        productMapper.update(productUpdateData, product);
        productRepository.save(product);
        var productUpdatedDTO = productMapper.map(product);

        return productUpdatedDTO;
    }


    // END

    private Product toEntity(ProductCreateDTO productDto) {
        var product = new Product();
        product.setName(productDto.getTitle());
        product.setCost(productDto.getPrice());
        product.setBarcode(productDto.getVendorCode());
        return product;
    }

    private Product toEntity(ProductUpdateDTO productUpdateDTO, Product product) {
        product.setCost(productUpdateDTO.getPrice());
        return product;
    }

    private ProductDTO toDTO(Product product) {
        var dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getName());
        dto.setPrice(product.getCost());
        dto.setVendorCode(product.getBarcode());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}