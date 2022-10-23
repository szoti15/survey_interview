package surveys.dao;


import surveys.model.Participation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetParticipation {

    GeneralFileReader reader = new GeneralFileReader("src/main/resources/surveys/Participation.csv");

    public Map<Integer,List<Participation>> getParticipation(){
        Map<Integer,List<Participation>> participations = new HashMap<>();

        List<String> lines = reader.readLines();

        int counter = 0;
        for(String line : lines) {

            String[] splitLine = line.split(",");

            if(counter != 0){

                 int memberId = Integer.parseInt(splitLine[0]);
                 int surveyId = Integer.parseInt(splitLine[1]);
                 int status = Integer.parseInt(splitLine[2]);
                 int length = 0;
                 if(splitLine.length == 4){
                     length = Integer.parseInt(splitLine[3]);
                 }

                 List<Participation> pList = participations.computeIfAbsent(memberId,k-> new ArrayList<>());
                 Participation p = new Participation(memberId,surveyId,status,length);
                 pList.add(p);
                 participations.put(memberId,pList);
            }
            counter++;
        }

        return participations;
    }
}
