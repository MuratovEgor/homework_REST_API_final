package guru.qa.restbackend.tests;

import guru.qa.restbackend.domain.Book;
import guru.qa.restbackend.specs.Specs;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookControllerTests {
    @Test
    void getAllBooks() {
        Book[] books = Specs.booksRequestSpec
                .given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Book[].class);

        assertNotEquals(0, books.length);
    }

    @Test
    void addNewBook() {
        Book[] before = Specs.booksRequestSpec
                .given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Book[].class);

        int bookID = before.length + 1,
                authorID = 1;
        String bookName = "Сильмариллион";

        Specs.booksRequestSpec
                .given()
                .contentType(ContentType.JSON)
                .body("{" +
                        "\"book_id\": \"" + bookID + "\",\n" +
                        "\"book_name\": \"" + bookName + "\",\n" +
                        "\"author_id\": \"" + authorID + "\"" +
                        "}")
                .log().body()
                .when()
                .post("/add")
                .then()
                .statusCode(200)
                .log().body()
                .body("book_id", is(bookID),
                        "book_name", is(bookName),
                        "author_id", is(authorID)
                );

        Book[] after = Specs.booksRequestSpec
                .given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Book[].class);


        assertEquals(before.length + 1, after.length);

    }

    @Test
    void getBookById() {

        Integer bookId = 1;
        String bookName = "Властелин колец";

        Specs.booksRequestSpec
                .given()
                .when()
                .get("/withId/" + bookId)
                .then()
                .statusCode(200)
                .log().body()
                .body("book_id[0]", is(bookId),
                        "book_name[0]", is(bookName));
    }

    @Test
    void getBooksByAuthorId() {

        Integer authorId = 1,
                booksCount = 2;

        Book[] books = Specs.booksRequestSpec
                .given()
                .when()
                .get("/withAuthorId/" + authorId)
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Book[].class);

        assertEquals(booksCount, books.length);
    }
}
