package io.company.app.backend.services

/** Container for all services used to implicitly pass services to endpoints. */
class DomainServices(implicit
  val authService: AuthService,
  val rpcClientsService: RpcClientsService
)
