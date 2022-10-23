package surveys

import spock.lang.Specification
import surveys.controller.SurveyController
import surveys.dao.GetStatus
import surveys.model.Statuses

class SurveyControllerTest extends Specification{

//
//    def "getMembersWithStatus" () {
//        setup:
//            SurveyController surveyController = new SurveyController()
//
//
//        when:
//
//
//
//        then:
//
//
//
//
//
//    }
//


    def "getStatuses" () {
        setup:
            SurveyController surveyController = new SurveyController()
            surveyController.getStatus = Mock(GetStatus)
            surveyController.statuses = savedStatuses

        when:
            def result = surveyController.getStatuses()


        then:
            (countOfCall) * surveyController.getStatus.getStatus() >> statusesFromFile

            assert result.size() == returnedValue.size()
            assert result.get(1).name == returnedValue.get(1).name

        where:
            savedStatuses                   | statusesFromFile                    | returnedValue                        | countOfCall
            [1 : new Statuses(2, "kabbe")]  | [1 : new Statuses(3, "asdsadsada")] | [1 : new Statuses(2, "kabbe")]       | 0
            null                            | [1 : new Statuses(3, "asdsadsada")] | [1 : new Statuses(2, "asdsadsada")]  | 1
    }

}
