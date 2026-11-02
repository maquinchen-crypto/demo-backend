package com.example.demo.service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BookService {
@Autowired
private BookRepository bookRepository;
public List<Book> findAll() {
    return bookRepository.findAll();
}
public Book findById(Long id) {
    return bookRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado: " + id));
}
public Book save(Book book) {
    book.setId(null);
    return bookRepository.save(book);
}
public Book update(Long id, Book data) {
    Book existing = findById(id);
    existing.setTitle(data.getTitle());
    existing.setAuthor(data.getAuthor());
    return bookRepository.save(existing);
}
public void delete(Long id) {
    if (!bookRepository.existsById(id)) {
    throw new ResourceNotFoundException("Libro no encontrado: " + id);}
    bookRepository.deleteById(id);
}
}