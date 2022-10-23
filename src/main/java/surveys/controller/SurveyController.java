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

import java.util.*;
import java.util.stream.Collectors;

public class SurveyController {
    private static final String COMPLETED = "Completed";
    private static final String FILTERED = "Filtered";
    private static final String REJECTED = "Rejected";

    private GetParticipation getParticipation = new GetParticipation();
    private GetStatus getStatus = new GetStatus();
    private GetMembers getMembers = new GetMembers();
    private GetSurveys getSurveys = new GetSurveys();

    List<Participation> participations;
    Map<Integer, List<Participation>> participationsByMember;
    Map<Integer, List<Participation>> participationsBySurveyId;
    Map<Integer, Members> members;
    Map<Integer, Statuses> statuses;
    Map<Integer, Surveys> surveys;

    //a) Adott kérdőívet kitöltők (Completed státuszúak) listája
    public List<Members> getMembersWithStatus(int surveyId, String statusName){
        Statuses status = getStatusByName(statusName);

        if(status == null) {
            System.out.println("Nincs ilyen status " + statusName);
            return Collections.emptyList();
        }

        Map<Integer, Members> storedMembers = getMembers();

        return getParticipationsBySurveyId(surveyId).stream()
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

        return getParticipationsByMember(memberId).stream()
                .filter(p -> p.getStatusId() == status.statusId)
                .map( p -> surveys.get(p.getSurveyId()))
                .collect(Collectors.toList());
    }

    //c) Adott személy által eddig gyűjtött pontok lekérdezése
    public Points getPointsOfMember(int memberId) {
        Statuses completed = getStatusByName(COMPLETED);
        Statuses filtered = getStatusByName(FILTERED);

        List<Participation> participations = getParticipationsByMember(memberId).stream()
                .filter(p -> p.getStatusId() == completed.statusId || p.getStatusId() == filtered.statusId)
                .collect(Collectors.toList());

        Points point = new Points();
        Map<Integer, Surveys> surveys = getSurveys();

        for(Participation p : participations) {
            Surveys survey = surveys.get(p.getSurveyId());

            if(p.getStatusId() == completed.statusId){
                point.completionPoints += survey.completionPoints;
            } else if(p.getStatusId() == filtered.statusId){
                point.filteredPoints += survey.filteredPoints;
            }
        }

        return point;
    }

    //d) Adott kérdőívre meghívható (Még nem vett részt ebben a felmérésben és státusza aktív) személyek listázása
    public List<Members> getNonParticipiants(int surveyId){

        List<Integer> memberIds = getParticipationsBySurveyId(surveyId)
                .stream()
                .map(Participation::getMemberId)
                .collect(Collectors.toList());

        return getMembers().values().stream()
                .filter(m -> !memberIds.contains(m.memberId))
                .filter(m -> m.isActive)
                .collect(Collectors.toList());
    }

    // e) Összes kérdőív listázása, statisztikákkal:
    // Kérdőív azonosító, Kérdőív neve, Kitöltők száma, Kiszűrtek száma, Kérdőívet elutasítók száma, Átlagos kitöltési hossz

    // surveys (id, name) -> az adott survey kellene nekem a participations (kitolto szam, kiszurtek szama, elutasitok szama, avg hossz)
    public List<SurveyStatistics> getSurveyStatistics() {
        List<SurveyStatistics> statistics = new ArrayList<>();

        Statuses completed = getStatusByName(COMPLETED);
        Statuses filtered = getStatusByName(FILTERED);
        Statuses rejected = getStatusByName(REJECTED);

        for(Surveys survey : getSurveys().values()){
            SurveyStatistics statistic = new SurveyStatistics();

            statistic.surveyId = survey.surveyId;
            statistic.surveyName = survey.Name;

            List<Participation> participations = getParticipationsBySurveyId(survey.surveyId);

            int sumLength = 0;
            for(Participation p : participations){

               if(p.getStatusId() == completed.statusId) {
                   sumLength += p.getLength();
                   statistic.numbOfCompleted += 1;
               } else if (p.getStatusId() == filtered.statusId){
                   statistic.numbOfFiltered += 1;
               } else if (p.getStatusId() == rejected.statusId){
                   statistic.numbOfRejected += 1;
               }
            }

            statistic.avgLength = sumLength / statistic.numbOfCompleted;
            statistics.add(statistic);
        }

        return statistics;
    }

    private void fillParticipations(){
        if(participations == null) {
            participations = getParticipation.getParticipation();

            participationsByMember = new HashMap<>();
            participationsBySurveyId = new HashMap<>();
            for(Participation p : participations) {
                participationsByMember
                        .computeIfAbsent(p.getMemberId(), k -> new ArrayList<>())
                        .add(p);

                participationsBySurveyId
                        .computeIfAbsent(p.getSurveyId(), k -> new ArrayList<>())
                        .add(p);
            }
        }
    }

    private List<Participation> getParticipationsByMember(int memberId){
        if(participationsByMember == null) {
            fillParticipations();
        }

        return participationsByMember.get(memberId);
    }

    private List<Participation> getParticipationsBySurveyId(int surveyId){
        if(participationsBySurveyId == null) {
            fillParticipations();
        }

        return participationsBySurveyId.get(surveyId);
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
