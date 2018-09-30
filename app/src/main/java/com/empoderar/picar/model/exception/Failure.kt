package com.empoderar.picar.model.exception

sealed class Failure {
    class NetworkConnection: Failure()
    class ServerError: Failure()
    abstract class FeatureFailure: Failure()

}