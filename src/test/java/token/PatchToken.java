package token;

import utils.service.Constants;
import utils.service.HeaderUtils;
import utils.service.Specifications;
import utils.service.TokenManager;
import io.restassured.http.Headers;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class PatchToken {
    String authToken;

    @BeforeMethod // Получение токена из файла перед каждым тестом
    public void setup() {
        authToken = TokenManager.readTokenFromFile();
    }

    @Test // Обновление токена
    public void PatchAGuestToken() {
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(204));
        String platform = "Android 12";
        String version = "2.1.0";
        String build = "Build/SP1A.210812.016";

        // Создание заголовков с использованием HeaderUtils
        Headers headers = HeaderUtils.createHeaders(authToken);

        ValidatableResponse response = given()
                .headers(headers)
                .when()
                .patch("/auth/token")
                .then()
                .header("Content-Type","application/json; charset=utf-8")
                .header("Cache-Control", "no-store, no-cache, must-revalidate")
                .log().all();

        // Извлечение значения токена из заголовков ответа
        String authToken = response.extract().header("token");
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
}