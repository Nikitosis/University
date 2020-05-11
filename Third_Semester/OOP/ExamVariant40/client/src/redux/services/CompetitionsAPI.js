import axios from "axios";

class CompetitionsAPI{
    getCompetitions(startDate,endDate,sportsFields) {

        var searchParams = new URLSearchParams();

        if(startDate != null) {
            searchParams.append("startDate", startDate);
        }

        if(endDate != null) {
            searchParams.append("endDate", endDate);
        }

        if(sportsFields != null) {
            sportsFields.forEach(field => searchParams.append("sportsFields", field));
        }
        debugger;
        return axios.get(`http://exambackend.us-east-2.elasticbeanstalk.com/competition`,{
            params:searchParams
        })
    }
    getCompetitionInfo(competitionId) {
        return axios.get(`http://exambackend.us-east-2.elasticbeanstalk.com/competition/${competitionId}`)
    }
}

export default new CompetitionsAPI();
