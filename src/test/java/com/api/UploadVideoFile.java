package com.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadVideoFile {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @Test
    public void uploadVideoFile(){

        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder().
                setBaseUri("https://dkyc.syntizen.com")
                .setBasePath("/api/dkyc")
                .addHeader("authkey",VcipLinkDetails.authKey)
                .addHeader("apikey","0")
                .log(LogDetail.ALL);
        requestSpecification=requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification=responseSpecBuilder.build();

        String filePath = "/Users/noushadpathan/Downloads/surendharDetails.mp4";
        File file = new File(filePath);

        given(requestSpecification)
                .param("vcipkey",VcipLinkDetails.vcipKey)
                .param("ftype",".mp4")
                .multiPart("file", file)
                .when().post("/UploadVCIPRecordedVideo")
                .then().spec(responseSpecification)
                .assertThat()
                .statusCode(200);
        System.out.println("======= upload video =========");
    }
}
