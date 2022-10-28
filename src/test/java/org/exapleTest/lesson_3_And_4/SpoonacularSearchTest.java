package org.exapleTest.lesson_3_And_4;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class SpoonacularSearchTest extends AbstractTest {

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void separation() {
        System.out.println("$-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-$");
    }

    @Test
    void getCarbsTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .queryParam("minCarbs", "20")
                .queryParam("maxCarbs", "25")
                .when()
                .get(getBaseUrl())
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getAddRecipeNutritionTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeNutrition", "false")
                .when()
                .get(getBaseUrl())
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getQueryTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "borsch")
                .when()
                .get(getBaseUrl())
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getIncludeIngredientsTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeIngredients", "turmeric,tomato")
                .when()
                .get(getBaseUrl())
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getSearchPastaTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("maxFat", "25")
                .queryParam("number", "2")
                .when()
                .get(getBaseUrl())
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void postShoppingList() {
        String id = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", getBaseHash())
                .pathParams("username", "alexv0")
                .body("{\n"
                        + " \"item\": \"1 package baking powder\",\n"
                        + " \"aisle\": \"Baking\",\n"
                        + " \"parse\": true\n"
                        + "}")
                .when()
                .post(baseShoppingUrl() + "items")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", getBaseHash())
                .pathParams("username", "alexv0")
                .delete(baseShoppingUrl()+"items/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void getShoppingList() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", getBaseHash())
                .pathParams("username", "alexv0")
                .when()
                .get(baseShoppingUrl())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
