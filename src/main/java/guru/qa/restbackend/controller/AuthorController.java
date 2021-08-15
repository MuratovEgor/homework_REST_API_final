package guru.qa.restbackend.controller;

import guru.qa.restbackend.domain.Author;
import guru.qa.restbackend.exception.AuthorNotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
public class AuthorController {
    private Map<Integer, Author> authors = new HashMap<>(Map.of(
            1, Author.builder()
                    .authorId(1)
                    .authorName("Толкин")
                    .build(),
            2, Author.builder()
                    .authorId(2)
                    .authorName("Роулинг")
                    .build()
    ));

    @GetMapping("authors/all")
    @ApiOperation("Получить всех авторов")
    public List<Author> getAllAuthors() {
        return authors.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    @PostMapping("authors/add")
    @ApiOperation("Добавить автора")
    public Author addAuthor(@RequestBody Author author) {
        authors.put(authors.size() + 1, author);

        return Author.builder()
                .authorId(author.getAuthorId())
                .authorName(author.getAuthorName())
                .build();
    }

    @GetMapping("authors/getById/{id}/")
    @ApiOperation("Получить автора по id")
    public List<Author> getAuthorById(@PathVariable("id") Integer id) {
        if (authors.containsKey(id)) {
            return authors.entrySet()
                    .stream()
                    .map(Map.Entry::getValue)
                    .filter(author -> author.getAuthorId() == id)
                    .collect(toList());}
        else throw new AuthorNotFoundException(HttpStatus.NOT_FOUND);
    }



}
