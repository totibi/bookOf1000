package io.company.app.backend.services

import io.company.app.shared.model.auth.UserContext
import io.company.app.shared.rpc.client.MainClientRPC
import io.udash.rpc.{ClientId, ClientRPCTarget, DefaultClientRPC}

import scala.collection.mutable

/** Helper object for server -> client calls. */
class RpcClientsService(sendToClientFactory: ClientRPCTarget => MainClientRPC) {
  private val clients: mutable.Set[ClientId] = mutable.HashSet.empty
  private val authClients: mutable.Map[ClientId, UserContext] = mutable.HashMap.empty

  def sendToClient(target: ClientRPCTarget): MainClientRPC =
    sendToClientFactory(target)

  /** Returns active client ids. */
  def activeClients: Set[ClientId] = clients.toSet

  /** Returns authenticated client ids. */
  def authenticatedClients: Map[ClientId, UserContext] = authClients.toMap

  /** Adds new connection ID to the set. */
  def registerConnection(clientId: ClientId): Unit = {
    clients += clientId
  }

  /** Adds new connection ID to the authenticated set. */
  def registerAuthenticatedConnection(clientId: ClientId, userContext: UserContext): Unit = {
    authClients(clientId) = userContext
  }

  /** Removes connection ID from the list. */
  def unregisterConnection(clientId: ClientId): Unit ={
    clients -= clientId
    authClients.remove(clientId)
  }

}

object RpcClientsService {
  val defaultSendToClientFactory: ClientRPCTarget => MainClientRPC = {
    import scala.concurrent.ExecutionContext.Implicits.global
    (target: ClientRPCTarget) => new DefaultClientRPC[MainClientRPC](target).get
  }
}

/** Class with default factory for Spring configuration. */
private class SpringRpcClientsService() extends RpcClientsService(RpcClientsService.defaultSendToClientFactory)