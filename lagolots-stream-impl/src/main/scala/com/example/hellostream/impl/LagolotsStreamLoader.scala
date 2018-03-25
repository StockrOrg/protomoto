package com.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.hellostream.api.LagolotsStreamService
import com.example.hello.api.LagolotsService
import com.softwaremill.macwire._

class LagolotsStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagolotsStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagolotsStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[LagolotsStreamService])
}

abstract class LagolotsStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LagolotsStreamService](wire[LagolotsStreamServiceImpl])

  // Bind the LagolotsService client
  lazy val lagolotsService = serviceClient.implement[LagolotsService]
}
