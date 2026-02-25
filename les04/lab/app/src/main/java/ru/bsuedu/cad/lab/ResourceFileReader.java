package ru.bsuedu.cad.lab;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component("rfr")
public class ResourceFileReader implements Reader {

    @Override
    public String read() {
        
        String information = "";
        StringBuilder builder = new StringBuilder();

        try (BufferedReader br = new BufferedReader
                (new InputStreamReader(
                                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("product.csv")),
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
