package com.itgnomes.punditzservice.service

import com.itgnomes.punditzservice.model.Cycle
import com.itgnomes.punditzservice.model.Match
import com.itgnomes.punditzservice.model.Season
import com.itgnomes.punditzservice.model.Team
import com.itgnomes.punditzservice.util.CycleUtil
import com.itgnomes.punditzservice.util.PunditzUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class FootBallApiService {
    companion object {
        lateinit var MATCHES: MutableList<Match>
        lateinit var MATCH_TIMESTAMP: Calendar

        lateinit var TEAMS: MutableList<Team>
        lateinit var TEAM_TIMESTAMP: Calendar

        lateinit var CYCLES: MutableList<Cycle>
        lateinit var CYCLE_TIMESTAMP: Calendar

        fun getTime(dateOffset: Int): Calendar {
            val now = Calendar.getInstance()
            now.set(Calendar.DATE, dateOffset)
            now.time
            return now
        }
    }

    fun getMatches(leagueId: Int = 2021): List<Match> {
        val now = getTime(-1)
        if (MATCHES == null || now.after(MATCH_TIMESTAMP)) {
            val url = ApiService.BASEURL + leagueId + ApiService.MATCHES
            val resp = ApiService.invoke(url)
            MATCHES = PunditzUtil.parseMatches(resp)
            MATCH_TIMESTAMP = Calendar.getInstance()
        }
        return MATCHES
    }

    fun getMatch(matchId: Int): Match {
        val match = getMatches().filter{it.id == matchId}
        return match[0]
    }

    fun getTeams(leagueId: Int = 2021): List<Team> {
        val now = getTime(-1)
        if (TEAMS == null || now.after(TEAM_TIMESTAMP)) {
            val url = ApiService.BASEURL + leagueId + ApiService.TEAMS
            val resp = ApiService.invoke(url)
            TEAMS = PunditzUtil.parseTeams(resp)
        }
        return TEAMS
    }

    fun getTeam(teamId: Int?, teamName: String?): Team {
        val team = getTeams().filter{it.id == teamId || it.name == teamName}
        return team[0]
    }

    fun getCycles(leagueId: Int = 2021): MutableMap<Int, Cycle> {
        val now = getTime(-1)
        val cycleMap: MutableMap<Int, Cycle>
        if (CYCLES == null || now.after(CYCLE_TIMESTAMP)) {
            val matches = getMatches(leagueId)
            cycleMap = CycleUtil.calculateCycles(matches, leagueId)
            CYCLES = cycleMap.values.toMutableList()
        } else {
            cycleMap = CycleUtil.calculateCycles(MATCHES, leagueId)
        }
        return cycleMap
    }

    fun getCycle(cycleNumber: Int): Cycle? {
        return getCycles()[cycleNumber]
    }
}