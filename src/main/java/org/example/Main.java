package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Config;
import org.example.models.Result;
import org.example.services.MatrixGenerator;
import org.example.services.RewardCalculator;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String configFilePath = args[1];
        int betAmount = Integer.parseInt(args[3]);

        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configFilePath), Config.class);
        config.validate();

        String[][] matrix = new MatrixGenerator(config).generateMatrix();

        Result result = new RewardCalculator(config).calculate(matrix, betAmount);

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
}