package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Image
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.ImageData
import com.empoderar.picar.model.persistent.database.interfaces.ImageDataDao
import javax.inject.Inject

class InsertImagesUseCase @Inject constructor(private val imageDataDao:
                                              ImageDataDao):
        UseCase<Boolean, List<Image>>() {

    override suspend fun run(params: List<Image>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    imageDataDao.insertImages(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }


    private fun transformList(list: List<Image>): List<ImageData>{
        val images = ArrayList<ImageData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val image = ImageData(list[i].id, list[i].form,
                        list[i].base64, list[i].latitude,
                        list[i].longitude, list[i].date)
                images.add(image)
            }
        }
        return images.toList()
    }

}