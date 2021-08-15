package guru.qa.restbackend.controller;

import guru.qa.restbackend.domain.Book;
import guru.qa.restbackend.exception.BookNotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
public class BookController {
    private final Map<Integer, Book> books = new HashMap<>(Map.of(
            1, Book.builder()
                    .bookId(1)
                    .bookName("Властелин колец")
                    .authorId(1)
                    .build(),
            2, Book.builder()
                    .bookId(2)
                    .bookName("Хоббит")
                    .authorId(1)
                    .build(),
            3, Book.builder()
                    .bookId(3)
                    .bookName("Гарри Поттер")
                    .authorId(2)
                    .build()
    ));

    @GetMapping("books/all")
    @ApiOperation("Получить все книги")
    public List<Book> getAllBooks() {
        return books.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    @PostMapping(value = "books/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Добавить книгу")
    public Book addBook(@RequestBody Book book) {
        books.put(books.size() + 1, book);
        return Book.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .authorId(book.getAuthorId())
                .build();
    }

    @GetMapping("books/withId/{id}")
    @ApiOperation("Получить книгу по id")
    public List<Book> getBookById(@PathVariable("id") Integer id) {
        if (books.containsKey(id)) {
            return books.entrySet()
                    .stream()
                    .map(Map.Entry::getValue)
                    .filter(book -> book.getBookId() == id)
                    .collect(toList());
        } else throw new BookNotFoundException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("books/authorId/{id}")
    @ApiOperation("Получить книгу по id автора")
    public List<Book> getBooksByAuthorId(@PathVariable("id") Integer id) {
        if (books.containsKey(id)) {
            return books.entrySet()
                    .stream()
                    .map(Map.Entry::getValue)
                    .filter(book -> book.getAuthorId() == id)
                    .collect(toList());
        } else throw new BookNotFoundException(HttpStatus.NOT_FOUND);
    }
}
