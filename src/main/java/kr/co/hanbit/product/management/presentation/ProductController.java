package kr.co.hanbit.product.management.presentation;

import jakarta.validation.Valid;
import kr.co.hanbit.product.management.application.SimpleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final SimpleProductService simpleProductService;

    @Autowired
    ProductController(SimpleProductService simpleProductService) {
        this.simpleProductService = simpleProductService;
    }

    @PostMapping("/products")
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        return simpleProductService.add(productDto);
    }

    @GetMapping("/products/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return simpleProductService.findById(id);
    }

    @GetMapping("/products")
    public List<ProductDto> findProduct(@RequestParam(required = false) String name) {
        if (name == null) {
            return simpleProductService.findAll();
        } else {
            return simpleProductService.findByNameContaining(name);
        }
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productDto.setId(id); // productDto에 id값이 없을 경우 url의 id값으로 대체
        return simpleProductService.update(productDto);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        simpleProductService.delete(id);
    }
}
