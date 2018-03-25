package com.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.hellostream.api.LagolotsStreamService
import com.example.hello.api.LagolotsService

import scala.concurrent.Future

/**
  * Implementation of the LagolotsStreamService.
  */
class LagolotsStreamServiceImpl(lagolotsService: LagolotsService) extends LagolotsStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(lagolotsService.hello(_).invoke()))
  }
}
