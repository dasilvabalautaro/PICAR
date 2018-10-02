package com.empoderar.picar.domain.interactor

import com.empoderar.picar.model.persistent.network.repository.UsersRepository
import com.empoderar.picar.presentation.data.User
import javax.inject.Inject


class DownUsersUseCase @Inject constructor(private val usersRepository:
                                          UsersRepository):
        UseCase<List<User>, UseCase.None>() {
    override suspend fun run(params: None) = usersRepository.users()

}