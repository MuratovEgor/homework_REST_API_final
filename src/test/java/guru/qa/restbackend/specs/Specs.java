package guru.qa.restbackend.specs;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;

public class Specs {
    public static RequestSpecification booksRequestSpec = with()
            .baseUri("http://localhost:8080")
            .basePath("/books").contentType(ContentType.JSON);

    public static RequestSpecification authorsRequestSpec = with()
            .baseUri("http://localhost:8080")
            .basePath("/authors").contentType(ContentType.JSON);
}
