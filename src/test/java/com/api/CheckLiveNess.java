package com.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CheckLiveNess {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String authkey= VcipLinkDetails.authKey;
    String vcipkey= VcipLinkDetails.vcipKey;

    // Method to encode image to Base64
    private String encodeImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream imageInputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = imageInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        // Optionally remove the prefix if necessary
        if (base64Image.startsWith("data:image/jpeg;base64,")) {
            base64Image = base64Image.substring("data:image/jpeg;base64,".length());
        }

        imageInputStream.close();
        outputStream.close();

        return base64Image;
    }

    public void checkLiveNess(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder().
                setBaseUri("https://dkyc.syntizen.com")
                .setBasePath("/api/dkyc")
                .addHeader("authkey",authkey)
                .addHeader("apikey","0");
        requestSpecification=requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification=responseSpecBuilder.build();

        // Define the image path
        String imagePath = "/Users/noushadpathan/Downloads/liveimage.jpg";

        try {
            // Encode the image to Base64
            String base64Image = encodeImageToBase64(imagePath);

            // Construct JSON payload using a Map
            Map<String, Object> jsonPayload = new HashMap<>();
            jsonPayload.put("vcipkey", vcipkey);
            jsonPayload.put("liveimage", base64Image);
            jsonPayload.put("rrn", "1");  // Assuming rrn should be a string

            // Convert Map to JSON string using ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(jsonPayload);

            given(requestSpecification)
                    .body(payload)
                    .when().post("/CheckImageLiveness")
                    .then().spec(responseSpecification)
                    .assertThat()
                    .statusCode(200);

            System.out.println("====== live ness done ========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
