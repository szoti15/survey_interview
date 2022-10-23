package surveys.dao;


import surveys.model.Statuses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetStatus {

    GeneralFileReader reader = new GeneralFileReader("src/main/resources/surveys/Statuses.csv");

    public  Map<Integer, Statuses> getStatus(){
        Map<Integer,Statuses> statuses = new HashMap<>();

        List<String> lines = reader.readLines();

        int counter = 0;
        for(String line : lines) {

            String[] splitLine = line.split(",");

            if(counter != 0){
                 int statusId = Integer.parseInt(splitLine[0]);
                 String name = splitLine[1];

                Statuses st = new Statuses(statusId,name);
                statuses.put(st.statusId,st);
            }

            counter++;
        }

        return statuses;
    }
}
