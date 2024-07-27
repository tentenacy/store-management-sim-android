package com.tenutz.storemngsim.usecase

import com.tenutz.storemngsim.data.repository.user.UserRepository

class GetProfileUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.userDetails()
}