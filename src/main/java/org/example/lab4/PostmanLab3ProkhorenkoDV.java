package org.example.lab4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;



import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PostmanLab3ProkhorenkoDV {

    private static final String baseUrl = "https://647efdc7-c7e0-469b-802c-ab1c11804e41.mock.pstmn.io";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void getuser_positive(){
        Response response_positive = given().queryParam("id_student",25)
                .get(baseUrl+"/getmyname");
        response_positive.then().statusCode(HttpStatus.SC_OK);
        response_positive.then().body("name",equalTo("Prokhorenko Daria"));
        response_positive.then().body("group",equalTo("122m-23-2"));
        response_positive.then().body("id_student",equalTo("25"));
        response_positive.then().body("university",equalTo("NTU DP"));
    }

    @Test
    public void getuser_negative(){
        Response response_positive = given().queryParam("id_student",1)
                .get(baseUrl+"/getmyname");
        response_positive.then().statusCode(HttpStatus.SC_NOT_FOUND);
        response_positive.then().body("error",equalTo("Such user is not in our system"));
    }

    @Test
    public void updateuser_positive(){
        Response put_response = given()
                .queryParam("id_student","12")
                .queryParam("name","Olivia")
                .queryParam("university","DNU")
                .put(baseUrl+"/updateuser");
        put_response.then().statusCode(HttpStatus.SC_OK);
        put_response.then().body("name", equalTo("Olivia"));
        put_response.then().body("id_student", equalTo(12));
        put_response.then().body("university", equalTo("DNU"));
    }

    @Test
    public void updateuser_negative(){
        Response put_response = given()
                .queryParam("id_student","12")
                .queryParam("name",12)
                .queryParam("university","DNU")
                .put(baseUrl+"/updateuser");
        put_response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        put_response.then().body("error", equalTo("Invalid name provided. Name can not be : 12"));
    }

    @Test
    public void addnewuser_positive(){
        Response post_response = given()
                .queryParam("id_student","01")
                .queryParam("name","Maria Oleksandrivna")
                .queryParam("university","KPI")
                .post(baseUrl+"/addnewuser");
        post_response.then().statusCode(HttpStatus.SC_OK);
        post_response.then().body("msg", equalTo("New user was successfully added to the system."));
        post_response.then().body("id_student", equalTo("01"));
        post_response.then().body("name", equalTo("Maria Oleksandrivna"));
        post_response.then().body("university", equalTo("KPI"));
    }

    @Test
    public void addnewuser_negative(){
        Response post_response = given()
                .queryParam("id_student","01")
                .queryParam("name","Maria Oleksandrivna")
                .queryParam("university","")
                .post(baseUrl+"/addnewuser");
        post_response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        post_response.then().body("error", equalTo("University input field can not be empty."));
    }

    @Test
    public void deletestudent_positive(){
        Response delete_response = given()
                .queryParam("id_student","01")
                .delete(baseUrl+"/deleteuser");
        delete_response.then().statusCode(HttpStatus.SC_OK);
        delete_response.then().body("msg",equalTo("User was deleted successfully."));
    }

    @Test
    public void deletestudent_negative(){
        Response delete_response = given()
                .queryParam("id_student","0")
                .delete(baseUrl+"/deleteuser");
        delete_response.then().statusCode(HttpStatus.SC_NOT_FOUND);
        delete_response.then().body("msg",equalTo("No user was found with such id."));
    }


}
