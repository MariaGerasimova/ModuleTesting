package ru.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests {
    // Student with id=1
    int idnew1=1;
    String name1newJson ="\"Peter\"";
    String name1new="Peter"; //новая переменная, т.к. сохраняется с кавычками. а без них не идет в json
    List <Integer> lstst1=List.of(2,2);
    Student st1_new=new Student (idnew1, name1new,lstst1);

    //Student with id=2
    int idnew2=2;
    String name2json="\"Kate\"";
    String name2="Kate";
    Student st2=new Student ();

    //Student with id=3
    int idnew3=3;
    Student st3=new Student ();
    //Student with id=4
    int idnew4=4;
    String name4newJson ="\"Alice\"";
    String name4="Alice"; //новая переменная, т.к. сохраняется с кавычками. а без них не идет в json
    List <Integer> lstst4=List.of(5,5,5,5,5);
    Student st4=new Student ();


    //код 200 и пустое тело, если студентов в базе нет - первым выполняется
    @Test
    @DisplayName("1!.get /topStudent - при пустой БД")
    @Order(1)
    public void GetTopStudentEmptyDB (){
        RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .body(Matchers.equalTo(""));
    }

    // создаем студента с id=1 (id=число, имя есть, массив оценок пустой) - второй тест
    @Test
    @DisplayName("2!.post /student/{id} - creation new student (id=new, name+, without marks")
    @Order(2)
    public void testCPostvalidNewID (){
        String jsonbody="{\"id\":"+ idnew1 +",\"name\": \"Vasia\"}";
        RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body(jsonbody)
                .when().post().then()
                .statusCode(201)
                .body(Matchers.equalTo(""));
    }
    //порядок важен
    //код 200 и пустое тело, если ни у кого из студентов нет оценок - третий тест (пока нет оценок в БД)
    @Test
    @DisplayName("3!.get /topStudent - студент есть, но без оценок")
    @Order(3)
    public void GetTopStudentWithoutAllMarksInDB (){
        RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .body(Matchers.equalTo(""));
    }

    // создаем студента c тем же ID, но другим именем + оценки (имя есть, массив оценок не пустой) - важно, чтобы шел после создания студента с тем же id
    @Test
    @DisplayName("4!.post /student/ - creation new student (id=the same, name+, marks+)")
    @Order(4)
    public void PostvalidSameID (){
        String str="{\"id\":"+ idnew1 + ",\"name\":"+ name1newJson +",\"marks\":[2,2]}";
        RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body (str)
                .when().post().then()
                .statusCode(201)
                .body(Matchers.equalTo(""));
    }
    //код 200 и возвращает студента, оценка у одного студента - порядок важен
    @Test
    @DisplayName("5!.get /topStudent - оценки у одного студента только")
    @Order(5)
    public void GetTopStudentOneStudentWithMarksInDB (){
       RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .contentType(ContentType.JSON);
       //save response_body
       List <Student> stApi=Arrays.asList(RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get()
                .thenReturn()
                .getBody().as(Student[].class));
       // Assert List.size (один элемент)
        Assertions.assertEquals(stApi.size(),1);
       // Assert value
        System.out.println(stApi.get(0));
        System.out.println(st1_new);
        Assertions.assertEquals(stApi.get(0).hashCode(),st1_new.hashCode());
    }
    // проверяем студента с id=1
    @Test
    @DisplayName("6!.get /student/{id} - valid id")
    @Order(6)
    public void GetvalidID (){
        RestAssured.given()
                .baseUri("http://localhost:8080/student/" + idnew1)
                .contentType(ContentType.JSON)
          .when().get().then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(idnew1))
                .body("name",Matchers.equalTo(name1new))
                .body("marks",Matchers.equalTo(lstst1));
    }

    // проверка возвращения топовых студентов с одинаковыми оценками
    @Test
    @DisplayName("7!.get /topstudent/ - several students with the same marks")
    @Order(7)
    public void GetTopStudentTwoStudentWithMarksInDB (){
        //create the seconde student as the id=1
        String json="{\"name\":"+ name1newJson +",\"marks\":[2,2]}";
        String s=RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body(json)
                .when().post().then()
                .statusCode(201)
                .extract().body().as(String.class);
        st3=new Student(Integer.parseInt(s),name1new,lstst1);
        // get request
        RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        //save response_body
        List <Student> stApi=Arrays.asList(RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get()
                .thenReturn()
                .getBody().as(Student[].class));
        // Assert List.size (один элемент)
        Assertions.assertEquals(stApi.size(),2);
        // Assert value
        Assertions.assertEquals(stApi.get(0).hashCode(),st1_new.hashCode());
        Assertions.assertEquals(stApi.get(1).hashCode(),st3.hashCode());
    }

    // создаем студента без id (имя есть, массив оценок пустой). возвращается ответ с id студента - порядок не важен
    @Test
    @DisplayName("8.post /student/ - creation new student (without id, name+, marks + - empty)")
    public void PostvalidWithoutID (){
        String json="{\"name\":"+ name2json +",\"marks\":[]}";
        String id =RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body(json)
                .when().post().then()
                .statusCode(201)
                .body(Matchers.notNullValue())
                .extract().body().as(String.class);
        st2=new Student(Integer.parseInt(id),name2);

    }
    //- порядок не важен
    @Test
    @DisplayName("9.post /student/ - создание студента, если отсутствует обязательное поле name")
    public void PostInvalidWithoutName (){
        String str="{\"id\":"+ idnew1 + ",\"marks\":[4,5]}";
        RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body (str)
                .when().post().then()
                .statusCode(400);
    }
    // порядок не важен
    @Test
    @DisplayName("10.get /student/{id} - invalid id")
    public void GetInvalidID (){
        int id=-1;
        RestAssured.given()
                .baseUri("http://localhost:8080/student/"+id)
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(404)
                .body(Matchers.equalTo(""));
    }
    @Test
    @DisplayName("11. delete/student/{id} - invalid id")
    public void DeleteInvalidID (){
        int id=-1;
        RestAssured.given()
                .baseUri("http://localhost:8080/student/"+id)
                .contentType(ContentType.JSON)
                .when().delete().then()
                .statusCode(404)
                .body(Matchers.equalTo(""));
    }
    // проверка возвращения топового студента с максимальными оценками - порядок не важен
    @Test
    @DisplayName("12.get /topstudent/ - top student with the max marks")
    public void GetTopStudentWithMaxMarksInDB (){
        //create the seconde studenat as the id=1
        String json="{\"name\":"+ name4newJson +",\"marks\":[5,5,5,5,5]}";
        String s=RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body(json)
                .when().post().then()
                .statusCode(201)
                .extract().body().as(String.class);
        st4=new Student(Integer.parseInt(s),name4,lstst4);
        // get request
        RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        //save response_body
        List <Student> stApi=Arrays.asList(RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get()
                .thenReturn()
                .getBody().as(Student[].class));
        // Assert List.size (один элемент)
        Assertions.assertEquals(stApi.size(),1);
        // Assert value
        System.out.println(stApi.get(0));
        System.out.println(st4);
        Assertions.assertEquals(stApi.get(0).hashCode(),st4.hashCode());
    }
    //порядок не важен
    @Test
    @DisplayName("13. delete/student/{id} - valid id")
    public void DeleteValidID (){
        String json="{\"name\":"+ name2json +",\"marks\":[]}";
        String id =RestAssured.given()
                .baseUri("http://localhost:8080/student/")
                .contentType(ContentType.JSON)
                .body(json)
                .when().post().then()
                .statusCode(201)
                .body(Matchers.notNullValue())
                .extract().body().as(String.class);
        RestAssured.given()
                .baseUri("http://localhost:8080/student/"+Integer.parseInt(id))
                .contentType(ContentType.JSON)
                .when().delete().then()
                .statusCode(200);
        // далее проверить, что студент реально удален
        RestAssured.given()
                .baseUri("http://localhost:8080/student/"+Integer.parseInt(id))
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(404);
    }

}

/*
    //код 200 и пустое тело, если ни у кого из студентов нет оценок
    @Test
    @DisplayName("6.get /topStudent - есть 2 студента, но без оценок")
    public void GetTopStudentWithoutAllMarksInDB2 (){
        RestAssured.given()
                .baseUri("http://localhost:8080/topStudent")
                .contentType(ContentType.JSON)
                .when().get().then()
                .statusCode(200)
                .body(Matchers.equalTo(""));
    }
 */