package surveys.dao;




import surveys.model.Members;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMembers {
    static final String MEMBER_FILE_PATH = "src/main/resources/surveys/Members.csv";


    public Map<Integer, Members> getMembers(){
        Map<Integer,Members> members = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(MEMBER_FILE_PATH))){
            bufferedReader.readLine();
            String line = bufferedReader.readLine();

            while(line != null) {
                Members member = getMemberFromText(line);
                members.put(member.memberId, member);

                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("This file is not found");
        } catch (IOException e) {
            System.out.println("Error reading file " + e.getMessage());
        }

        return members;
    }

    private Members getMemberFromText(String line){
        String[] splitLine = line.split(",");

        int memberId = Integer.parseInt(splitLine[0]);
        String fullName = splitLine[1];
        String emailAddress = splitLine[2];
        boolean isActive = splitLine[3].equals("1");

        return new Members(memberId,fullName,emailAddress,isActive);
    }
}
