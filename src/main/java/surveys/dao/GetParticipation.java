package surveys.dao;


import surveys.model.Participation;

import java.util.ArrayList;
import java.util.List;

public class GetParticipation {

    GeneralFileReader reader = new GeneralFileReader("src/main/resources/surveys/Participation.csv");

    public List<Participation> getParticipation(){
        List<Participation> participations = new ArrayList<>();

        List<String> lines = reader.readLines();

        int counter = 0;
        for(String line : lines) {
            if(counter != 0){
                 participations.add(getParticipationFromText(line));
            }

            counter++;
        }

        return participations;
    }

    private Participation getParticipationFromText(String line){
        String[] splitLine = line.split(",");

        int memberId = Integer.parseInt(splitLine[0]);
        int surveyId = Integer.parseInt(splitLine[1]);
        int status = Integer.parseInt(splitLine[2]);
        int length = 0;
        if(splitLine.length == 4){
            length = Integer.parseInt(splitLine[3]);
        }

        return new Participation(memberId,surveyId,status,length);
    }
}
