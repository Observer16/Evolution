package token;

import utils.service.Constants;
import utils.service.Specifications;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class PostToken {
    public static String authToken;

    @Test  (priority=1) // Получение гостевого токена
    public void GetAGuestToken(){
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(204));
        String platform = "Android 12";
        String version = "2.1.0";
        String build = "Build/SP1A.210812.016";

        ValidatableResponse response = given()
                .when()
                .post("/auth/token")
                .then().log().all();

        // Извлечение значения токена из заголовков ответа
        String authToken = response.extract().header("x-auth-token");
        System.out.println(authToken);

        // Сохранение токена в файл
        saveTokenToFile(authToken);
    }

    // Метод для сохранения токена в файл
    private void saveTokenToFile(String token) {
        try {
            FileWriter fileWriter = new FileWriter(Constants.AUTH_TOKEN);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(token);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    @Test  (priority=2,dependsOnMethods = {"GetAGuestToken"}) // Проваленный тест Получение гостевого токена
    public void FailedGetAGuestToken(){
        API.Specifications.installSpecification(API.Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(401));
        // String platform = "Android 12";
        String version = "2.1.0";
        String build = "Build/SP1A.210812.016";

        ValidatableResponse response = given()
                .when()
                .post("/auth/token")
                .then().log().all();

        // Извлечение значения токена из заголовков ответа
        String authToken = response.extract().header("token");
        System.out.println(authToken);
    }
*/
}