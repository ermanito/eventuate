/*
 * Copyright (C) 2015 Red Bull Media House GmbH <http://www.redbullmediahouse.com> - all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package doc

object EventRouting {
  //#custom-routing
  import scala.util._
  import akka.actor._
  import com.rbmhtechnology.eventuate.EventsourcedActor

  case class ExampleEvent(data: String)
  case class ExampleCommand(data: String)

  class ExampleActor(override val replicaId: String,
                     override val eventLog: ActorRef) extends EventsourcedActor {

    override def aggregateId: Option[String] = Some("a1")

    override val onCommand: Receive = {
      case ExampleCommand(data) =>
        persist(ExampleEvent(data), customRoutingDestinations = Set("a2", "a3")) {
          case Success(evt)   => // ...
          case Failure(cause) => // ...
        }
    }

    // ...
  //#
    override val onEvent: Receive = {
      case ExampleEvent(data) => // ...
    }
  //#custom-routing
  }
  //#
}