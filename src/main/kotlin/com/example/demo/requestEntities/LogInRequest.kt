package com.example.demo.requestEntities

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class LogInRequest {
    var email: String = ""

    var username: String = ""

    var password: String = ""
}