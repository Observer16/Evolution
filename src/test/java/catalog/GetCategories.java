package catalog;

import utils.pojos.catalog.CatalogCategories;
import utils.pojos.catalog.CatalogRoot;
import utils.service.Constants;
import utils.service.HeaderUtils;
import utils.service.Specifications;
import utils.service.TokenManager;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetCategories {
    private String authToken;

    @BeforeMethod // Получение токена из файла перед каждым тестом
    public void setup() {
        authToken = TokenManager.readTokenFromFile();
    }

    @Test // Получение каталога товаров, а точнее иерархии категорий
    public void Get_Categories() {
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(200));

        // Создание заголовков с использованием HeaderUtils
        Headers headers = HeaderUtils.createHeaders(authToken);

        ValidatableResponse response = given()
                .headers(headers)
                .when()
                    .get("/categories")
                .then().assertThat();
// Проверить позже                .body(JsonSchemaValidator.matchesJsonSchema(new File(Constants.categories_schema)));
    }

    @Test // Проверка присутствия картинки категории
    public void PresenceCategoryPicture() {
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(200));
        // Создание заголовков с использованием HeaderUtils
        Headers headers = HeaderUtils.createHeaders(authToken);

        Response response = given()
                .headers(headers)
                .when()
                .get("/categories")
                .then().assertThat()
                .extract().response();

        CatalogRoot catalogResponse = response.as(CatalogRoot.class);
        List<CatalogCategories> categories = catalogResponse.getData().getCategories();

        boolean allCategoriesHavePictures = categories.stream()
                .allMatch(x -> x.getImage() != null && x.getImage().getUrl() != null && x.getImage().getUrl().endsWith(".jpg"));

        Assert.assertTrue(allCategoriesHavePictures, "Не все категории содержат изображения с расширением .jpg");
    }
}