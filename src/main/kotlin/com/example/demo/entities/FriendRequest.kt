package com.example.demo.entities

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "friend_requests")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private var id: Long? = null

    @Column(name = "created_date", nullable = false)
    private var createdDate: Timestamp = TODO()

    @JoinColumn(name = "requester_id", nullable = false)
    @ManyToOne
    private var requester: User = User()

    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne
    private var receiver: User = User()

}