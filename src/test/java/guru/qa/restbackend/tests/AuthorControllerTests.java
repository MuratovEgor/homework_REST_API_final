package guru.qa.restbackend.tests;

import guru.qa.restbackend.domain.Author;
import guru.qa.restbackend.specs.Specs;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AuthorControllerTests {
    @Test
    void getAllAuthors() {
        Author[] authors = Specs.authorsRequestSpec
                .given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Author[].class);

        assertNotEquals(0, authors.length);
    }


    @Test
    void addNewAuthor() {
        Author[] before = Specs.authorsRequestSpec
                .given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Author[].class);

        int newAuthorsID = before.length + 1;

        Specs.authorsRequestSpec
                .body("{" +
                        "\"author_id\": \"" + newAuthorsID + "\",\n" +
                        "\"author_name\": \"Толстой\"" +
                        "}")
                .log().body()
                .when()
                .post("/add")
                .then()
                .statusCode(200)
                .log().body()
                .body("author_id", is(newAuthorsID),
                        "author_name", is("Толстой"));

        Author[] after = Specs.authorsRequestSpec
                .given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response().as(Author[].class);

        assertEquals(before.length + 1, after.length);
    }

    @Test
    void getAuthorById() {
        Specs.authorsRequestSpec
                .given()
                .when()
                .get("/withId/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("author_id[0]", is(2),
                        "author_name[0]", is("Роулинг"));
    }
}
