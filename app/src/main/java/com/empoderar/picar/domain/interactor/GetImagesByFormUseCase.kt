package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Image
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.ImageDataDao
import javax.inject.Inject

class GetImagesByFormUseCase @Inject constructor(private val imageDataDao:
                                                 ImageDataDao):
        UseCase<List<Image>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<Image>> {
        return try {
            val listData = imageDataDao.findImagesByForm(params)

            val list = listData.map { Image(it.id, it.form,
                    it.project, it.base64, it.latitude, it.longitude, it.date) }

            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }


        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}