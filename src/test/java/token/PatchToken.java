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

    @Test (priority=1)// Обновление токена
    public void PatchAGuestToken() {
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(204));
        String platform = "Android 12";
        String version = "2.1.0";
        String build = "Build/SP1A.210812.016";

        // Создание заголовков с использованием HeaderUtils
        Headers headers = HeaderUtils.createHeaders(authToken);

        ValidatableResponse response = given()
                .headers(headers)
                .body("{\"platform\":\"" + platform + "\",\"version\":\"" + version + "\",\"build\":\"" + build + "\"}")
                .when()
                .patch("/auth/token")
                .then()
                .header("Content-Security-Policy","upgrade-insecure-requests")
                .log().all();

        // Извлечение значения токена из заголовков ответа
        String authToken = response.extract().header("x-auth-token");

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

    @Test (priority = 2) // Проваленный тест Получение гостевого токена без передачи платформы
    public void FailedPatchAGuestToken() {
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(401));

        String version = "2.1.0";
        String build = "Build/SP1A.210812.016";

        ValidatableResponse response = given()
                .body("{\"version\":\"" + version + "\",\"build\":\"" + build + "\"}")
                .when()
                .patch("/auth/token")
                .then().log().all();

    }

    @Test (priority = 3) // Тест получение гостевого токена с передачей только платформы
    public void GetAGuestTokenWithPlatform() {
        Specifications.installSpecification(Specifications.requestSpec(Constants.BASE_URL), Specifications.responseSpecUnique(204));
        String platform = "Android 12";

        ValidatableResponse response = given()
                .body("{\"platform\":\"" + platform + "\"}")
                .when()
                .patch("/auth/token")
                .then().log().all();

    }
}