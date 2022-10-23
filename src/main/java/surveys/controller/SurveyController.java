package surveys.controller;


/*

Adott egy 300 embert tartalmazó lista, akik kérdőíveket szoktak kitölteni.
A résztvevők a kérdőívek kitöltéséért pontokat kapnak, amit beválthatnak ajándék és használati tárgyakra.
A kitöltők között vannak aktív és passzív tagok.
Aktuálisan csak az aktív tagokat lehet megkérdezni, bár korábban a passzív tagok is részt vehettek  kutatásokban.

Adott ember, adott kérdőívre vonatkozó státusza a következők valamelyike lehet:
- (1) Not asked - Nem vett részt a felmérésben (nem kérték fel)
- (2) Rejected - Nem vett részt a felmérésben (megkérték, de nem akart)
- (3) Filtered - A szűrő kérdések alapján nem felelt meg
- (4) Completed - Kitöltötte a kérdőívet
(3) és (4) esetén kaphatnak pontot a résztvevők.



1. Olvassuk be a szükséges adatokat a Members, Surveys, Statuses és Participation CSV fájlokból az általunk létrehozott megfelelő adatszerkezetekbe.

2. Implementáljuk az alábbi szolgáltatásokat:
	a) Adott kérdőívet kitöltők (Completed státuszúak) listája
	b) Adott személy által kitöltött (Completed státuszú) kérdőívek listája
	c) Adott személy által eddig gyűjtött pontok lekérdezése
	d) Adott kérdőívre meghívható (Még nem vett részt ebben a felmérésben és státusza aktív) személyek listázása

Bónusz:
	e) Összes kérdőív listázása, statisztikákkal:
		Kérdőív azonosító, Kérdőív neve, Kitöltők száma, Kiszűrtek száma, Kérdőívet elutasítók száma, Átlagos kitöltési hossz

Megjegyzés: Minden számítást Java-ban végezzünk el!

*/

import surveys.dao.*;
import surveys.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SurveyController {
    private GetParticipation getParticipation = new GetParticipation();
    private GetStatus getStatus = new GetStatus();
    private GetMembers getMembers = new GetMembers();
    private GetSurveys getSurveys = new GetSurveys();

    List<Participation> participations;
    Map<Integer, Members> members;
    Map<Integer, Statuses> statuses;
    Map<Integer, Surveys> surveys;

    public List<Members> getMembersWithStatus(int surveyId, String statusName){
        Statuses status = getStatusByName(statusName);

        if(status == null) {
            System.out.println("Nincs ilyen status " + statusName);
            return Collections.emptyList();
        }

        List<Participation> participations = getParticipations();
        Map<Integer, Members> storedMembers = getMembers();

        return participations.stream()
                .filter(p -> p.getSurveyId() == surveyId)
                .filter(p -> p.getStatusId() == status.statusId)
                .map(p -> storedMembers.get(p.getMemberId()))
                .collect(Collectors.toList());
    }

    // b) Adott személy által kitöltött (Completed státuszú) kérdőívek listája
    public List<Surveys> getSurveysByMemberAndStatus(int memberId, String statusName) {
        Statuses status = getStatusByName(statusName);

        if(status == null) {
            System.out.println("Nincs ilyen status " + statusName);
            return Collections.emptyList();
        }

        Map<Integer, Surveys> surveys = getSurveys();

        return getParticipations().stream()
                .filter(p -> p.getMemberId() == memberId)
                .filter(p -> p.getStatusId() == status.statusId)
                .map( p -> surveys.get(p.getSurveyId()))
                .collect(Collectors.toList());
    }


    private List<Participation> getParticipations(){
        if(participations == null) {
            participations = getParticipation.getParticipation();
        }

        return participations;
    }

    private Statuses getStatusByName(String name){
         return getStatuses().values().stream()
         .filter(s -> s.name.equals(name))
         .findFirst()
         .orElse(null);
    }

    private Map<Integer, Statuses> getStatuses(){
        if(statuses == null){
            statuses = getStatus.getStatus();
        }

        return statuses;
    }

    private Map<Integer, Members> getMembers(){
        if(members == null){
            members = getMembers.getMembers();
        }

        return members;
    }

    private Map<Integer, Surveys> getSurveys(){
        if(surveys == null){
            surveys = getSurveys.getSurveys();
        }

        return surveys;
    }
}
