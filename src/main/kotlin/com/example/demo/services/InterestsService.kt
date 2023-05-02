package com.example.demo.services

import com.example.demo.entities.InterestEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.InterestsRepository
import com.example.demo.types.InterestKeyEntityType
import org.springframework.stereotype.Service

@Service
class InterestsService(private val interestsRepository: InterestsRepository) {
    fun findAllByUser(userEntity: UserEntity): List<InterestEntity> {
        return interestsRepository.findAllByUser(userEntity)
    }

    fun findByKeyAndEntityAndUser(key: String, keyType: InterestKeyEntityType, userEntity: UserEntity): InterestEntity? {
        return interestsRepository.findByKeyAndEntityAndUser(key, keyType, userEntity)
    }

    fun findFirstByUserOrderByLastCommentIdDesc(userEntity: UserEntity): InterestEntity? {
        return interestsRepository.findFirstByUserOrderByLastCommentIdDesc(userEntity)
    }

    fun calculateInterest(user: UserEntity, key: String, keyType: InterestKeyEntityType, value: Double, commentId: Long) {
        var interest = findByKeyAndEntityAndUser(key, keyType, user)

        if (interest == null) {
            // save new interest
            interest = InterestEntity(user, key, keyType, value)
        }
        else {
            // add to the score
            interest.value = ( interest.value * interest.count + value ) / (interest.count + 1)
        }
        interest.count ++
        interest.lastCommentId = commentId
        interestsRepository.save(interest)
    }

    fun findAllByEntityInAndUserUsernameOrderByValueDesc(entities: List<InterestKeyEntityType>,
                                                       username: String): List<InterestEntity> {
        return interestsRepository.findAllByEntityInAndUserUsernameOrderByValueDesc(entities, username)
    }

    fun decreaseInterest(interest: InterestEntity) {
        interest.value -= 0.00000000001
        interestsRepository.save(interest)
    }

}