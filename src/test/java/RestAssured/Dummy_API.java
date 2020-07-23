package RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.CreateEmployee;
import pojo.getEmployee;

public class Dummy_API {

	public static void main(String[] args) {
		
		//Creation of Employee using Serialization
		RestAssured.baseURI="http://dummy.restapiexample.com";
		CreateEmployee ce=new CreateEmployee();
		ce.setName("shluj");
		ce.setSalary("81060");
		ce.setAge("52");
		String CreateEmployee=given().log().all().header("Content-Type","application/json").header("Accept","application/json")
				.body(ce).when()
				.post("/api/v1/create").then().log().all().assertThat().statusCode(200).extract().response().asString();

		System.out.println(CreateEmployee);
		JsonPath js=new JsonPath(CreateEmployee);
		String ID=js.getString("data.id");
		System.out.println(ID);

		//Get Employee Using Deserialization 
			    getEmployee ge=	given().pathParam("id", ID).expect().defaultParser(Parser.JSON)
					.when()
					.get("api/v1/employee/{id}").as(getEmployee.class);
		
		  System.out.println("get employee:"+ge.getEmployee_name());

		
		//Update EMployee
		String newname="sainathkar";
		given().log().all().pathParam("id", ID)
		.header("Content-Type","application/json").header("Accept","application/json")
		.body("{\r\n" + 
				"    \"name\":\""+newname+"\"\",\r\n" + 
				"    \"age\":\"13\"\r\n" + 
				"}").when()
		.put("api/v1/update/{id}").then().log().all().assertThat().statusCode(200);



	}

}
