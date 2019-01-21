package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import kotlinx.coroutines.*


//Execute function async and return value or fail.

abstract class UseCase<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params,
                       onResult: (Either<Failure, Type>) -> Unit = {}){

       val job = GlobalScope.async { run(params) }
        GlobalScope.launch(Dispatchers.Main)  { onResult(job.await()) }
    }


    class None
}
