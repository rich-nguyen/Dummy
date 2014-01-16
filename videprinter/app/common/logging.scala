package common

trait Logging {
  implicit val log = play.api.Logger(getClass)
}

trait ExecutionContexts {
  implicit lazy val executionContext = play.api.libs.concurrent.Execution.Implicits.defaultContext
}
