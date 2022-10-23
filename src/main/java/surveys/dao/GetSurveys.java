package surveys.dao;



import surveys.model.Surveys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSurveys {

    GeneralFileReader reader = new GeneralFileReader("src/main/resources/surveys/Surveys.csv");

    public Map<Integer,Surveys> getSurveys(){
        Map<Integer, Surveys> surveys = new HashMap<>();

        List<String> lines = reader.readLines();

        int counter = 0;
        for(String line : lines) {

            String[] splitLine = line.split(",");

            if(counter != 0){

                 int surveyId = Integer.parseInt(splitLine[0]);
                 String Name = splitLine[1];
                 int expectedCompletes = Integer.parseInt(splitLine[2]);
                 int completionPoints = Integer.parseInt(splitLine[3]);
                 int filteredPoints =Integer.parseInt(splitLine[4]);

                Surveys sr = new Surveys(surveyId,Name,expectedCompletes,completionPoints,filteredPoints);
                surveys.put(surveyId,sr);
            }

            counter++;
        }

        return surveys;
    }
}
