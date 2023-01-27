package com.example.demo.services

import com.example.demo.entities.InterestEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.InterestsRepository
import org.springframework.stereotype.Service

@Service
class InterestsService(private val interestsRepository: InterestsRepository) {
    fun findAllByUser(userEntity: UserEntity): List<InterestEntity> {
        return interestsRepository.findAllByUser(userEntity)
    }

    fun findByKeyAndUser(key: String, userEntity: UserEntity): InterestEntity? {
        return interestsRepository.findByKeyAndUser(key, userEntity)
    }

    fun findFirstByUserOrderByLastCommentIdDesc(userEntity: UserEntity): InterestEntity? {
        return interestsRepository.findFirstByUserOrderByLastCommentIdDesc(userEntity)
    }

    fun calculateInterest(user: UserEntity, key: String, value: Double, commentId: Long) {
        var interest = findByKeyAndUser(key, user)

        if (interest == null) {
            // save new interest
            interest = InterestEntity(user, key, value)
//            interest.value = value
//            interest.key = key
//            interest.user = user
//            interest.count = 0
        }
        else {
            // add to the score
            interest.value = ( interest.value * interest.count + value ) / (interest.count + 1)
        }
        interest.count ++
        interest.lastCommentId = commentId
        interestsRepository.save(interest)
    }

}