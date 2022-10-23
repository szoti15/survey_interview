package surveys

import spock.lang.Specification
import surveys.dao.GetMembers


class GetMembersTest extends Specification{


    def "getMemberFromText" (){
        setup:
            GetMembers getMembers = new GetMembers()

        when:
            def member = getMembers.getMemberFromText(line)

        then:
            checkMember(member, id, name, email, active)

        where:
            line                                                || id | name            | email                         | active
            "1,Malissa Arn,MalissaArn0202@gmail.com,1"          || 1  | "Malissa Arn"   | "MalissaArn0202@gmail.com"    | true
            "17,Albina Holaway,AlbinaHolaway6169@gmail.com,0"   || 17 | "Albina Holaway"| "AlbinaHolaway6169@gmail.com" | false
    }

    def "getMemberFromText - exception" (){
        setup:
            GetMembers getMembers = new GetMembers()

        when:
            getMembers.getMemberFromText("")

        then:
            thrown(NumberFormatException)
    }

    def checkMember(def receivedMember, def id, def name, def email, def active){
        assert receivedMember.memberId == id
        assert receivedMember.fullName == name
        assert receivedMember.emailAddress == email
        assert receivedMember.isActive == active

        return 1
    }

}