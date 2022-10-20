package com.itgnomes.punditzservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.itgnomes.punditzservice.entity.Punditz
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PunditzRepository: JpaRepository<Punditz, Long> {
    fun findAllByType(type: String): List<Punditz>

    @Query("SELECT p FROM PUNDITZ p WHERE p.type = ?1 AND JSON_VALUE(p.jsonData, '\$.userName') = ?2")
    fun findAllByTypeAndUserName(type: String, userName: String): List<Punditz>

    @Query("SELECT p FROM PUNDITZ p WHERE p.type = ?1 AND JSON_VALUE(p.jsonData, '\$.cycleNumber') = ?2")
    fun findByTypeAndCycleNumber(type: String, cycleNumber: Int): Punditz

    @Query("SELECT p FROM PUNDITZ p WHERE p.type = ?1 AND JSON_VALUE(p.jsonData, '\$.userName') = ?2 AND JSON_VALUE(p.jsonData, '\$.matchId') = ?3")
    fun findByTypeAndUserNameAndMatchId(type: String, userName: String, matchId: Int): Punditz

    @Query("SELECT p FROM PUNDITZ p WHERE p.type = ?1 AND JSON_VALUE(p.jsonData, '\$.userName') = ?2 AND JSON_VALUE(p.jsonData, '\$.cycleNumber') = ?3")
    fun findAllByTypeAndUserNameAndCycleNumber(type: String, userName: String, cycleNumber: Int): List<Punditz>
}