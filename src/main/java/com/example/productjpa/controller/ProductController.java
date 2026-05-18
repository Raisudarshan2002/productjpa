package com.example.productjpa.controller;

import com.example.productjpa.entity.Product;
import com.example.productjpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
    public String list(Model model) {

        model.addAttribute("products", productService.findAll());

        return "Product/List";
    }

    @GetMapping("/products/new")
    public String newForm() {
        return "Product/form";
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("product", productService.findById(id));
            return "Product/detail";
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/products")
    public String createForm(
            Product product,
            @RequestParam("imageFile") MultipartFile imageFile) {

        productService.addProduct(product, imageFile);

        return "redirect:/products";
    }
    @PostMapping("/findProduct")
    public String findProduct(@RequestParam("name") String name, Model model){
        List<Product> products = productService.findProduct(name);
        model.addAttribute("products", products);
        return "Product/List";
    }

}