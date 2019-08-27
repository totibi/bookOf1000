package io.company.app.backend.rpc.secure

import io.company.app.backend.services.DomainServices
import io.company.app.shared.model.auth.UserContext
import io.company.app.shared.rpc.server.secure.SecureRPC

class SecureEndpoint(implicit domainServices: DomainServices, ctx: UserContext) extends SecureRPC {
  import domainServices._

}
