package io.company.app.frontend.services.rpc

import io.udash.utils.{CallbacksHandler, Registration}

/** Provides notifications about new messages and connections status. */
class NotificationsCenter {
  private[rpc] val connectionsListeners: CallbacksHandler[Int] = new CallbacksHandler[Int]


  def onConnectionsCountChange(callback: connectionsListeners.CallbackType): Registration =
    connectionsListeners.register(callback)
}
