package com.example.demo.controller;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/booksjwt")
@Tag(name = "Libros (JWT)", description = "Operaciones sobre libros protegidas con JWT")
@SecurityRequirement(name = "bearerAuth")
public class BookJwtController {
@Autowired
private BookService bookService;

@GetMapping
@Operation(summary = "Listar todos los libros (requiere token JWT)")
public List<Book> getAll() {
return bookService.findAll();
}
}
