package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;




import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

//@Component("rfr")
public class ResourceFileReader implements Reader {

//    @Value("#{property.name}")
//    private String fileName;

    private String fileName;

    public ResourceFileReader(String fileName) {
        this.fileName = fileName;
    }

    @PostConstruct
    public void init(){
        System.out.println("Дата и время инициализации бина 'ResourceFileReader'" +
                new Date());
    }

    @Override
    public String read() {

        
        String information = "";
        StringBuilder builder = new StringBuilder();

        try (BufferedReader br = new BufferedReader
                (new InputStreamReader(
                                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)),
                                StandardCharsets.UTF_8
                        ))) {
            

            while((information = br.readLine())!=null){

                builder.append(information).append("\n");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка при открытии файла!");
        } catch (IOException e) {
            System.out.println("Произошла неизвестная ошибка" + e.getMessage());
        }

        return builder.toString();
    }
}
