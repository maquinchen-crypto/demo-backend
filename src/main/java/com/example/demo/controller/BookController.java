package com.example.demo.controller;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/books")
@Tag(name = "Libros", description = "Operaciones CRUD sobre libros")
public class BookController {
@Autowired
private BookService bookService;

@GetMapping
@Operation(summary = "Listar todos los libros")
public List<Book> getAll() {
return bookService.findAll();
}

@GetMapping("/{id}")
@Operation(summary = "Obtener un libro por su id")
public Book getById(@PathVariable Long id) {
    return bookService.findById(id);
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
@Operation(summary = "Crear un libro")
public Book create(@RequestBody Book book) {
    return bookService.save(book);
}

@PutMapping("/{id}")
@Operation(summary = "Actualizar un libro existente")
public Book update(@PathVariable Long id, @RequestBody Book book) {
    return bookService.update(id, book);
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
@Operation(summary = "Eliminar un libro")
public void delete(@PathVariable Long id) {
    bookService.delete(id);
}
}