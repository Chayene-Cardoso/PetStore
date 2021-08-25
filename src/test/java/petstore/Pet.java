package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";
// sempre usar isso para chamar o json

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));

    }
    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String jsonBory = lerJson("db/pet1.json");


        given()//dado
                .contentType("application/json")  // comum em API REST - antigas usam "test/xml"
                .log().all()
                .body(jsonBory)
        .when()//quando
                .post(uri)
        .then()//então
                .log().all()
                .statusCode(200)
                .body("name", is("luke"))
                .body("status", is("available"))
                .body("category.name",is("AX2345LORD"))
                .body("tags.name", contains("data"));
    }

    @Test(priority = 2)
    public void consultarPet(){

        String petId = "9643065515";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("luke"))
                .body("category.name", is("AX2345LORD"))
                .body("status", is("available"))
        .extract()
                .path("category.name")
        ;

                System.out.println("O token é " + token);
    }



}
