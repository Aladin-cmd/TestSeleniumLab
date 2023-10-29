package org.example.lab2;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserTestLab2ProkhorenkoDV {

    private static final String baseUrl = "https://petstore.swagger.io/v2";
    public static final String name = "prokhorenko_dv";
    public static final Integer category_id = 2;
    public static final Integer pet_id = 25;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyLoginAction() {
        Map<String, ?> body = Map.of(
                "username", "daria_prokhorenko",
                "password", "122m-23-2.25"
        );

        Response response = given().body(body).get("/user/login");
        response.then().
                statusCode(HttpStatus.SC_OK);
        RestAssured.requestSpecification.
                sessionId(response.jsonPath()
                        .get("message")
                        .toString()
                        .replaceAll("[^0-9]", ""));

    }

    @Test(dependsOnMethods = "verifyLoginAction")
    public void testNewPetCreation(){
        Map<String, ?> pet_body = Map.of(
                "id", pet_id,
                "name", name
        );
        given().body(pet_body)
                .post("/pet")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testNewPetCreation")
    public void testGetPetByid(){
        given().pathParam("petId", pet_id).
                then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", equalTo(name));
    }

    @Test(dependsOnMethods = "testNewPetCreation")
    public void testPetUpdate(){
        Map<String, ?> pet_body = Map.of(
                "id", pet_id +10,
                "name", name+"new name"
        );
        given().body(pet_body)
                .put("/pet")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}
