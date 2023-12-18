package ru.mock.student;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.innotech.Student;
import ru.innotech.StudentRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestsMock {
    @Mock
    private HttpClient mockHttpClient;
    private HttpGet mockHttpGet;
    private HttpResponse mockHttpResponse;
    @Test
    public void addGradeCorrect () throws IOException {
        StudentMock stud = new StudentMock("vasia");
        mockHttpResponse.setReasonPhrase("true");
        Mockito.when(mockHttpClient.execute(mockHttpGet)).thenReturn(mockHttpResponse);
        //stud.addGrade(5);
        Assertions.assertEquals(5,stud.getGrades());
    }
    @Test
    public void addGradeInCorrect () throws IOException {
        StudentMock stud = new StudentMock("vasia");
        Boolean a=true;
        mockHttpResponse.setReasonPhrase("false");
        Mockito.when(mockHttpClient.execute(mockHttpGet)).thenReturn(mockHttpResponse);
        //Assertions.assertThrows(IllegalArgumentException.class, () ->stud.addGrade(1));
    }
}


/*private HttpEntity mockHttpEntity;
    private InputStream mockInputStream;
    private InputStreamReader mockInputStreamReader;
    private BufferedReader mockBufferedReader;*/