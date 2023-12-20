package com.example.demo2;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product newProduct) {
        return repository.save(newProduct);
    }

    @PutMapping("/{id}")
    public Product update(@RequestBody Product updatedProduct, @PathVariable Long id) {
        return repository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setQuantity(updatedProduct.getQuantity());
                    product.setPrice(updatedProduct.getPrice());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    updatedProduct.setId(id);
                    return repository.save(updatedProduct);
                });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
